package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.repository.UsuarioRepository;
import com.proyecto7.docedeseosbackend.responses.Login;
import com.proyecto7.docedeseosbackend.service.JWTMiddlewareService;
import com.proyecto7.docedeseosbackend.service.UsuarioService;
import com.proyecto7.docedeseosbackend.utils.Encrypter;
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

public class UsuarioServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    JWTMiddlewareService JWT;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void testGetUsuarios() {
        UsuarioEntity usuario1 = new UsuarioEntity(1L, "Nicolas", "nicolas@example.com", "pass123", 29, "Basico", 0);
        UsuarioEntity usuario2 = new UsuarioEntity(2L, "Maria", "maria@example.com", "pass456", 25, "Premium", 1);
        List<UsuarioEntity> usuariosList = new ArrayList<>(List.of(usuario1, usuario2));

        when(usuarioRepository.findAll()).thenReturn(usuariosList);
        List<UsuarioEntity> result = usuarioService.getUsuarios();

        assertEquals(2, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsuarioById() {
        Long id = 1L;
        UsuarioEntity usuario = new UsuarioEntity(id, "Nicolas", "nicolas@example.com", "pass123", 29, "Basico", 0);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        UsuarioEntity result = usuarioService.getUsuarioById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void testGetUsuarioByCorreo() {
        String correo = "nicolas@example.com";
        UsuarioEntity usuario = new UsuarioEntity(1L, "Nicolas", correo, "pass123", 29, "Basico", 0);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(usuario);
        UsuarioEntity result = usuarioService.getUsuarioByCorreo(correo);

        assertNotNull(result);
        assertEquals(correo, result.getCorreo());
        verify(usuarioRepository, times(1)).findByCorreo(correo);
    }

    @Test
    void testGetUsuariosByEdad() {
        int edad = 29;
        UsuarioEntity usuario = new UsuarioEntity(1L, "Nicolas", "nicolas@example.com", "pass123", edad, "Basico", 0);
        List<UsuarioEntity> usuariosList = new ArrayList<>(List.of(usuario));

        when(usuarioRepository.findByEdad(edad)).thenReturn(usuariosList);
        List<UsuarioEntity> result = usuarioService.getUsuariosByEdad(edad);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(edad, result.get(0).getEdad());
        verify(usuarioRepository, times(1)).findByEdad(edad);
    }

    @Test
    void testSaveUsuario() {
        UsuarioEntity usuario = new UsuarioEntity(1L, "Nicolas", "nicolas@example.com", "pass123", 29, "Basico", 0);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);
        UsuarioEntity savedUsuario = usuarioService.save(usuario);

        assertNotNull(savedUsuario);
        assertEquals("Nicolas", savedUsuario.getNombre());
        assertEquals("nicolas@example.com", savedUsuario.getCorreo());
    }

    @Test
    void testUpdateUsuario() throws Exception {
        Long id = 1L;
        UsuarioEntity existingUsuario = new UsuarioEntity(id, "Nicolas", "nicolas@example.com", "password123", 29, "Basico", 0);
        UsuarioEntity updatedData = new UsuarioEntity(id, "Nicolas Updated", "nicolas_updated@example.com", "newpassword", 30, "Premium", 1);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(existingUsuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(updatedData);

        UsuarioEntity result = usuarioService.updateUsuario(id, updatedData);

        assertNotNull(result);
        assertEquals("Nicolas Updated", result.getNombre());
        assertEquals("nicolas_updated@example.com", result.getCorreo());
        assertEquals("newpassword", result.getPassword());
        assertEquals(30, result.getEdad());
        assertEquals("Premium", result.getPlanUsuario());
        assertEquals(1, result.getIdRol());

        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(existingUsuario);
    }

    @Test
    void testUpdateUsuarioNotFound() {
        Long id = 1L;
        UsuarioEntity updatedData = new UsuarioEntity(id, "Nicolas Updated", "nicolas_updated@example.com", "newpassword", 30, "Premium", 1);

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> usuarioService.updateUsuario(id, updatedData));
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(0)).save(any(UsuarioEntity.class));
    }

    @Test
    void testLoginSuccess() {
        String correo = "nicolas@example.com";
        String password = "testpass123";
        UsuarioEntity usuario = new UsuarioEntity(1L, "Nicolas", correo, Encrypter.encrypt(password, correo), 29, "Basico", 0);
        LoginForm form = new LoginForm(correo, password);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(usuario);
        when(JWT.generateToken(form)).thenReturn("jwt-token");

        Login result = usuarioService.login(form);

        assertTrue(result.isSuccess());
        assertEquals("jwt-token", result.getJwt());
    }

    @Test
    void testLoginFail_InvalidPassword() {
        String correo = "nicolas@example.com";
        UsuarioEntity usuario = new UsuarioEntity(1L, "Nicolas", correo, Encrypter.encrypt("correctpass", correo), 29, "Basico", 0);
        LoginForm form = new LoginForm(correo, "wrongpass");

        when(usuarioRepository.findByCorreo(correo)).thenReturn(usuario);

        Login result = usuarioService.login(form);

        assertFalse(result.isSuccess());
        assertNull(result.getJwt());
    }

    @Test
    void testDeleteUsuario() {
        Long id = 1L;

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(id));

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
