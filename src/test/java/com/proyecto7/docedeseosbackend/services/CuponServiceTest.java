package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.repository.CuponRepository;
import com.proyecto7.docedeseosbackend.service.CuponService;
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

public class CuponServiceTest {

    @Mock
    CuponRepository cuponRepository;

    @InjectMocks
    CuponService cuponService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCupones() {
        CuponEntity cupon1 = new CuponEntity(1L, "Cupon1", "Premium", 1, 1000);
        CuponEntity cupon2 = new CuponEntity(2L, "Cupon2", "Free", 2, 1000);
        List<CuponEntity> cuponesList = new ArrayList<>(List.of(cupon1, cupon2));

        when(cuponRepository.findAll()).thenReturn(cuponesList);
        List<CuponEntity> result = cuponService.getCupones();

        assertEquals(2, result.size());
        verify(cuponRepository, times(1)).findAll();
    }

    @Test
    void testGetCuponById() {
        Long id = 1L;
        CuponEntity cupon = new CuponEntity(id, "Cupon1", "Premium", 1, 1000);

        when(cuponRepository.findById(id)).thenReturn(Optional.of(cupon));
        CuponEntity result = cuponService.getCuponById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(cuponRepository, times(1)).findById(id);
    }

    @Test
    void testGetCuponByNombre() {
        String nombreCupon = "Cupon1";
        CuponEntity cupon = new CuponEntity(1L, nombreCupon, "Premium", 1, 1000);

        when(cuponRepository.findByNombreCupon(nombreCupon)).thenReturn(cupon);
        CuponEntity result = cuponService.getCuponByNombre(nombreCupon);

        assertNotNull(result);
        assertEquals(nombreCupon, result.getNombreCupon());
        verify(cuponRepository, times(1)).findByNombreCupon(nombreCupon);
    }

    @Test
    void testGetCuponesByTipo() {
        String tipo = "Premium";
        CuponEntity cupon = new CuponEntity(1L, "Cupon1", tipo, 1, 1000);
        List<CuponEntity> cuponesList = new ArrayList<>(List.of(cupon));

        when(cuponRepository.findByTipo(tipo)).thenReturn(cuponesList);
        List<CuponEntity> result = cuponService.getCuponesByTipo(tipo);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tipo, result.get(0).getTipo());
        verify(cuponRepository, times(1)).findByTipo(tipo);
    }

    @Test
    void testGetCuponesByIdTematica() {
        int idTematica = 1;
        CuponEntity cupon = new CuponEntity(1L, "Cupon1", "Premium", idTematica, 1000);
        List<CuponEntity> cuponesList = new ArrayList<>(List.of(cupon));

        when(cuponRepository.findByIdTematica(idTematica)).thenReturn(cuponesList);
        List<CuponEntity> result = cuponService.getCuponesByIdTematica(idTematica);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(idTematica, result.get(0).getIdTematica());
        verify(cuponRepository, times(1)).findByIdTematica(idTematica);
    }

    @Test
    void testSaveCupon() {
        CuponEntity cupon = new CuponEntity(1L, "Cupon1", "Premium", 1, 1000);

        when(cuponRepository.save(any(CuponEntity.class))).thenReturn(cupon);
        CuponEntity savedCupon = cuponService.save(cupon);

        assertNotNull(savedCupon);
        assertEquals("Cupon1", savedCupon.getNombreCupon());
        assertEquals("Premium", savedCupon.getTipo());
    }

    @Test
    void testUpdateCupon() throws Exception {
        Long id = 1L;
        CuponEntity existingCupon = new CuponEntity(id, "Cupon1", "Premium", 1, 1000);
        CuponEntity updatedData = new CuponEntity(id, "Cupon Actualizado", "Free", 2, 1000);

        when(cuponRepository.findById(id)).thenReturn(Optional.of(existingCupon));
        when(cuponRepository.save(any(CuponEntity.class))).thenReturn(updatedData);

        CuponEntity result = cuponService.updateCupon(id, updatedData);

        assertNotNull(result);
        assertEquals("Cupon Actualizado", result.getNombreCupon());
        assertEquals("Free", result.getTipo());
        assertEquals(2, result.getIdTematica());

        verify(cuponRepository, times(1)).findById(id);
        verify(cuponRepository, times(1)).save(existingCupon);
    }

    @Test
    void testUpdateCuponNotFound() throws Exception {
        Long id = 1L;
        CuponEntity updatedData = new CuponEntity(id, "Cupon Actualizado", "Free", 2, 1000);

        when(cuponRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> cuponService.updateCupon(id, updatedData));
        assertEquals("Cupón no encontrado", exception.getMessage());
        verify(cuponRepository, times(1)).findById(id);
        verify(cuponRepository, times(0)).save(any(CuponEntity.class));
    }

    @Test
    void testUpdatePrecio() throws Exception {
        Long id = 1L;
        int nuevoPrecio = 1500;
        CuponEntity existingCupon = new CuponEntity(id, "Cupon1", "Premium", 1, 1000);

        // Simula encontrar el cupón existente
        when(cuponRepository.findById(id)).thenReturn(Optional.of(existingCupon));
        // Simula guardar el cupón actualizado
        when(cuponRepository.save(any(CuponEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llama al método del servicio para actualizar el precio
        CuponEntity updatedCupon = cuponService.updatePrecio(id, nuevoPrecio);

        // Verificaciones
        assertNotNull(updatedCupon);
        assertEquals(nuevoPrecio, updatedCupon.getPrecio());
        verify(cuponRepository, times(1)).findById(id);
        verify(cuponRepository, times(1)).save(existingCupon);
    }


    @Test
    void testDeleteCupon() throws Exception {
        Long id = 1L;

        assertDoesNotThrow(() -> cuponService.deleteCupon(id));

        verify(cuponRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdatePrecioInvalid() {
        Long id = 1L;
        int precioInvalido = -1000;

        // No es necesario configurar mocks porque la validación ocurre antes de llamar al repositorio
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cuponService.updatePrecio(id, precioInvalido));
        assertEquals("El precio no puede ser negativo", exception.getMessage());

        // Verifica que no se interactuó con el repositorio
        verify(cuponRepository, times(0)).findById(anyLong());
        verify(cuponRepository, times(0)).save(any(CuponEntity.class));
    }

    @Test
    void testUpdatePrecioUnexpectedException() {
        Long id = 1L;
        int nuevoPrecio = 1500;

        when(cuponRepository.findById(id)).thenThrow(new RuntimeException("Error inesperado"));

        Exception exception = assertThrows(RuntimeException.class, () -> cuponService.updatePrecio(id, nuevoPrecio));
        assertEquals("Error inesperado", exception.getMessage());

        verify(cuponRepository, times(1)).findById(id);
        verify(cuponRepository, times(0)).save(any(CuponEntity.class));
    }



}
