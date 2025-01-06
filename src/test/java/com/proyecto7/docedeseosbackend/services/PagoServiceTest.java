package com.proyecto7.docedeseosbackend.services;
import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import com.proyecto7.docedeseosbackend.repository.PagoRepository;
import com.proyecto7.docedeseosbackend.service.PagoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PagoServiceTest{
    @InjectMocks
    private PagoService pagoService;
    @Mock
    private PagoRepository pagoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPago(){
        PagoEntity pago = new PagoEntity();
        pago.setBoleta("Nuevo pago");
        pago.setMonto(500);
        when(pagoRepository.save(any(PagoEntity.class))).thenReturn(pago);
        PagoEntity resultado = pagoService.savePago(pago);
        assertNotNull(resultado, "El pago guardado no debe ser nulo");
        Assertions.assertEquals(pago.getBoleta(), resultado.getBoleta());
    }

    @Test
    void findPagos(){
        Long idPago = 1L;
        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setId(idPago);
        pagoEntity.setBoleta("Descripcion");
        pagoEntity.setMonto(1000);
        when(pagoRepository.findById(pagoEntity.getId())).thenReturn(Optional.of(pagoEntity));
        Optional<PagoEntity> resultado = pagoService.getPagoById(pagoEntity.getId());
        Assertions.assertEquals(pagoEntity.getBoleta(), resultado.get().getBoleta());
        Assertions.assertEquals(pagoEntity.getMonto(), resultado.get().getMonto());

    }

    @Test
    void testDeletePago() {
        Long id = 1L;

        assertDoesNotThrow(() -> pagoRepository.deleteById(id));

        verify(pagoRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdatePagoSuccess() throws Exception {

        Long id = 1L;
        PagoEntity existingPago = new PagoEntity(id, 1000.0, "boleta123");
        PagoEntity updatedPagoData = new PagoEntity(id, 1500.0, "boleta456");

        when(pagoRepository.findById(id)).thenReturn(Optional.of(existingPago));
        when(pagoRepository.save(any(PagoEntity.class))).thenReturn(updatedPagoData);

        PagoEntity updatedPago = pagoService.updatePago(id, updatedPagoData);

        assertNotNull(updatedPago);
        assertEquals(1500.0, updatedPago.getMonto(), 0.01);
        assertEquals("boleta456", updatedPago.getBoleta());

        verify(pagoRepository, times(1)).findById(id);
        verify(pagoRepository, times(1)).save(any(PagoEntity.class));
    }

    @Test
    void getAllPagos() {
        // given
        PagoEntity pago1 = new PagoEntity(1L, 100.0, "boleta1");
        PagoEntity pago2 = new PagoEntity(2L, 200.0, "boleta2");
        List<PagoEntity> listaPagos = Arrays.asList(pago1, pago2);

        // when
        when(pagoRepository.findAll()).thenReturn(listaPagos);

        // then
        List<PagoEntity> resultado = pagoService.getPagos();
        Assertions.assertNotNull(resultado, "La lista de pagos no debe ser nula");
        Assertions.assertEquals(2, resultado.size(), "La lista de pagos debe contener dos elementos");
        Assertions.assertEquals("boleta1", resultado.get(0).getBoleta(), "El primer pago debe ser boleta1");
        Assertions.assertEquals("boleta2", resultado.get(1).getBoleta(), "El segundo pago debe ser boleta2");
    }
}

