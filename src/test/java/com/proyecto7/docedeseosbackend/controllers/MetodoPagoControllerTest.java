package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.MetodoPagoController;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;

import com.proyecto7.docedeseosbackend.service.MetodoPagoService;
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

@WebMvcTest(MetodoPagoController.class)
public class MetodoPagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetodoPagoService metodoPagoService;

    @Test
    public void listarMetodosPago_ShouldReturnMetodosPago() throws Exception {
        MetodoPagoEntity metodoPago1 = new MetodoPagoEntity(1L, "Tarjeta de Credito", 1);
        MetodoPagoEntity metodoPago2 = new MetodoPagoEntity(2L, "PayPal", 2);
        List<MetodoPagoEntity> metodosPago = new ArrayList<>(Arrays.asList(metodoPago1, metodoPago2));

        given(metodoPagoService.getMetodosPago()).willReturn(metodosPago);

        mockMvc.perform(get("/api/v1/metodoPagos/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreMetodo", is("Tarjeta de Credito")))
                .andExpect(jsonPath("$[1].nombreMetodo", is("PayPal")));
    }

    @Test
    public void getMetodoPagoById_ShouldReturnMetodoPago() throws Exception {
        MetodoPagoEntity metodoPago = new MetodoPagoEntity(1L, "Transferencia", 1);

        given(metodoPagoService.getMetodoPagoById(1L)).willReturn(metodoPago);

        mockMvc.perform(get("/api/v1/metodoPagos/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombreMetodo", is("Transferencia")));
    }

    @Test
    public void saveMetodoPago_ShouldReturnSavedMetodoPago() throws Exception {

        MetodoPagoEntity savedMetodoPago = new MetodoPagoEntity(1L, "Tarjeta de Debito", 3);

        given(metodoPagoService.saveMetodoPago(Mockito.any(MetodoPagoEntity.class))).willReturn(savedMetodoPago);

        String metodoPagoJson = """
            {
                "nombreMetodo": "Tarjeta de Debito",
                "idPago": 3
            }
        """;

        mockMvc.perform(post("/api/v1/metodoPagos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metodoPagoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreMetodo", is("Tarjeta de Debito")));
    }

    @Test
    public void updateMetodoPago_ShouldReturnUpdatedMetodoPago() throws Exception {
        Long id = 1L;
        MetodoPagoEntity metodoPagoActualizado = new MetodoPagoEntity(id, "Tarjeta de Débito", 2);

        given(metodoPagoService.updateMetodoPago(eq(id), Mockito.any(MetodoPagoEntity.class))).willReturn(metodoPagoActualizado);

        String metodoPagoJson = """
                {
                    "nombreMetodo": "Tarjeta de Débito",
                    "idPago": 2
                }
            """;

        mockMvc.perform(put("/api/v1/metodoPagos/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metodoPagoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreMetodo", is("Tarjeta de Débito")))
                .andExpect(jsonPath("$.idPago", is(2)));

        verify(metodoPagoService, times(1)).updateMetodoPago(eq(id), Mockito.any(MetodoPagoEntity.class));
    }


    @Test
    public void deleteMetodoPagoById_ShouldReturn204() throws Exception {
        given(metodoPagoService.deleteMetodoPago(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/metodoPagos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}

