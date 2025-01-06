package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import com.proyecto7.docedeseosbackend.repository.TematicaRepository;
import com.proyecto7.docedeseosbackend.service.TematicaService;
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

public class TematicaServiceTest {

    @Mock
    private TematicaRepository tematicaRepository;

    @InjectMocks
    private TematicaService tematicaService;

    @BeforeEach
    public void init() {
        // Inicializar los mocks antes de cada test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTematicas() {
        TematicaEntity tematica1 = new TematicaEntity(1L, "Temática 1", "Descripción 1");
        TematicaEntity tematica2 = new TematicaEntity(2L, "Temática 2", "Descripción 2");
        List<TematicaEntity> tematicaList = new ArrayList<>();
        tematicaList.add(tematica1);
        tematicaList.add(tematica2);

        when(tematicaRepository.findAll()).thenReturn(tematicaList);

        List<TematicaEntity> result = tematicaService.getAllTematicas();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tematicaRepository, times(1)).findAll();
    }

    @Test
    void testGetTematicaById() {
        Long id = 1L;
        TematicaEntity tematica = new TematicaEntity(id, "Temática 1", "Descripción 1");

        when(tematicaRepository.findById(id)).thenReturn(Optional.of(tematica));

        Optional<TematicaEntity> result = tematicaService.getTematicaById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(tematicaRepository, times(1)).findById(id);
    }

    @Test
    void testGetTematicaByIdNotFound() {
        Long id = 1L;

        when(tematicaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<TematicaEntity> result = tematicaService.getTematicaById(id);

        assertFalse(result.isPresent());
        verify(tematicaRepository, times(1)).findById(id);
    }

    @Test
    void testSaveTematica() {
        TematicaEntity tematica = new TematicaEntity(1L, "Temática 1", "Descripción 1");

        when(tematicaRepository.save(tematica)).thenReturn(tematica);

        TematicaEntity result = tematicaService.save(tematica);

        assertNotNull(result);
        assertEquals("Temática 1", result.getNombreTematica());
        assertEquals("Descripción 1", result.getDescripcion());
        verify(tematicaRepository, times(1)).save(tematica);
    }

    @Test
    void testUpdateTematica() throws Exception {
        Long id = 1L;
        TematicaEntity existingTematica = new TematicaEntity(id, "Temática 1", "Descripción 1");
        TematicaEntity updatedData = new TematicaEntity(id, "Temática Actualizada", "Descripción Actualizada");

        when(tematicaRepository.findById(id)).thenReturn(Optional.of(existingTematica));
        when(tematicaRepository.save(any(TematicaEntity.class))).thenReturn(updatedData);

        TematicaEntity result = tematicaService.updateTematica(id, updatedData);

        assertNotNull(result);
        assertEquals("Temática Actualizada", result.getNombreTematica());
        assertEquals("Descripción Actualizada", result.getDescripcion());
        verify(tematicaRepository, times(1)).findById(id);
        verify(tematicaRepository, times(1)).save(any(TematicaEntity.class));
    }

    @Test
    void testUpdateTematicaNotFound() {
        Long id = 1L;
        TematicaEntity updatedData = new TematicaEntity(id, "Temática Actualizada", "Descripción Actualizada");

        when(tematicaRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> tematicaService.updateTematica(id, updatedData));

        assertEquals("Temática no encontrada", exception.getMessage());
        verify(tematicaRepository, times(1)).findById(id);
        verify(tematicaRepository, times(0)).save(any(TematicaEntity.class));
    }

    @Test
    void testDeleteTematica() throws Exception {
        Long id = 1L;

        // No se espera que el método delete lance una excepción
        doNothing().when(tematicaRepository).deleteById(id);  // Aquí es posible simular con el mockeo

        assertTrue(tematicaService.deleteTematica(id));
        verify(tematicaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTematicaThrowsException() throws Exception {
        Long id = 1L;

        doThrow(new RuntimeException("Error al eliminar")).when(tematicaRepository).deleteById(id);

        Exception exception = assertThrows(Exception.class, () -> tematicaService.deleteTematica(id));

        assertEquals("Error al eliminar", exception.getMessage());
        verify(tematicaRepository, times(1)).deleteById(id);
    }
}
