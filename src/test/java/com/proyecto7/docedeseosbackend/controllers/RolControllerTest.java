package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.RolController;
import com.proyecto7.docedeseosbackend.entity.RolEntity;
import com.proyecto7.docedeseosbackend.service.RolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Test
    public void listarRoles_ShouldReturnRoles() throws Exception {

        RolEntity rol1 = new RolEntity(1L, "Usuario");
        RolEntity rol2 = new RolEntity(2L, "Administrador");

        List<RolEntity> roles = Arrays.asList(rol1, rol2);

        given(rolService.getRoles()).willReturn(roles);

        mockMvc.perform(get("/api/v1/roles/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreRol", is("Usuario")))
                .andExpect(jsonPath("$[1].nombreRol", is("Administrador")));
    }

    @Test
    public void getRolById_ShouldReturnRol() throws Exception {

        RolEntity rol = new RolEntity(1L, "Usuario");

        given(rolService.getRolById(1L)).willReturn(rol);

        mockMvc.perform(get("/api/v1/roles/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreRol", is("Usuario")));
    }

    @Test
    public void saveRol_ShouldReturnSavedRol() throws Exception {

        RolEntity rolNuevo = new RolEntity(null, "Usuario");
        RolEntity rolGuardado = new RolEntity(1L, "Usuario");

        given(rolService.save(rolNuevo)).willReturn(rolGuardado);

        String rolJson = """
            {
                "nombreRol": "Usuario"
            }
        """;

        mockMvc.perform(post("/api/v1/roles/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rolJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol", is("Usuario")));
    }

    @Test
    public void deleteRolById_ShouldReturnNoContent() throws Exception {

        given(rolService.deleteRol(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/roles/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
