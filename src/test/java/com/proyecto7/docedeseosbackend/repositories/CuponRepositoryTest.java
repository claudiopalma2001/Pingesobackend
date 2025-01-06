package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.repository.CuponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CuponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CuponRepository cuponRepository;

    @Test
    public void whenFindByNombreCupon_thenReturnCuponEntity() {
        // given
        CuponEntity cupon = new CuponEntity(null, "Cupon Navidad", "Premium", 1, 1000);
        entityManager.persistAndFlush(cupon);

        // when
        CuponEntity found = cuponRepository.findByNombreCupon(cupon.getNombreCupon());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getNombreCupon()).isEqualTo(cupon.getNombreCupon());
    }

    @Test
    public void whenFindByTipo_thenReturnListOfCuponEntity() {
        // given
        CuponEntity cupon1 = new CuponEntity(null, "Cupon Navidad", "Premium", 1, 1000);
        CuponEntity cupon2 = new CuponEntity(null, "Cupon picnic", "Free", 2, 1000);
        CuponEntity cupon3 = new CuponEntity(null, "Cupon Navidad", "Premium", 1, 1000);

        entityManager.persistAndFlush(cupon1);
        entityManager.persistAndFlush(cupon2);
        entityManager.persistAndFlush(cupon3);

        // when
        List<CuponEntity> foundList = cuponRepository.findByTipo("Free");

        assertThat(foundList).hasSize(1);

        // then
        assertThat(foundList).extracting(CuponEntity::getTipo).containsOnly("Free");
    }

    @Test
    public void whenFindByIdTematica_thenReturnListOfCuponEntity() {
        // given
        CuponEntity cupon1 = new CuponEntity(null, "Cupon Navidad", "Premium", 1, 1000);
        CuponEntity cupon2 = new CuponEntity(null, "Cupon San Valentin", "Free", 3, 1000);
        CuponEntity cupon3 = new CuponEntity(null, "Cupon Dia de Playa", "Premium", 1, 1000);

        entityManager.persistAndFlush(cupon1);
        entityManager.persistAndFlush(cupon2);
        entityManager.persistAndFlush(cupon3);

        // when
        List<CuponEntity> foundList = cuponRepository.findByIdTematica(1);

        // then
        assertThat(foundList).hasSize(2).extracting(CuponEntity::getIdTematica).containsOnly(1);
    }
}