package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.TematicaController;
import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import com.proyecto7.docedeseosbackend.service.TematicaService;
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
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TematicaController.class)
public class TematicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TematicaService tematicaService;

    @Test
    public void listarTematicas_ShouldReturnTematicas() throws Exception {

        TematicaEntity tematica1 = new TematicaEntity(1L, "Tematica 1", "Descripción de la temática 1");
        TematicaEntity tematica2 = new TematicaEntity(2L, "Tematica 2", "Descripción de la temática 2");

        List<TematicaEntity> tematicas = new ArrayList<>(Arrays.asList(tematica1, tematica2));

        given(tematicaService.getAllTematicas()).willReturn(tematicas);

        mockMvc.perform(get("/api/v1/tematicas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreTematica", is("Tematica 1")))
                .andExpect(jsonPath("$[1].nombreTematica", is("Tematica 2")));
    }

    @Test
    public void getTematicaById_ShouldReturnTematica() throws Exception {

        TematicaEntity tematica = new TematicaEntity(1L, "Tematica 1", "Descripción de la temática 1");

        given(tematicaService.getTematicaById(1L)).willReturn(Optional.of(tematica));

        mockMvc.perform(get("/api/v1/tematicas/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreTematica", is("Tematica 1")));
    }

    @Test
    public void getTematicaById_ShouldReturnNotFound() throws Exception {

        given(tematicaService.getTematicaById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/tematicas/id/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveTematica_ShouldReturnSavedTematica() throws Exception {

        TematicaEntity tematicaNueva = new TematicaEntity(1L, "Tematica Nueva", "Descripción de la nueva temática");

        given(tematicaService.save(Mockito.any(TematicaEntity.class))).willReturn(tematicaNueva);

        String tematicaJson = """
            {
                "nombre": "Tematica Nueva",
                "descripcion": "Descripción de la nueva temática"
            }
        """;

        mockMvc.perform(post("/api/v1/tematicas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tematicaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreTematica", is("Tematica Nueva")));
    }

    @Test
    public void updateTematica_ShouldReturnUpdatedTematica() throws Exception {

        Long id = 1L;
        TematicaEntity tematicaActualizada = new TematicaEntity(id, "Tematica Actualizada", "Descripción actualizada");

        given(tematicaService.updateTematica(eq(id), Mockito.any(TematicaEntity.class))).willReturn(tematicaActualizada);

        String tematicaJson = """
                {
                    "nombre": "Tematica Actualizada",
                    "descripcion": "Descripción actualizada"
                }
            """;

        mockMvc.perform(put("/api/v1/tematicas/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tematicaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreTematica", is("Tematica Actualizada")))
                .andExpect(jsonPath("$.descripcion", is("Descripción actualizada")));

        verify(tematicaService, times(1)).updateTematica(eq(id), Mockito.any(TematicaEntity.class));
    }

    @Test
    public void deleteTematicaById_ShouldReturn204() throws Exception {

        when(tematicaService.deleteTematica(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/tematicas/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTematicaById_ShouldReturn500() throws Exception {

        when(tematicaService.deleteTematica(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/tematicas/delete/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }
}
