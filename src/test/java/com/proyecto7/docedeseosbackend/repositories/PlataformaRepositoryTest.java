package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import com.proyecto7.docedeseosbackend.repository.PlataformaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PlataformaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Test
    public void whenFindById_thenReturnPlataformaEntity() {
        // given
        PlataformaEntity plataforma = new PlataformaEntity(null, "Plataforma Test");
        PlataformaEntity savedPlataforma = entityManager.persistAndFlush(plataforma);

        // when
        Optional<PlataformaEntity> foundPlataforma = plataformaRepository.findById(savedPlataforma.getId());

        // then
        assertThat(foundPlataforma).isPresent();
        assertThat(foundPlataforma.get().getTipoPlataforma()).isEqualTo(plataforma.getTipoPlataforma());
    }

    @Test
    public void whenSavePlataforma_thenReturnPlataformaEntity() {
        // given
        PlataformaEntity plataforma = new PlataformaEntity(null, "Plataforma Test");
        PlataformaEntity savedPlataforma = plataformaRepository.save(plataforma);

        // when
        PlataformaEntity foundPlataforma = entityManager.find(PlataformaEntity.class, savedPlataforma.getId());

        // then
        assertThat(foundPlataforma).isNotNull();
        assertThat(foundPlataforma.getTipoPlataforma()).isEqualTo(plataforma.getTipoPlataforma());
    }
}
