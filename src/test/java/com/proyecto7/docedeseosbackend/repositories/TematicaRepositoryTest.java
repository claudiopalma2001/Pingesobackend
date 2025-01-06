package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import com.proyecto7.docedeseosbackend.repository.TematicaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TematicaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TematicaRepository tematicaRepository;

    @Test
    public void whenFindByNombreTematica_thenReturnTematicaEntity() {
        // given
        TematicaEntity tematica = new TematicaEntity(null, "Pololos", "Temática sobre actividades que pueden realizar los pololos");
        entityManager.persistAndFlush(tematica);

        // when
        TematicaEntity found = tematicaRepository.findByNombreTematica(tematica.getNombreTematica());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getNombreTematica()).isEqualTo(tematica.getNombreTematica());
    }

    @Test
    public void whenFindByDescripcionContaining_thenReturnListOfTematicaEntities() {
        // given
        TematicaEntity tematica1 = new TematicaEntity(null, "Pololos", "Temática sobre actividades que pueden realizar los pololos");
        TematicaEntity tematica2 = new TematicaEntity(null, "Embarazadas", "Temática sobre actividades que pueden realizar las embarazadas");
        TematicaEntity tematica3 = new TematicaEntity(null, "Familiar", "Temática sobre actividades que pueden realizar en familia");

        entityManager.persistAndFlush(tematica1);
        entityManager.persistAndFlush(tematica2);
        entityManager.persistAndFlush(tematica3);

        // when
        List<TematicaEntity> foundList = tematicaRepository.findByDescripcionContaining("Temática sobre actividades que pueden realizar en familia");

        // then
        assertThat(foundList).hasSize(1);
        assertThat(foundList).extracting(TematicaEntity::getDescripcion)
                .containsExactly("Temática sobre actividades que pueden realizar en familia");
    }

    @Test
    public void whenFindByDescripcionContaining_thenReturnEmptyListIfNoMatch() {
        // given
        TematicaEntity tematica1 = new TematicaEntity(null, "Pololos", "Temática sobre actividades que pueden realizar los pololos");
        TematicaEntity tematica2 = new TematicaEntity(null, "Amistad", "Temática sobre actividades que pueden realizar entre amigos");

        entityManager.persistAndFlush(tematica1);
        entityManager.persistAndFlush(tematica2);

        // when
        List<TematicaEntity> foundList = tematicaRepository.findByDescripcionContaining("Infantil");

        // then
        assertThat(foundList).isEmpty();
    }

    @Test
    public void whenFindById_thenReturnTematicaEntity() {
        // given
        TematicaEntity tematica = new TematicaEntity(null, "Papá", "Temática sobre actividades que pueden realizar con tu papá");
        entityManager.persistAndFlush(tematica);

        // when
        TematicaEntity found = tematicaRepository.findById(tematica.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(tematica.getId());
    }
}
