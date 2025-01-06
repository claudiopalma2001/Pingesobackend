package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.UsuarioController;
import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.responses.Login;
import com.proyecto7.docedeseosbackend.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void listarUsuarios_ShouldReturnUsuarios() throws Exception {

        UsuarioEntity usuario1 = new UsuarioEntity(
                1L,
                "Francisca",
                "francisca@example.com",
                "password123",
                21,
                "Basico",
                0
        );

        UsuarioEntity usuario2 = new UsuarioEntity(
                2L,
                "Pedro",
                "pedro@example.com",
                "password456",
                27,
                "Basico",
                0
        );

        List<UsuarioEntity> usuarios = new ArrayList<>(Arrays.asList(usuario1, usuario2));

        given(usuarioService.getUsuarios()).willReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Francisca")))
                .andExpect(jsonPath("$[1].nombre", is("Pedro")));
    }

    @Test
    public void getUsuarioById_ShouldReturnUsuario() throws Exception {

        UsuarioEntity usuario = new UsuarioEntity(
                1L,
                "Pedro",
                "pedro@example.com",
                "password456",
                27,
                "Premium",
                0
        );

        given(usuarioService.getUsuarioById(1L)).willReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", is("Pedro")));
    }

    @Test
    public void getUsuarioByCorreo_ShouldReturnUsuario() throws Exception {

        UsuarioEntity usuario = new UsuarioEntity(
                1L,
                "Joaquin",
                "joaquin@example.com",
                "contrasegura21",
                33,
                "Premium",
                1
        );

        given(usuarioService.getUsuarioByCorreo("joaquin@example.com")).willReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/correo/{correo}", "joaquin@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", is("Joaquin")));
    }

    @Test
    public void getUsuariosByEdad_ShouldReturnUsuarios() throws Exception {

        UsuarioEntity usuario1 = new UsuarioEntity(
                1L,
                "Javiera",
                "javiera@example.com",
                "palabra1982",
                27,
                "Basico",
                0
        );

        UsuarioEntity usuario2 = new UsuarioEntity(
                2L,
                "Juan",
                "juan@example.com",
                "ejemplo654",
                27,
                "Premium",
                1
        );

        List<UsuarioEntity> usuarios = new ArrayList<>(Arrays.asList(usuario1, usuario2));

        given(usuarioService.getUsuariosByEdad(27)).willReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios/edad/{edad}", 27))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Javiera")))
                .andExpect(jsonPath("$[1].nombre", is("Juan")));
    }

    @Test
    public void getUsuariosByPlanUsuario_ShouldReturnUsuarios() throws Exception {

        UsuarioEntity usuario1 = new UsuarioEntity(
                1L,
                "Claudio",
                "claudio@example.com",
                "windsw0rd20",
                23,
                "Basico",
                0
        );

        UsuarioEntity usuario2 = new UsuarioEntity(
                2L,
                "Agustin",
                "agustin@example.com",
                "ejemplo654",
                27,
                "Basico",
                1
        );

        List<UsuarioEntity> usuarios = new ArrayList<>(Arrays.asList(usuario1, usuario2));

        given(usuarioService.getUsuariosByPlanUsuario("Basico")).willReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios/planUsuario/{planUsuario}", "Basico"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Claudio")))
                .andExpect(jsonPath("$[1].nombre", is("Agustin")));
    }

    @Test
    public void saveUsuario_ShouldReturnSavedUsuario() throws Exception {

        UsuarioEntity usuarioNuevo = new UsuarioEntity(
                1L,
                "Nicolas",
                "nicolas@example.com",
                "testpass123",
                29,
                "Basico",
                0
        );

        given(usuarioService.saveUsuario(Mockito.any(UsuarioEntity.class))).willReturn(usuarioNuevo);

        String usuarioJson = """
            {
                "nombre": "Nicolas",
                "correo": "nicolas@example.com",
                "password": "testpass123",
                "edad": 29,
                "planUsuario": "Basico",
                "idRol": 0
            }
        """;

        mockMvc.perform(post("/api/v1/usuarios/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Nicolas")));
    }

    @Test
    public void updateUsuario_ShouldReturnUpdatedUsuario() throws Exception {

        Long id = 1L;
        UsuarioEntity usuarioActualizado = new UsuarioEntity(
                id,
                "Sebastian",
                "sebastian@example.com",
                "passtest1980",
                42,
                "Premium",
                1
        );

        given(usuarioService.updateUsuario(eq(id), Mockito.any(UsuarioEntity.class))).willReturn(usuarioActualizado);

        String usuarioJson = """
                {
                    "nombre": "Sebastian",
                    "correo": "sebastian@example.com",
                    "password": "passtest1980",
                    "edad": 42,
                    "planUsuario": "Premium",
                    "idRol": 1
                }
            """;

        mockMvc.perform(put("/api/v1/usuarios/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Sebastian")))
                .andExpect(jsonPath("$.correo", is("sebastian@example.com")))
                .andExpect(jsonPath("$.edad", is(42)))
                .andExpect(jsonPath("$.planUsuario", is("Premium")))
                .andExpect(jsonPath("$.idRol", is(1)));

        verify(usuarioService, times(1)).updateUsuario(eq(id), Mockito.any(UsuarioEntity.class));
    }


        @Test
    public void deleteUsuarioById_ShouldReturn204() throws Exception {
        when(usuarioService.deleteUsuario(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void login_ShouldReturnLoginResponse() throws Exception {

        // Objeto de prueba para la respuesta de login
        Login loginResponse = new Login(true, "token123");

        // Formulario de inicio de sesión de prueba
        LoginForm form = new LoginForm("nicolas@example.com", "testpass123");

        // Simulación del servicio de login
        given(usuarioService.login(Mockito.any(LoginForm.class))).willReturn(loginResponse);

        // JSON del formulario de inicio de sesión
        String loginFormJson = """
        {
            "correo": "nicolas@example.com",
            "password": "testpass123"
        }
        """;

        // Prueba del controlador
        mockMvc.perform(post("/api/v1/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginFormJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt", is("token123"))); // Asegura que el token es el esperado
    }

}
