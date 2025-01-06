package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.IdiomaController;
import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import com.proyecto7.docedeseosbackend.service.IdiomaService;
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

@WebMvcTest(IdiomaController.class)
public class IdiomaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdiomaService idiomaService;

    @Test
    public void listarIdiomas_ShouldReturnIdiomas() throws Exception {
        IdiomaEntity idioma1 = new IdiomaEntity(1L, "Español");
        IdiomaEntity idioma2 = new IdiomaEntity(2L, "Inglés");

        List<IdiomaEntity> idiomas = new ArrayList<>(Arrays.asList(idioma1, idioma2));

        given(idiomaService.getIdiomas()).willReturn(idiomas);

        mockMvc.perform(get("/api/v1/idiomas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreIdioma", is("Español")))
                .andExpect(jsonPath("$[1].nombreIdioma", is("Inglés")));
    }

    @Test
    public void getIdiomaById_ShouldReturnIdioma() throws Exception {
        IdiomaEntity idioma = new IdiomaEntity(1L, "Español");

        given(idiomaService.getIdiomaById(1L)).willReturn(idioma);

        mockMvc.perform(get("/api/v1/idiomas/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreIdioma", is("Español")));
    }

    @Test
    public void getIdiomaByNombre_ShouldReturnIdioma() throws Exception {
        IdiomaEntity idioma = new IdiomaEntity(1L, "Español");

        given(idiomaService.getIdiomaByNombre("Español")).willReturn(idioma);

        mockMvc.perform(get("/api/v1/idiomas/nombre/{nombreIdioma}", "Español"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreIdioma", is("Español")));
    }

    @Test
    public void saveIdioma_ShouldReturnSavedIdioma() throws Exception {
        IdiomaEntity idiomaNuevo = new IdiomaEntity(1L, "Francés");

        given(idiomaService.saveIdioma(Mockito.any(IdiomaEntity.class))).willReturn(idiomaNuevo);

        String idiomaJson = """
            {
                "nombre": "Francés"
            }
        """;

        mockMvc.perform(post("/api/v1/idiomas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idiomaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreIdioma", is("Francés")));
    }

    @Test
    public void updateIdioma_ShouldReturnUpdatedIdioma() throws Exception {
        Long id = 1L;
        IdiomaEntity idiomaActualizado = new IdiomaEntity(id, "Alemán");

        given(idiomaService.updateIdioma(eq(id), Mockito.any(IdiomaEntity.class))).willReturn(idiomaActualizado);

        String idiomaJson = """
            {
                "nombre": "Alemán"
            }
        """;

        mockMvc.perform(put("/api/v1/idiomas/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idiomaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreIdioma", is("Alemán")));

        verify(idiomaService, times(1)).updateIdioma(eq(id), Mockito.any(IdiomaEntity.class));
    }

    @Test
    public void deleteIdiomaById_ShouldReturn204() throws Exception {
        when(idiomaService.deleteIdioma(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/idiomas/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
