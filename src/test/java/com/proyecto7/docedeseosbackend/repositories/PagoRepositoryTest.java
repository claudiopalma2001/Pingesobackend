package com.proyecto7.docedeseosbackend.repositories;

import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import com.proyecto7.docedeseosbackend.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
public class PagoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    public void whenFindByBoleta_thenReturnPagoEntity() {
        // given
        PagoEntity pago = new PagoEntity(null, 100.0, "boleta1");
        entityManager.persistAndFlush(pago);

        // when
        PagoEntity found = pagoRepository.findByBoleta(pago.getBoleta());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getBoleta()).isEqualTo(pago.getBoleta());
    }

    @Test
    public void whenFindByMonto_thenReturnListOfPagoEntity() {
        // given
        PagoEntity pago1 = new PagoEntity(null, 150.0, "boleta1");
        PagoEntity pago2 = new PagoEntity(null, 150.0, "boleta2");

        entityManager.persistAndFlush(pago1);
        entityManager.persistAndFlush(pago2);

        // when
        List<PagoEntity> foundList = pagoRepository.findByMonto(150.0);

        // then
        assertThat(foundList).hasSize(2).extracting(PagoEntity::getMonto).containsOnly(150.0);
    }
}
