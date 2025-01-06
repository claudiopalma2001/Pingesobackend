package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;
import com.proyecto7.docedeseosbackend.repository.MetodoPagoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
public class MetodoPagoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Test
    public void whenFindByNombreMetodo_thenReturnMetodoPagoEntity() {
        // given
        MetodoPagoEntity metodoPago = new MetodoPagoEntity(null, "Tarjeta de Crédito", 1);
        entityManager.persistAndFlush(metodoPago);

        // when
        MetodoPagoEntity found = metodoPagoRepository.findByNombreMetodo(metodoPago.getNombreMetodo());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getNombreMetodo()).isEqualTo(metodoPago.getNombreMetodo());
    }

    @Test
    public void whenFindByIdPago_thenReturnListOfMetodoPagoEntity() {
        // given
        MetodoPagoEntity metodoPago1 = new MetodoPagoEntity(null, "PayPal", 2);
        MetodoPagoEntity metodoPago2 = new MetodoPagoEntity(null, "Transferencia", 2);

        entityManager.persistAndFlush(metodoPago1);
        entityManager.persistAndFlush(metodoPago2);

        // when
        List<MetodoPagoEntity> foundList = metodoPagoRepository.findByIdPago(2);

        // then
        assertThat(foundList).hasSize(2).extracting(MetodoPagoEntity::getIdPago).containsOnly(2);
    }
}
