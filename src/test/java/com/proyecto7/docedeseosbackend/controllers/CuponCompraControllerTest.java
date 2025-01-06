package com.proyecto7.docedeseosbackend.controllers;
import com.proyecto7.docedeseosbackend.controller.CuponCompraController;
import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import com.proyecto7.docedeseosbackend.service.CuponCompraService;
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

@WebMvcTest(CuponCompraController.class)
public class CuponCompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuponCompraService cuponCompraService;

    @Test
    public void listarCuponesCompra_ShouldReturnCuponesCompra() throws Exception {

        CuponCompraEntity cuponCompra1 = new CuponCompraEntity(1L, 101L, 201L);
        CuponCompraEntity cuponCompra2 = new CuponCompraEntity(2L, 102L, 202L);

        List<CuponCompraEntity> cuponesCompra = new ArrayList<>(Arrays.asList(cuponCompra1, cuponCompra2));

        given(cuponCompraService.getCuponesCompra()).willReturn(cuponesCompra);

        mockMvc.perform(get("/api/v1/cuponcompra/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idCupon", is(101)))
                .andExpect(jsonPath("$[1].idCupon", is(102)));
    }

    @Test
    public void getCuponCompraById_ShouldReturnCuponCompra() throws Exception {

        CuponCompraEntity cuponCompra = new CuponCompraEntity(1L, 101L, 201L);

        given(cuponCompraService.getCuponCompraById(1L)).willReturn(cuponCompra);

        mockMvc.perform(get("/api/v1/cuponcompra/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCupon", is(101)))
                .andExpect(jsonPath("$.idCompra", is(201)));
    }

    @Test
    public void saveCuponCompra_ShouldReturnSavedCuponCompra() throws Exception {

        CuponCompraEntity cuponCompraNuevo = new CuponCompraEntity(3L, 103L, 203L);

        given(cuponCompraService.saveCuponCompra(Mockito.any(CuponCompraEntity.class))).willReturn(cuponCompraNuevo);

        String cuponCompraJson = """
            {
                "idCupon": 103,
                "idCompra": 203
            }
        """;

        mockMvc.perform(post("/api/v1/cuponcompra/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuponCompraJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCupon", is(103)))
                .andExpect(jsonPath("$.idCompra", is(203)));
    }

    @Test
    public void deleteCuponCompraById_ShouldReturnNoContent() throws Exception {

        Long id = 1L;
        given(cuponCompraService.deleteCuponCompra(id)).willReturn(true);

        mockMvc.perform(delete("/api/v1/cuponcompra/{id}", id))
                .andExpect(status().isNoContent());
    }
}
