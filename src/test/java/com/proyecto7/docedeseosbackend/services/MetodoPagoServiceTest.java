package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;
import com.proyecto7.docedeseosbackend.repository.MetodoPagoRepository;
import com.proyecto7.docedeseosbackend.service.MetodoPagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MetodoPagoServiceTest {

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMetodoPago() {
        MetodoPagoEntity metodoPago = new MetodoPagoEntity(null, "Tarjeta de Crédito", 1);
        MetodoPagoEntity savedMetodoPago = new MetodoPagoEntity(1L, "Tarjeta de Crédito", 1);

        when(metodoPagoRepository.save(any(MetodoPagoEntity.class))).thenReturn(savedMetodoPago);

        MetodoPagoEntity resultado = metodoPagoService.saveMetodoPago(metodoPago);
        assertNotNull(resultado, "El método de pago guardado no debe ser nulo");
        assertEquals(savedMetodoPago.getNombreMetodo(), resultado.getNombreMetodo());
    }

    @Test
    void getMetodoPagoById() {
        Long idMetodoPago = 1L;
        MetodoPagoEntity metodoPago = new MetodoPagoEntity(idMetodoPago, "Transferencia", 2);

        when(metodoPagoRepository.findById(idMetodoPago)).thenReturn(Optional.of(metodoPago));

        MetodoPagoEntity resultado = metodoPagoService.getMetodoPagoById(idMetodoPago);
        assertNotNull(resultado, "El método de pago encontrado no debe ser nulo");
        assertEquals(metodoPago.getNombreMetodo(), resultado.getNombreMetodo());
    }

    @Test
    void deleteMetodoPago() {
        Long idMetodoPago = 1L;
        doNothing().when(metodoPagoRepository).deleteById(idMetodoPago);

        boolean resultado = metodoPagoService.deleteMetodoPago(idMetodoPago);
        assertEquals(true, resultado);
        verify(metodoPagoRepository, times(1)).deleteById(idMetodoPago);
    }

    @Test
    void testUpdateMetodoPago() throws Exception {
        Long id = 1L;
        MetodoPagoEntity existingMetodoPago = new MetodoPagoEntity(id, "Tarjeta de Credito", 1);
        MetodoPagoEntity updatedData = new MetodoPagoEntity(id, "Tarjeta de Debito", 2);

        when(metodoPagoRepository.findById(id)).thenReturn(Optional.of(existingMetodoPago));
        when(metodoPagoRepository.save(any(MetodoPagoEntity.class))).thenReturn(updatedData);

        MetodoPagoEntity result = metodoPagoService.updateMetodoPago(id, updatedData);

        assertNotNull(result);
        assertEquals("Tarjeta de Debito", result.getNombreMetodo());
        assertEquals(2, result.getIdPago());

        verify(metodoPagoRepository, times(1)).findById(id);
        verify(metodoPagoRepository, times(1)).save(existingMetodoPago);
    }

    @Test
    void testUpdateMetodoPagoNotFound() {
        Long id = 1L;
        MetodoPagoEntity updatedData = new MetodoPagoEntity(id, "Tarjeta de Debito", 2);

        when(metodoPagoRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> metodoPagoService.updateMetodoPago(id, updatedData));
        assertEquals("Metodo de pago no encontrado", exception.getMessage());

        verify(metodoPagoRepository, times(1)).findById(id);
        verify(metodoPagoRepository, times(0)).save(any(MetodoPagoEntity.class));
    }

    @Test
    void getAllMetodosPago() {
        MetodoPagoEntity metodoPago1 = new MetodoPagoEntity(1L, "Tarjeta de Crédito", 1);
        MetodoPagoEntity metodoPago2 = new MetodoPagoEntity(2L, "PayPal", 2);
        List<MetodoPagoEntity> listaMetodosPago = Arrays.asList(metodoPago1, metodoPago2);

        when(metodoPagoRepository.findAll()).thenReturn(listaMetodosPago);

        List<MetodoPagoEntity> resultado = metodoPagoService.getMetodosPago();
        assertNotNull(resultado, "La lista de métodos de pago no debe ser nula");
        assertEquals(2, resultado.size(), "La lista de métodos de pago debe contener dos elementos");
        assertEquals("Tarjeta de Crédito", resultado.get(0).getNombreMetodo(), "El primer método de pago debe ser Tarjeta de Crédito");
        assertEquals("PayPal", resultado.get(1).getNombreMetodo(), "El segundo método de pago debe ser PayPal");
    }
}
