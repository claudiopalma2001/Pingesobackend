package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import com.proyecto7.docedeseosbackend.repository.CuponCompraRepository;
import com.proyecto7.docedeseosbackend.service.CuponCompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuponCompraServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    CuponCompraRepository cuponCompraRepository;

    @InjectMocks
    CuponCompraService cuponCompraService;

    @Test
    void testGetCuponesCompra() {
        CuponCompraEntity cupon1 = new CuponCompraEntity(1L, 1L, 1L);
        CuponCompraEntity cupon2 = new CuponCompraEntity(2L, 1L, 2L);
        List<CuponCompraEntity> cuponesList = new ArrayList<>(List.of(cupon1, cupon2));

        when(cuponCompraRepository.findAll()).thenReturn(cuponesList);
        List<CuponCompraEntity> result = cuponCompraService.getCuponesCompra();

        assertEquals(2, result.size());
        verify(cuponCompraRepository, times(1)).findAll();
    }

    @Test
    void testGetCuponCompraById() {
        Long id = 1L;
        CuponCompraEntity cupon = new CuponCompraEntity(id, 1L, 1L);

        when(cuponCompraRepository.findById(id)).thenReturn(Optional.of(cupon));
        CuponCompraEntity result = cuponCompraService.getCuponCompraById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(cuponCompraRepository, times(1)).findById(id);
    }

    @Test
    void testSaveCuponCompra() {
        CuponCompraEntity cupon = new CuponCompraEntity(1L, 1L, 1L);

        when(cuponCompraRepository.save(any(CuponCompraEntity.class))).thenReturn(cupon);
        CuponCompraEntity savedCupon = cuponCompraService.saveCuponCompra(cupon);

        assertNotNull(savedCupon);
        assertEquals(1L, savedCupon.getIdCompra());
        assertEquals(1L, savedCupon.getIdCupon());
        verify(cuponCompraRepository, times(1)).save(cupon);
    }

    @Test
    void testDeleteCuponCompra() throws Exception {
        Long id = 1L;

        doNothing().when(cuponCompraRepository).deleteById(id);
        assertTrue(cuponCompraService.deleteCuponCompra(id));

        verify(cuponCompraRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCuponCompra_NotFound() {
        Long id = 1L;

        doThrow(new RuntimeException("Cupon no encontrado")).when(cuponCompraRepository).deleteById(id);

        Exception exception = assertThrows(Exception.class, () -> cuponCompraService.deleteCuponCompra(id));
        assertEquals("Cupon no encontrado", exception.getMessage());

        verify(cuponCompraRepository, times(1)).deleteById(id);
    }
}
