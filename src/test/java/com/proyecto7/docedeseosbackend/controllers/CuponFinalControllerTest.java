package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.CuponFinalController;
import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.service.CuponFinalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuponFinalController.class)
public class CuponFinalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuponFinalService cuponFinalService;

    // 1. Listar todos los cupones finales
    @Test
    public void listarCuponesFinales_ShouldReturnCuponesFinales() throws Exception {
        CuponFinalEntity cupon1 = new CuponFinalEntity(1L, "De1", "Para1", "Incluye1", LocalDate.now(), 101L, 201L, 301L, 1000, null);
        CuponFinalEntity cupon2 = new CuponFinalEntity(2L, "De2", "Para2", "Incluye2", LocalDate.now(), 102L, 202L, 302L, 1500, null);

        List<CuponFinalEntity> cupones = Arrays.asList(cupon1, cupon2);
        given(cuponFinalService.getCuponesFinales()).willReturn(cupones);

        mockMvc.perform(get("/api/v1/cuponfinal/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campoDe", is("De1")))
                .andExpect(jsonPath("$[1].precioF", is(1500)));
    }

    // 2. Obtener cupón final por ID
    @Test
    public void getCuponFinalById_ShouldReturnCuponFinal() throws Exception {
        CuponFinalEntity cupon = new CuponFinalEntity(1L, "De1", "Para1", "Incluye1", LocalDate.now(), 101L, 201L, 301L, 1000, null);

        given(cuponFinalService.getCuponFinalById(1L)).willReturn(cupon);

        mockMvc.perform(get("/api/v1/cuponfinal/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.campoIncluye", is("Incluye1")))
                .andExpect(jsonPath("$.idCupon", is(101)));
    }

    // 3. Obtener cupones finales por ID de cupón
    @Test
    public void getCuponesFinalesByIdCupon_ShouldReturnCupones() throws Exception {
        CuponFinalEntity cupon1 = new CuponFinalEntity(1L, "De1", "Para1", "Incluye1", LocalDate.now(), 101L, 201L, 301L, 1000, null);
        CuponFinalEntity cupon2 = new CuponFinalEntity(2L, "De2", "Para2", "Incluye2", LocalDate.now(), 101L, 202L, 302L, 1500, null);

        List<CuponFinalEntity> cupones = Arrays.asList(cupon1, cupon2);
        given(cuponFinalService.getCuponesFinalesByIdCupon(101L)).willReturn(cupones);

        mockMvc.perform(get("/api/v1/cuponfinal/idCupon/{idCupon}", 101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campoPara", is("Para1")))
                .andExpect(jsonPath("$[1].campoIncluye", is("Incluye2")));
    }

    // 4. Guardar un nuevo cupón final
    @Test
    public void saveCuponFinal_ShouldReturnSavedCuponFinal() throws Exception {
        CuponFinalEntity cuponNuevo = new CuponFinalEntity(1L, "NuevoDe", "NuevoPara", "NuevoIncluye", LocalDate.now(), 103L, 203L, 303L, 2000, null);

        given(cuponFinalService.saveCuponFinal(Mockito.any(CuponFinalEntity.class))).willReturn(cuponNuevo);

        String cuponJson = """
            {
                "campoDe": "NuevoDe",
                "campoPara": "NuevoPara",
                "campoIncluye": "NuevoIncluye",
                "fecha": "2024-12-17",
                "idCupon": 103,
                "idUsuario": 203,
                "idPlantilla": 303,
                "precioF": 2000
            }
        """;

        mockMvc.perform(post("/api/v1/cuponfinal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuponJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campoDe", is("NuevoDe")))
                .andExpect(jsonPath("$.precioF", is(2000)));
    }

    // 5. Actualizar un cupón final
    @Test
    public void updateCuponFinal_ShouldReturnUpdatedCuponFinal() throws Exception {
        CuponFinalEntity cuponActualizado = new CuponFinalEntity(1L, "DeActualizado", "ParaActualizado", "IncluyeActualizado", LocalDate.now(), 104L, 204L, 304L, 2500, null);

        given(cuponFinalService.updateCuponFinal(Mockito.eq(1L), Mockito.any(CuponFinalEntity.class))).willReturn(cuponActualizado);

        String cuponJson = """
            {
                "campoDe": "DeActualizado",
                "campoPara": "ParaActualizado",
                "campoIncluye": "IncluyeActualizado",
                "fecha": "2024-12-17",
                "idCupon": 104,
                "idUsuario": 204,
                "idPlantilla": 304,
                "precioF": 2500
            }
        """;

        mockMvc.perform(put("/api/v1/cuponfinal/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuponJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campoPara", is("ParaActualizado")))
                .andExpect(jsonPath("$.precioF", is(2500)));
    }

    // 6. Eliminar un cupón final
    @Test
    public void deleteCuponFinalById_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        given(cuponFinalService.deleteCuponFinal(id)).willReturn(true);

        mockMvc.perform(delete("/api/v1/cuponfinal/{id}", id))
                .andExpect(status().isNoContent());
    }
}