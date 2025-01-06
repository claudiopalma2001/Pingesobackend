package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import com.proyecto7.docedeseosbackend.repository.IdiomaRepository;
import com.proyecto7.docedeseosbackend.service.IdiomaService;
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

public class IdiomaServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    IdiomaRepository idiomaRepository;

    @InjectMocks
    IdiomaService idiomaService;

    @Test
    void testGetIdiomas() {
        IdiomaEntity idioma1 = new IdiomaEntity(1L, "Español");
        IdiomaEntity idioma2 = new IdiomaEntity(2L, "Inglés");
        List<IdiomaEntity> idiomasList = new ArrayList<>(List.of(idioma1, idioma2));

        when(idiomaRepository.findAll()).thenReturn(idiomasList);
        List<IdiomaEntity> result = idiomaService.getIdiomas();

        assertEquals(2, result.size());
        verify(idiomaRepository, times(1)).findAll();
    }

    @Test
    void testGetIdiomaById() {
        Long id = 1L;
        IdiomaEntity idioma = new IdiomaEntity(id, "Español");

        when(idiomaRepository.findById(id)).thenReturn(Optional.of(idioma));
        IdiomaEntity result = idiomaService.getIdiomaById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(idiomaRepository, times(1)).findById(id);
    }

    @Test
    void testGetIdiomaByIdNotFound() {
        Long id = 1L;

        when(idiomaRepository.findById(id)).thenReturn(Optional.empty());
        IdiomaEntity result = idiomaService.getIdiomaById(id);

        assertNull(result);
        verify(idiomaRepository, times(1)).findById(id);
    }

    @Test
    void testGetIdiomaByNombre() {
        String nombreIdioma = "Español";
        IdiomaEntity idioma = new IdiomaEntity(1L, nombreIdioma);

        when(idiomaRepository.findByNombreIdioma(nombreIdioma)).thenReturn(idioma);
        IdiomaEntity result = idiomaService.getIdiomaByNombre(nombreIdioma);

        assertNotNull(result);
        assertEquals(nombreIdioma, result.getNombreIdioma());
        verify(idiomaRepository, times(1)).findByNombreIdioma(nombreIdioma);
    }

    @Test
    void testSaveIdioma() {
        IdiomaEntity idioma = new IdiomaEntity(1L, "Español");

        when(idiomaRepository.save(any(IdiomaEntity.class))).thenReturn(idioma);
        IdiomaEntity savedIdioma = idiomaService.saveIdioma(idioma);

        assertNotNull(savedIdioma);
        assertEquals("Español", savedIdioma.getNombreIdioma());
    }

    @Test
    void testUpdateIdioma() throws Exception {
        Long id = 1L;
        IdiomaEntity existingIdioma = new IdiomaEntity(id, "Español");
        IdiomaEntity updatedData = new IdiomaEntity(id, "Inglés");

        when(idiomaRepository.findById(id)).thenReturn(Optional.of(existingIdioma));
        when(idiomaRepository.save(any(IdiomaEntity.class))).thenReturn(updatedData);

        IdiomaEntity result = idiomaService.updateIdioma(id, updatedData);

        assertNotNull(result);
        assertEquals("Inglés", result.getNombreIdioma());
        verify(idiomaRepository, times(1)).findById(id);
        verify(idiomaRepository, times(1)).save(existingIdioma);
    }

    @Test
    void testUpdateIdiomaNotFound() {
        Long id = 1L;
        IdiomaEntity updatedData = new IdiomaEntity(id, "Inglés");

        when(idiomaRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> idiomaService.updateIdioma(id, updatedData));
        assertEquals("Idioma no encontrado", exception.getMessage());
        verify(idiomaRepository, times(1)).findById(id);
        verify(idiomaRepository, times(0)).save(any(IdiomaEntity.class));
    }

    @Test
    void testDeleteIdioma() throws Exception {
        Long id = 1L;

        assertDoesNotThrow(() -> idiomaService.deleteIdioma(id));

        verify(idiomaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteIdiomaNotFound() throws Exception{
        Long id = 1L;

        doNothing().when(idiomaRepository).deleteById(id);

        boolean resultado = idiomaService.deleteIdioma(id);

        assertEquals( true, resultado);

        verify(idiomaRepository, times(1)).deleteById(id);
    }
}
