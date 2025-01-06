package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.PlantillaController;
import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.service.CuponService;
import com.proyecto7.docedeseosbackend.service.PlantillaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantillaController.class)
public class PlantillaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantillaService plantillaService;

    @MockBean
    private CuponService cuponService;

    @Test
    public void listarPlantillas_ShouldReturnPlantillas() throws Exception {
        PlantillaEntity plantilla1 = new PlantillaEntity(1L, 1, 1, 2, "http://example.com/imagen1.jpg");
        PlantillaEntity plantilla2 = new PlantillaEntity(2L, 2, 3, 2, "http://example.com/imagen2.jpg");

        given(plantillaService.getAllPlantillas()).willReturn(List.of(plantilla1, plantilla2));

        mockMvc.perform(get("/api/v1/plantillas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].idCupon", is(1)))
                .andExpect(jsonPath("$[0].idIdioma", is(1)))
                .andExpect(jsonPath("$[0].idPlataforma", is(2)))
                .andExpect(jsonPath("$[0].urlImagen", is("http://example.com/imagen1.jpg")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].idCupon", is(2)))
                .andExpect(jsonPath("$[1].idIdioma", is(3)))
                .andExpect(jsonPath("$[1].idPlataforma", is(2)))
                .andExpect(jsonPath("$[1].urlImagen", is("http://example.com/imagen2.jpg")));
    }

    @Test
    public void getPlantillaPorId_ShouldReturnPlantilla() throws Exception {
        PlantillaEntity plantilla = new PlantillaEntity(1L, 1, 1, 2, "http://example.com/imagen1.jpg");

        given(plantillaService.getPlantillaPorId(1L)).willReturn(plantilla);

        mockMvc.perform(get("/api/v1/plantillas/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.idCupon", is(1)))
                .andExpect(jsonPath("$.idIdioma", is(1)))
                .andExpect(jsonPath("$.idPlataforma", is(2)))
                .andExpect(jsonPath("$.urlImagen", is("http://example.com/imagen1.jpg")));
    }

    @Test
    public void getPlantillaPorId_ShouldReturnNotFound() throws Exception {
        given(plantillaService.getPlantillaPorId(1L)).willThrow(new RuntimeException("No encontrada"));

        mockMvc.perform(get("/api/v1/plantillas/id/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void savePlantilla_ShouldReturnSavedPlantilla() throws Exception {
        PlantillaEntity plantilla = new PlantillaEntity(1L, 1, 1, 2, "http://example.com/imagen1.jpg");

        given(plantillaService.savePlantilla(Mockito.any(PlantillaEntity.class))).willReturn(plantilla);

        String plantillaJson = """
                    {
                        "idCupon": 1,
                        "idIdioma": 1,
                        "idPlataforma": 2,
                        "url_Imagen": "http://example.com/imagen1.jpg"
                    }
                """;

        mockMvc.perform(post("/api/v1/plantillas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plantillaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCupon", is(1)))
                .andExpect(jsonPath("$.idIdioma", is(1)))
                .andExpect(jsonPath("$.idPlataforma", is(2)))
                .andExpect(jsonPath("$.urlImagen", is("http://example.com/imagen1.jpg")));
    }

    @Test
    public void updatePlantilla_ShouldReturnUpdatedPlantilla() throws Exception {
        Long id = 1L;
        PlantillaEntity updatePlantilla = new PlantillaEntity(id, 1, 1, 2, "http://example.com/imagen1.jpg");

        given(plantillaService.updatePlantilla(eq(id), Mockito.any(PlantillaEntity.class))).willReturn(updatePlantilla);

        String plantillaJson = """
                {
                    "idCupon": 1,
                    "idIdioma": 1,
                    "idPlataforma": 2,
                    "urlImagen": "http://example.com/imagen1.jpg"
                }
            """;

        mockMvc.perform(put("/api/v1/plantillas/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plantillaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCupon", is(1)))
                .andExpect(jsonPath("$.idIdioma", is(1)))
                .andExpect(jsonPath("$.idPlataforma", is(2)))
                .andExpect(jsonPath("$.urlImagen", is("http://example.com/imagen1.jpg")));

    }


    @Test
    public void deletePlantillaById_ShouldReturn204() throws Exception {
        Long id = 1L;
        doNothing().when(plantillaService).deletePlantilla(id);

        mockMvc.perform(delete("/api/v1/plantillas/delete/{id}", id))
                .andExpect(status().isNoContent());

        verify(plantillaService).deletePlantilla(id);
    }

    @Test
    public void deletePlantillaById_ShouldReturnNotFound() throws Exception {
        Long id = 1L;

        doThrow(new RuntimeException("No encontrada")).when(plantillaService).deletePlantilla(id);

        mockMvc.perform(delete("/api/v1/plantillas/delete/{id}", id))
                .andExpect(status().isNotFound());

        verify(plantillaService, times(1)).deletePlantilla(id);
    }
}