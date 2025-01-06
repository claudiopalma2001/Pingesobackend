package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.repository.CuponFinalRepository;
import com.proyecto7.docedeseosbackend.service.CuponFinalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuponFinalServiceTest {

    @Mock
    private CuponFinalRepository cuponFinalRepository;

    @InjectMocks
    private CuponFinalService cuponFinalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Obtener todos los cupones finales
    @Test
    void testGetCuponesFinales() {
        List<CuponFinalEntity> cuponesList = List.of(
                new CuponFinalEntity(1L, "Campo De 1", "Campo Para 1", "Campo Incluye 1", LocalDate.of(2024, 11, 1), 1L, 1L, 1L, 1000, null),
                new CuponFinalEntity(2L, "Campo De 2", "Campo Para 2", "Campo Incluye 2", LocalDate.of(2024, 11, 2), 1L, 1L, 2L, 2000, null)
        );

        when(cuponFinalRepository.findAll()).thenReturn(cuponesList);

        List<CuponFinalEntity> result = cuponFinalService.getCuponesFinales();

        assertEquals(2, result.size());
        verify(cuponFinalRepository, times(1)).findAll();
    }

    // 2. Obtener cupón final por ID
    @Test
    void testGetCuponFinalById() {
        Long id = 1L;
        CuponFinalEntity cupon = new CuponFinalEntity(id, "Campo De", "Campo Para", "Campo Incluye", LocalDate.of(2024, 11, 1), 1L, 1L, 1L, 1000, null);

        when(cuponFinalRepository.findById(id)).thenReturn(Optional.of(cupon));

        CuponFinalEntity result = cuponFinalService.getCuponFinalById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(cuponFinalRepository, times(1)).findById(id);
    }

    // 3. Obtener cupones finales por ID de cupón
    @Test
    void testGetCuponesFinalesByIdCupon() {
        Long idCupon = 1L;
        List<CuponFinalEntity> cuponesList = List.of(
                new CuponFinalEntity(1L, "Campo De 1", "Campo Para 1", "Campo Incluye 1", LocalDate.of(2024, 11, 1), idCupon, 1L, 1L, 1000, null),
                new CuponFinalEntity(2L, "Campo De 2", "Campo Para 2", "Campo Incluye 2", LocalDate.of(2024, 11, 2), idCupon, 1L, 2L, 2000, null)
        );

        when(cuponFinalRepository.findByIdCupon(idCupon)).thenReturn(cuponesList);

        List<CuponFinalEntity> result = cuponFinalService.getCuponesFinalesByIdCupon(idCupon);

        assertEquals(2, result.size());
        verify(cuponFinalRepository, times(1)).findByIdCupon(idCupon);
    }

    // 4. Guardar un cupón final
    @Test
    void testSaveCuponFinal() {
        CuponFinalEntity cupon = new CuponFinalEntity(null, "Campo De", "Campo Para", "Campo Incluye", LocalDate.of(2024, 11, 1), 1L, 1L, 1L, 1000, null);

        when(cuponFinalRepository.save(any(CuponFinalEntity.class))).thenReturn(cupon);

        CuponFinalEntity result = cuponFinalService.saveCuponFinal(cupon);

        assertNotNull(result);
        assertEquals("Campo De", result.getCampoDe());
        verify(cuponFinalRepository, times(1)).save(cupon);
    }

    // 5. Actualizar un cupón final existente
    @Test
    void testUpdateCuponFinal() throws Exception {
        Long id = 1L;
        CuponFinalEntity existingCupon = new CuponFinalEntity(id, "Campo De", "Campo Para", "Campo Incluye", LocalDate.of(2024, 11, 1), 1L, 1L, 1L, 1000, null);
        CuponFinalEntity updatedCupon = new CuponFinalEntity(id, "Campo De Updated", "Campo Para Updated", "Campo Incluye Updated", LocalDate.of(2024, 11, 2), 1L, 1L, 1L, 1500, null);

        when(cuponFinalRepository.findById(id)).thenReturn(Optional.of(existingCupon));
        when(cuponFinalRepository.save(any(CuponFinalEntity.class))).thenReturn(updatedCupon);

        CuponFinalEntity result = cuponFinalService.updateCuponFinal(id, updatedCupon);

        assertNotNull(result);
        assertEquals("Campo De Updated", result.getCampoDe());
        assertEquals(1500, result.getPrecioF());
        verify(cuponFinalRepository, times(1)).findById(id);
        verify(cuponFinalRepository, times(1)).save(existingCupon);
    }

    // 6. Actualizar precio de un cupón final
    @Test
    void testUpdatePrecio() throws Exception {
        Long id = 1L;
        int nuevoPrecioF = 1500;

        CuponFinalEntity existingCupon = new CuponFinalEntity(id, "Campo De", "Campo Para", "Campo Incluye", LocalDate.of(2024, 11, 1), 1L, 1L, 1L, 1000, null);

        when(cuponFinalRepository.findById(id)).thenReturn(Optional.of(existingCupon));
        when(cuponFinalRepository.save(any(CuponFinalEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CuponFinalEntity result = cuponFinalService.updatePrecio(id, nuevoPrecioF);

        assertNotNull(result);
        assertEquals(nuevoPrecioF, result.getPrecioF());
        verify(cuponFinalRepository, times(1)).findById(id);
        verify(cuponFinalRepository, times(1)).save(existingCupon);
    }

    // 7. Eliminar un cupón final
    @Test
    void testDeleteCuponFinal() throws Exception {
        Long id = 1L;

        doNothing().when(cuponFinalRepository).deleteById(id);

        boolean result = cuponFinalService.deleteCuponFinal(id);

        assertTrue(result);
        verify(cuponFinalRepository, times(1)).deleteById(id);
    }

    // 8. Eliminar un cupón final inexistente
    @Test
    void testDeleteCuponFinal_NotFound() {
        Long id = 1L;

        doThrow(new RuntimeException("Cupon no encontrado")).when(cuponFinalRepository).deleteById(id);

        Exception exception = assertThrows(Exception.class, () -> cuponFinalService.deleteCuponFinal(id));

        assertEquals("Cupon no encontrado", exception.getMessage());
        verify(cuponFinalRepository, times(1)).deleteById(id);
    }
}