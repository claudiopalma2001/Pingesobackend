package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.repository.CuponFinalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CuponFinalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CuponFinalRepository cuponFinalRepository;

    // 1. Buscar cupones por ID de cupón
    @Test
    public void whenFindByIdCupon_thenReturnCuponFinalEntities() {
        // Datos de prueba
        CuponFinalEntity cupon1 = new CuponFinalEntity(null, "De1", "Para1", "Incluye1",
                LocalDate.of(2024, 11, 11), 101L, 201L, 301L, 1000, null);
        CuponFinalEntity cupon2 = new CuponFinalEntity(null, "De2", "Para2", "Incluye2",
                LocalDate.of(2024, 12, 12), 101L, 202L, 302L, 1500, null);
        CuponFinalEntity cupon3 = new CuponFinalEntity(null, "De3", "Para3", "Incluye3",
                LocalDate.of(2024, 1, 1), 102L, 203L, 303L, 2000, null);

        // Persistimos los cupones en la base de datos de prueba
        entityManager.persistAndFlush(cupon1);
        entityManager.persistAndFlush(cupon2);
        entityManager.persistAndFlush(cupon3);

        // Cuando buscamos por idCupon = 101L
        List<CuponFinalEntity> foundCupones = cuponFinalRepository.findByIdCupon(101L);

        // Entonces
        assertThat(foundCupones).hasSize(2);
        assertThat(foundCupones).extracting(CuponFinalEntity::getIdCupon).containsOnly(101L);
        assertThat(foundCupones).extracting(CuponFinalEntity::getCampoDe)
                .containsExactlyInAnyOrder("De1", "De2");
    }

    // 2. Buscar cupones por ID de usuario
    @Test
    public void whenFindByIdUsuario_thenReturnCuponFinalEntities() {
        // Datos de prueba
        CuponFinalEntity cupon1 = new CuponFinalEntity(null, "De1", "Para1", "Incluye1",
                LocalDate.of(2024, 11, 11), 101L, 201L, 301L, 1000, null);
        CuponFinalEntity cupon2 = new CuponFinalEntity(null, "De2", "Para2", "Incluye2",
                LocalDate.of(2024, 12, 12), 102L, 201L, 302L, 1500, null);
        CuponFinalEntity cupon3 = new CuponFinalEntity(null, "De3", "Para3", "Incluye3",
                LocalDate.of(2024, 1, 1), 103L, 202L, 303L, 2000, null);

        // Persistimos los cupones en la base de datos de prueba
        entityManager.persistAndFlush(cupon1);
        entityManager.persistAndFlush(cupon2);
        entityManager.persistAndFlush(cupon3);

        // Cuando buscamos por idUsuario = 201L
        List<CuponFinalEntity> foundCupones = cuponFinalRepository.findByIdUsuario(201L);

        // Entonces
        assertThat(foundCupones).hasSize(2);
        assertThat(foundCupones).extracting(CuponFinalEntity::getIdUsuario).containsOnly(201L);
        assertThat(foundCupones).extracting(CuponFinalEntity::getCampoIncluye)
                .containsExactlyInAnyOrder("Incluye1", "Incluye2");
    }

    // 3. Buscar cupones por ID de plantilla
    @Test
    public void whenFindByIdPlantilla_thenReturnCuponFinalEntities() {
        // Datos de prueba
        CuponFinalEntity cupon1 = new CuponFinalEntity(null, "De1", "Para1", "Incluye1",
                LocalDate.of(2024, 11, 11), 101L, 201L, 301L, 1000, null);
        CuponFinalEntity cupon2 = new CuponFinalEntity(null, "De2", "Para2", "Incluye2",
                LocalDate.of(2024, 12, 12), 102L, 202L, 301L, 1500, null);
        CuponFinalEntity cupon3 = new CuponFinalEntity(null, "De3", "Para3", "Incluye3",
                LocalDate.of(2024, 1, 1), 103L, 203L, 302L, 2000, null);

        // Persistimos los cupones en la base de datos de prueba
        entityManager.persistAndFlush(cupon1);
        entityManager.persistAndFlush(cupon2);
        entityManager.persistAndFlush(cupon3);

        // Cuando buscamos por idPlantilla = 301L
        List<CuponFinalEntity> foundCupones = cuponFinalRepository.findByIdPlantilla(301L);

        // Entonces
        assertThat(foundCupones).hasSize(2);
        assertThat(foundCupones).extracting(CuponFinalEntity::getIdPlantilla).containsOnly(301L);
        assertThat(foundCupones).extracting(CuponFinalEntity::getCampoIncluye)
                .containsExactlyInAnyOrder("Incluye1", "Incluye2");
    }

    // 4. Buscar cupón por ID
    @Test
    public void whenFindById_thenReturnCuponFinalEntity() {
        // Datos de prueba
        CuponFinalEntity cupon = new CuponFinalEntity(null, "De1", "Para1", "Incluye1",
                LocalDate.of(2024, 11, 11), 101L, 201L, 301L, 1000, null);

        // Persistimos el cupón en la base de datos de prueba
        CuponFinalEntity persistedCupon = entityManager.persistAndFlush(cupon);

        // Cuando buscamos por id
        CuponFinalEntity foundCupon = cuponFinalRepository.findById(persistedCupon.getId()).orElse(null);

        // Entonces
        assertThat(foundCupon).isNotNull();
        assertThat(foundCupon.getCampoDe()).isEqualTo("De1");
        assertThat(foundCupon.getIdCupon()).isEqualTo(101L);
    }
}