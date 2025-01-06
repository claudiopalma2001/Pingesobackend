package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.repository.PlantillaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PlantillaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlantillaRepository plantillaRepository;

    @Test
    public void whenFindByIdCupon_thenReturnListOfPlantillaEntity() {
        // given
        PlantillaEntity plantilla1 = new PlantillaEntity(null, 1, 1, 1, "http://example.com/imagen1.jpg");
        PlantillaEntity plantilla2 = new PlantillaEntity(null, 2, 2, 2, "http://example.com/imagen2.jpg");
        entityManager.persistAndFlush(plantilla1);
        entityManager.persistAndFlush(plantilla2);

        // when
        List<PlantillaEntity> foundList = plantillaRepository.findByIdCupon(1);

        // then
        assertThat(foundList).hasSize(1).extracting(PlantillaEntity::getIdCupon).containsOnly(1);
    }

    @Test
    public void whenFindByIdIdioma_thenReturnListOfPlantillaEntity() {
        // given
        PlantillaEntity plantilla1 = new PlantillaEntity(null, 1, 1, 1, "http://example.com/imagen1.jpg");
        PlantillaEntity plantilla2 = new PlantillaEntity(null, 2, 2, 2, "http://example.com/imagen2.jpg");
        entityManager.persistAndFlush(plantilla1);
        entityManager.persistAndFlush(plantilla2);

        // when
        List<PlantillaEntity> foundList = plantillaRepository.findByIdIdioma(2);

        // then
        assertThat(foundList).hasSize(1).extracting(PlantillaEntity::getIdIdioma).containsOnly(2);
    }

    @Test
    public void whenFindByIdPlataforma_thenReturnListOfPlantillaEntity() {
        // given
        PlantillaEntity plantilla1 = new PlantillaEntity(null, 1, 1, 1, "http://example.com/imagen1.jpg");
        PlantillaEntity plantilla2 = new PlantillaEntity(null, 2, 2, 2, "http://example.com/imagen2.jpg");
        entityManager.persistAndFlush(plantilla1);
        entityManager.persistAndFlush(plantilla2);

        // when
        List<PlantillaEntity> foundList = plantillaRepository.findByIdPlataforma(2);

        // then
        assertThat(foundList).hasSize(1).extracting(PlantillaEntity::getIdPlataforma).containsOnly(2);
    }

    @Test
    public void whenFindByUrlImagen_thenReturnListOfPlantillaEntity() {
        // given
        PlantillaEntity plantilla1 = new PlantillaEntity(null, 1, 1, 1, "http://example.com/imagen1.jpg");
        PlantillaEntity plantilla2 = new PlantillaEntity(null, 2, 2, 2, "http://example.com/imagen2.jpg");
        entityManager.persistAndFlush(plantilla1);
        entityManager.persistAndFlush(plantilla2);

        // when
        List<PlantillaEntity> foundList = plantillaRepository.findByUrlImagen("http://example.com/imagen1.jpg");

        // then
        assertThat(foundList).hasSize(1).extracting(PlantillaEntity::getUrlImagen).containsOnly("http://example.com/imagen1.jpg");
    }
}