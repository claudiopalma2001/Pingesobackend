package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import com.proyecto7.docedeseosbackend.repository.IdiomaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class IdiomaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Test
    public void whenFindByNombreIdioma_thenReturnIdiomaEntity() {
        // given
        IdiomaEntity idioma = new IdiomaEntity(null, "Español");
        entityManager.persistAndFlush(idioma);

        // when
        IdiomaEntity found = idiomaRepository.findByNombreIdioma(idioma.getNombreIdioma());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getNombreIdioma()).isEqualTo(idioma.getNombreIdioma());
    }

    @Test
    public void whenFindAll_thenReturnListOfIdiomaEntities() {
        // given
        IdiomaEntity idioma1 = new IdiomaEntity(null, "Español");
        IdiomaEntity idioma2 = new IdiomaEntity(null, "Inglés");

        entityManager.persistAndFlush(idioma1);
        entityManager.persistAndFlush(idioma2);

        // when
        List<IdiomaEntity> foundList = idiomaRepository.findAll();

        // then
        assertThat(foundList).hasSize(2)
                .extracting(IdiomaEntity::getNombreIdioma)
                .containsOnly("Español", "Inglés");
    }
}
