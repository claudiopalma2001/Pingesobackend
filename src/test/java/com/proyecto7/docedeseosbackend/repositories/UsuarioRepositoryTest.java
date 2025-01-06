package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void whenFindByCorreo_thenReturnUsuarioEntity() {
        // given
        UsuarioEntity usuario = new UsuarioEntity(null, "Pedro", "pedro@example.com", "testpassword", 26, "Basico", 0);
        entityManager.persistAndFlush(usuario);

        // when
        UsuarioEntity found = usuarioRepository.findByCorreo(usuario.getCorreo());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getCorreo()).isEqualTo(usuario.getCorreo());
    }

    @Test
    public void whenFindByEdad_thenReturnListOfUsuarioEntity() {
        // given
        UsuarioEntity usuario1 = new UsuarioEntity(null, "Pedro", "pedro@example.com", "testpassword", 21, "Basico", 0);
        UsuarioEntity usuario2 = new UsuarioEntity(null, "Benjamin", "benjamin@example.com", "asdfd123", 24, "Premium", 0);

        entityManager.persistAndFlush(usuario1);
        entityManager.persistAndFlush(usuario2);

        // when
        List<UsuarioEntity> foundList = usuarioRepository.findByEdad(24);

        // then
        assertThat(foundList).hasSize(1).extracting(UsuarioEntity::getEdad).containsOnly(24);
    }

    @Test
    public void whenFindByPlanUsuario_thenListOfReturnUsuarioEntity() {
        // given
        UsuarioEntity usuario1 = new UsuarioEntity(null, "Camilo", "camilo@example.com", "abyssarium123", 21, "Basico", 0);
        UsuarioEntity usuario2 = new UsuarioEntity(null, "Roberto", "roberto@example.com", "1ymedio2", 24, "Basico", 0);

        entityManager.persistAndFlush(usuario1);
        entityManager.persistAndFlush(usuario2);

        // when
        List<UsuarioEntity> foundList = usuarioRepository.findByPlanUsuario("Basico");

        // then
        assertThat(foundList).hasSize(2).extracting(UsuarioEntity::getPlanUsuario).containsOnly("Basico");
    }

    @Test
    public void whenFindByIdRol_thenReturnListOfUsuarioEntity() {
        // given
        UsuarioEntity usuario1 = new UsuarioEntity(null, "Camilo", "martin@example.com", "pelota25", 25, "Basico", 0);
        UsuarioEntity usuario2 = new UsuarioEntity(null, "Roberto", "bastian@example.com", "parrilla23", 28, "Basico", 0);

        entityManager.persistAndFlush(usuario1);
        entityManager.persistAndFlush(usuario2);

        // when
        List<UsuarioEntity> foundList = usuarioRepository.findByIdRol(0);

        // then
        assertThat(foundList).hasSize(2).extracting(UsuarioEntity::getIdRol).containsOnly(0);
    }
}
