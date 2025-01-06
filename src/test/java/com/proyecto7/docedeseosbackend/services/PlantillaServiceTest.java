package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.repository.PlantillaRepository;
import com.proyecto7.docedeseosbackend.service.PlantillaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlantillaServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    PlantillaRepository plantillaRepository;

    @InjectMocks
    PlantillaService plantillaService;

    @Test
    void testGetAllPlantillas() {
        // Cambié idCupon, idIdioma, idPlataforma a Integer
        PlantillaEntity plantilla1 = new PlantillaEntity(1L, 101, 1, 1, "http://image1.com");
        PlantillaEntity plantilla2 = new PlantillaEntity(2L, 102, 2, 2, "http://image2.com");
        List<PlantillaEntity> plantillasList = List.of(plantilla1, plantilla2);

        when(plantillaRepository.findAll()).thenReturn(plantillasList);
        List<PlantillaEntity> result = plantillaService.getAllPlantillas();

        assertEquals(2, result.size());
        verify(plantillaRepository, times(1)).findAll();
    }

    @Test
    void testGetPlantillaPorId() {
        Long id = 1L;

        PlantillaEntity plantilla = new PlantillaEntity(id, 101, 1, 1, "http://image1.com");

        when(plantillaRepository.findById(id)).thenReturn(Optional.of(plantilla));
        PlantillaEntity result = plantillaService.getPlantillaPorId(id);

        assertNotNull(result);
        assertEquals(101, result.getIdCupon());
        verify(plantillaRepository, times(1)).findById(id);
    }

    @Test
    void testGetPlantillaPorIdNotFound() {
        Long id = 1L;

        when(plantillaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> plantillaService.getPlantillaPorId(id));
        assertEquals("Plantilla no encontrada", exception.getMessage());
        verify(plantillaRepository, times(1)).findById(id);
    }

    @Test
    void testSavePlantilla() {
        // Cambié idCupon, idIdioma, idPlataforma a Integer
        PlantillaEntity plantilla = new PlantillaEntity(1L, 101, 1, 1, "http://image1.com");

        when(plantillaRepository.save(any(PlantillaEntity.class))).thenReturn(plantilla);
        PlantillaEntity savedPlantilla = plantillaService.savePlantilla(plantilla);

        assertNotNull(savedPlantilla);
        assertEquals(101, savedPlantilla.getIdCupon());  // Cambié 1 por 101 aquí
        assertEquals("http://image1.com", savedPlantilla.getUrlImagen());
    }

    @Test
    void testUpdatePlantilla() {
        Long id = 1L;
        // Cambié idCupon, idIdioma, idPlataforma a Integer
        PlantillaEntity existingPlantilla = new PlantillaEntity(id, 101, 1, 1, "http://image1.com");
        PlantillaEntity updatedPlantilla = new PlantillaEntity(id, 102, 2, 2, "http://image2.com");

        when(plantillaRepository.findById(id)).thenReturn(Optional.of(existingPlantilla));
        when(plantillaRepository.save(any(PlantillaEntity.class))).thenReturn(updatedPlantilla);

        PlantillaEntity result = plantillaService.updatePlantilla(id, updatedPlantilla);

        assertNotNull(result);
        assertEquals(102, result.getIdCupon());
        assertEquals("http://image2.com", result.getUrlImagen());
        verify(plantillaRepository, times(1)).findById(id);
        verify(plantillaRepository, times(1)).save(any(PlantillaEntity.class));
    }

    @Test
    void testUpdatePlantillaNotFound() {
        Long id = 1L;
        // Cambié idCupon, idIdioma, idPlataforma a Integer
        PlantillaEntity updatedPlantilla = new PlantillaEntity(id, 102, 2, 2, "http://image2.com");

        when(plantillaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> plantillaService.updatePlantilla(id, updatedPlantilla));
        assertEquals("Plantilla no encontrada", exception.getMessage());
        verify(plantillaRepository, times(1)).findById(id);
        verify(plantillaRepository, times(0)).save(any(PlantillaEntity.class));
    }

    @Test
    void testDeletePlantilla() {
        Long id = 1L;

        when(plantillaRepository.existsById(id)).thenReturn(true);
        plantillaService.deletePlantilla(id);

        verify(plantillaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePlantillaNotFound() {
        Long id = 1L;

        when(plantillaRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> plantillaService.deletePlantilla(id));
        assertEquals("Plantilla no encontrada", exception.getMessage());
        verify(plantillaRepository, times(1)).existsById(id);
        verify(plantillaRepository, times(0)).deleteById(id);
    }
}
