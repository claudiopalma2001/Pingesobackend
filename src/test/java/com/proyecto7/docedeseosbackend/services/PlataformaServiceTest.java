package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import com.proyecto7.docedeseosbackend.repository.PlataformaRepository;
import com.proyecto7.docedeseosbackend.service.PlataformaService;
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

public class PlataformaServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    PlataformaRepository plataformaRepository;

    @InjectMocks
    PlataformaService plataformaService;

    @Test
    void testGetPlataformas() {
        PlataformaEntity plataforma1 = new PlataformaEntity(1L, "Plataforma A");
        PlataformaEntity plataforma2 = new PlataformaEntity(2L, "Plataforma B");
        List<PlataformaEntity> plataformasList = new ArrayList<>(List.of(plataforma1, plataforma2));

        when(plataformaRepository.findAll()).thenReturn(plataformasList);
        List<PlataformaEntity> result = plataformaService.getPlataformas();

        assertEquals(2, result.size());
        verify(plataformaRepository, times(1)).findAll();
    }

    @Test
    void testGetPlataformaById() {
        Long id = 1L;
        PlataformaEntity plataforma = new PlataformaEntity(id, "Plataforma A");

        when(plataformaRepository.findById(id)).thenReturn(Optional.of(plataforma));
        PlataformaEntity result = plataformaService.getPlataformaById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(plataformaRepository, times(1)).findById(id);
    }

    @Test
    void testSavePlataforma() {
        PlataformaEntity plataforma = new PlataformaEntity(1L, "Plataforma A");

        when(plataformaRepository.save(any(PlataformaEntity.class))).thenReturn(plataforma);
        PlataformaEntity savedPlataforma = plataformaService.savePlataforma(plataforma);

        assertNotNull(savedPlataforma);
        assertEquals("Plataforma A", savedPlataforma.getTipoPlataforma());
        verify(plataformaRepository, times(1)).save(plataforma);
    }

    @Test
    void testDeletePlataforma() throws Exception {
        Long id = 1L;

        doNothing().when(plataformaRepository).deleteById(id);
        assertTrue(plataformaService.deletePlataforma(id));

        verify(plataformaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePlataforma_NotFound() {
        Long id = 1L;

        doThrow(new RuntimeException("Plataforma no encontrada")).when(plataformaRepository).deleteById(id);

        Exception exception = assertThrows(Exception.class, () -> plataformaService.deletePlataforma(id));
        assertEquals("Plataforma no encontrada", exception.getMessage());

        verify(plataformaRepository, times(1)).deleteById(id);
    }
}
