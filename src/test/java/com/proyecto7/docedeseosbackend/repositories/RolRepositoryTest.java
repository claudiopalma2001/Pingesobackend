package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.RolEntity;
import com.proyecto7.docedeseosbackend.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RolRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RolRepository rolRepository;

    @Test
    public void whenFindByNombreRol_thenReturnRolEntity() {
        // given
        RolEntity rol = new RolEntity(null, "Usuario");
        entityManager.persistAndFlush(rol);

        // when
        Optional<RolEntity> found = rolRepository.findByNombreRol("Usuario");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNombreRol()).isEqualTo("Usuario");
    }

    @Test
    public void whenFindById_thenReturnRolEntity() {
        // given
        RolEntity rol = new RolEntity(null, "Administrador");
        RolEntity savedRol = entityManager.persistAndFlush(rol);

        // when
        Optional<RolEntity> found = rolRepository.findById(savedRol.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNombreRol()).isEqualTo("Administrador");
    }
}
