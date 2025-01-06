package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.RolEntity;
import com.proyecto7.docedeseosbackend.repository.RolRepository;
import com.proyecto7.docedeseosbackend.service.RolService;
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

public class RolServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    RolRepository rolRepository;

    @InjectMocks
    RolService rolService;

    @Test
    void testGetRoles() {
        RolEntity rol1 = new RolEntity(1L, "Usuario");
        RolEntity rol2 = new RolEntity(2L, "Administrador");
        List<RolEntity> rolesList = new ArrayList<>(List.of(rol1, rol2));

        when(rolRepository.findAll()).thenReturn(rolesList);
        List<RolEntity> result = rolService.getRoles();

        assertEquals(2, result.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void testGetRolById() {
        Long id = 1L;
        RolEntity rol = new RolEntity(id, "Usuario");

        when(rolRepository.findById(id)).thenReturn(Optional.of(rol));
        RolEntity result = rolService.getRolById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(rolRepository, times(1)).findById(id);
    }

    @Test
    void testGetRolByNombre() {
        String nombreRol = "Administrador";
        RolEntity rol = new RolEntity(1L, nombreRol);

        when(rolRepository.findByNombreRol(nombreRol)).thenReturn(Optional.of(rol));
        Optional<RolEntity> result = rolService.getRolByNombre(nombreRol);

        assertTrue(result.isPresent());
        assertEquals(nombreRol, result.get().getNombreRol());
        verify(rolRepository, times(1)).findByNombreRol(nombreRol);
    }

    @Test
    void testSaveRol() {
        RolEntity rol = new RolEntity(1L, "Usuario");

        when(rolRepository.save(any(RolEntity.class))).thenReturn(rol);
        RolEntity savedRol = rolService.save(rol);

        assertNotNull(savedRol);
        assertEquals("Usuario", savedRol.getNombreRol());
    }

    @Test
    void testDeleteRol() throws Exception {
        Long id = 1L;

        doNothing().when(rolRepository).deleteById(id);

        assertTrue(rolService.deleteRol(id));
        verify(rolRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRolThrowsException() {
        Long id = 1L;

        doThrow(new RuntimeException("Error al eliminar")).when(rolRepository).deleteById(id);

        Exception exception = assertThrows(Exception.class, () -> rolService.deleteRol(id));
        assertEquals("Error al eliminar", exception.getMessage());
    }
}
