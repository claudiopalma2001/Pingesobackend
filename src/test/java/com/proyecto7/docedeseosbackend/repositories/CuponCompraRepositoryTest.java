package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import com.proyecto7.docedeseosbackend.repository.CuponCompraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CuponCompraRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CuponCompraRepository cuponCompraRepository;

    @Test
    public void whenFindById_thenReturnCuponCompraEntity() {
        // given
        CuponCompraEntity cuponCompra = new CuponCompraEntity(null, 1L, 2L);
        entityManager.persistAndFlush(cuponCompra);

        // when
        CuponCompraEntity found = cuponCompraRepository.findById(cuponCompra.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getIdCupon()).isEqualTo(cuponCompra.getIdCupon());
        assertThat(found.getIdCompra()).isEqualTo(cuponCompra.getIdCompra());
    }

    @Test
    public void whenSaveCuponCompra_thenReturnSavedEntity() {
        // given
        CuponCompraEntity cuponCompra = new CuponCompraEntity(null, 3L, 4L);

        // when
        CuponCompraEntity savedCuponCompra = cuponCompraRepository.save(cuponCompra);

        // then
        assertThat(savedCuponCompra).isNotNull();
        assertThat(savedCuponCompra.getIdCupon()).isEqualTo(cuponCompra.getIdCupon());
        assertThat(savedCuponCompra.getIdCompra()).isEqualTo(cuponCompra.getIdCompra());
    }
}

