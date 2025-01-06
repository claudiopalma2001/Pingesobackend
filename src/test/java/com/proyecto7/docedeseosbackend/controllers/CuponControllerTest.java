package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.CuponController;
import com.proyecto7.docedeseosbackend.controller.PlantillaController;
import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.service.CuponService;
import com.proyecto7.docedeseosbackend.service.PlantillaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CuponController.class)
public class CuponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantillaController plantillaController;

    @MockBean
    private PlantillaService plantillaService; // Mock del servicio requerido por el controlador

    @MockBean
    private CuponService cuponService;

    // 1. Listar todos los cupones
    @Test
    public void listarCupones_ShouldReturnCupones() throws Exception {
        CuponEntity cupon1 = new CuponEntity(1L, "Cupon navidad", "Premium", 1, 1000);
        CuponEntity cupon2 = new CuponEntity(2L, "Cupon San Valentin", "Free", 2, 1500);

        List<CuponEntity> cupones = Arrays.asList(cupon1, cupon2);
        given(cuponService.getCupones()).willReturn(cupones);

        mockMvc.perform(get("/api/v1/cupones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreCupon", is("Cupon navidad")))
                .andExpect(jsonPath("$[1].precio", is(1500)));
    }

    // 2. Obtener cupón por ID
    @Test
    public void getCuponById_ShouldReturnCupon() throws Exception {
        CuponEntity cupon = new CuponEntity(1L, "Cupon navidad", "Premium", 1, 1000);
        given(cuponService.getCuponById(1L)).willReturn(cupon);

        mockMvc.perform(get("/api/v1/cupones/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreCupon", is("Cupon navidad")))
                .andExpect(jsonPath("$.precio", is(1000)));
    }

    // 3. Crear un nuevo cupón
    @Test
    public void createCupon_ShouldReturnSavedCupon() throws Exception {
        CuponEntity cuponNuevo = new CuponEntity(3L, "Cupon Dia de la madre", "Free", 3, 2000);
        given(cuponService.save(Mockito.any(CuponEntity.class))).willReturn(cuponNuevo);

        String cuponJson = """
        {
            "nombreCupon": "Cupon Dia de la madre",
            "tipo": "Free",
            "idTematica": 3,
            "precio": 2000
        }
    """;

        mockMvc.perform(post("/api/v1/cupones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuponJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCupon", is("Cupon Dia de la madre")))
                .andExpect(jsonPath("$.precio", is(2000)));
    }

    // 4. Actualizar un cupón
    @Test
    public void updateCupon_ShouldReturnUpdatedCupon() throws Exception {
        Long id = 1L;
        CuponEntity cuponActualizado = new CuponEntity(id, "Cupon Halloween", "Premium", 1, 2500);

        given(cuponService.updateCupon(eq(id), Mockito.any(CuponEntity.class))).willReturn(cuponActualizado);

        String cuponJson = """
        {
            "nombreCupon": "Cupon Halloween",
            "tipo": "Premium",
            "idTematica": 1,
            "precio": 2500
        }
    """;

        mockMvc.perform(put("/api/v1/cupones/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuponJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCupon", is("Cupon Halloween")))
                .andExpect(jsonPath("$.precio", is(2500)));
    }

    // 5. Eliminar un cupón
    @Test
    public void deleteCupon_ShouldReturnNoContent() throws Exception {
        int idCupon = 1;
        given(cuponService.deleteCupon((long) idCupon)).willReturn(true);

        mockMvc.perform(delete("/api/v1/cupones/{idCupon}", idCupon))
                .andExpect(status().isNoContent());
    }
}