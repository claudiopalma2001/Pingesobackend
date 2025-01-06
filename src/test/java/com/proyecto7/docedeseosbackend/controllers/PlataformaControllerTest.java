package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.PlataformaController;
import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import com.proyecto7.docedeseosbackend.service.PlataformaService;
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

@WebMvcTest(PlataformaController.class)
public class PlataformaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlataformaService plataformaService;

    @Test
    public void listarPlataformas_ShouldReturnPlataformas() throws Exception {

        PlataformaEntity plataforma1 = new PlataformaEntity(1L, "Web");
        PlataformaEntity plataforma2 = new PlataformaEntity(2L, "Movil");

        List<PlataformaEntity> plataformas = new ArrayList<>(Arrays.asList(plataforma1, plataforma2));

        given(plataformaService.getPlataformas()).willReturn(plataformas);

        mockMvc.perform(get("/api/v1/plataforma/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipoPlataforma", is("Web")))
                .andExpect(jsonPath("$[1].tipoPlataforma", is("Movil")));
    }

    @Test
    public void getPlataformaById_ShouldReturnPlataforma() throws Exception {

        PlataformaEntity plataforma = new PlataformaEntity(1L, "Web");

        given(plataformaService.getPlataformaById(1L)).willReturn(plataforma);

        mockMvc.perform(get("/api/v1/plataforma/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tipoPlataforma", is("Web")));
    }

    @Test
    public void savePlataforma_ShouldReturnSavedPlataforma() throws Exception {

        PlataformaEntity plataformaNueva = new PlataformaEntity(2L, "Movil");

        given(plataformaService.savePlataforma(Mockito.any(PlataformaEntity.class))).willReturn(plataformaNueva);

        String plataformaJson = """
            {
                "tipoPlataforma": "Movil"
            }
        """;

        mockMvc.perform(post("/api/v1/plataforma/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plataformaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoPlataforma", is("Movil")));
    }

    @Test
    public void deletePlataformaById_ShouldReturnNoContent() throws Exception {

        Long id = 1L;
        given(plataformaService.deletePlataforma(id)).willReturn(true);

        mockMvc.perform(delete("/api/v1/plataforma/{id}", id))
                .andExpect(status().isNoContent());
    }

}
