package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.repository.CompraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CompraRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompraRepository compraRepository;

    // 1. Prueba: Obtener compras por ID de usuario con resultados
    @Test
    public void whenFindByIdUsuario_thenReturnCompraEntities() {
        // given
        Long userId = 1L;
        CompraEntity compra1 = new CompraEntity(null, userId, LocalDate.now(), 10000.0f, new ArrayList<>());
        CompraEntity compra2 = new CompraEntity(null, userId, LocalDate.now().minusDays(1), 15000.0f, new ArrayList<>());

        entityManager.persistAndFlush(compra1);
        entityManager.persistAndFlush(compra2);

        // when
        List<CompraEntity> compras = compraRepository.findByIdUsuario(userId);

        // then
        assertThat(compras).isNotNull();
        assertThat(compras).hasSize(2);
        assertThat(compras).extracting(CompraEntity::getIdUsuario).containsOnly(userId);
    }

    // 2. Prueba: Obtener compras por ID de usuario sin resultados
    @Test
    public void whenFindByIdUsuario_withNoPurchases_thenReturnEmptyList() {
        // given
        Long userId = 2L;

        // when
        List<CompraEntity> compras = compraRepository.findByIdUsuario(userId);

        // then
        assertThat(compras).isNotNull();
        assertThat(compras).isEmpty();
    }

    // 3. Prueba: Obtener compras para múltiples usuarios
    @Test
    public void whenFindByIdUsuario_withMultipleUsers_thenReturnCorrectCompras() {
        // given
        CompraEntity compraForUser1 = new CompraEntity(null, 1L, LocalDate.now(), 12000.0f, new ArrayList<>());
        CompraEntity compraForUser2 = new CompraEntity(null, 2L, LocalDate.now(), 25000.0f, new ArrayList<>());

        entityManager.persistAndFlush(compraForUser1);
        entityManager.persistAndFlush(compraForUser2);

        // when
        List<CompraEntity> comprasUser1 = compraRepository.findByIdUsuario(1L);
        List<CompraEntity> comprasUser2 = compraRepository.findByIdUsuario(2L);

        // then
        assertThat(comprasUser1).isNotNull();
        assertThat(comprasUser1).hasSize(1);
        assertThat(comprasUser1.get(0).getIdUsuario()).isEqualTo(1L);

        assertThat(comprasUser2).isNotNull();
        assertThat(comprasUser2).hasSize(1);
        assertThat(comprasUser2.get(0).getIdUsuario()).isEqualTo(2L);
    }

    // 4. Prueba: Verificar compras con fecha específica
    @Test
    public void whenFindComprasByDate_thenReturnCorrectCompras() {
        // given
        LocalDate targetDate = LocalDate.now();
        CompraEntity compraToday = new CompraEntity(null, 1L, targetDate, 15000.0f, new ArrayList<>());
        CompraEntity compraYesterday = new CompraEntity(null, 2L, targetDate.minusDays(1), 20000.0f, new ArrayList<>());

        entityManager.persistAndFlush(compraToday);
        entityManager.persistAndFlush(compraYesterday);

        // when
        List<CompraEntity> comprasToday = compraRepository.findAll()
                .stream()
                .filter(compra -> compra.getFechaCompra().isEqual(targetDate))
                .toList();

        // then
        assertThat(comprasToday).isNotNull();
        assertThat(comprasToday).hasSize(1);
        assertThat(comprasToday.get(0).getMontoTotal()).isEqualTo(15000.0f);
    }

    // 5. Prueba: Verificar compras con cupones asociados
    @Test
    public void whenFindCompraWithCupones_thenReturnCompraWithCupones() {
        // given
        LocalDate targetDate = LocalDate.now();
        CuponFinalEntity cupon = new CuponFinalEntity(null, "De", "Para", "Incluye", targetDate, 1L, 1L, 1L, 500, null);
        List<CuponFinalEntity> cupones = List.of(cupon);
        CompraEntity compraWithCupon = new CompraEntity(null, 1L, targetDate, 15000.0f, cupones);

        entityManager.persistAndFlush(compraWithCupon);

        // when
        List<CompraEntity> compras = compraRepository.findByIdUsuario(1L);

        // then
        assertThat(compras).isNotNull();
        assertThat(compras).hasSize(1);
        assertThat(compras.get(0).getCuponesFinales()).hasSize(1);
        assertThat(compras.get(0).getCuponesFinales().get(0).getCampoDe()).isEqualTo("De");
    }
}