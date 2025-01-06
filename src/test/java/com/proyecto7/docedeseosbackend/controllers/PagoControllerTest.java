package com.proyecto7.docedeseosbackend.controllers;

import com.proyecto7.docedeseosbackend.controller.PagoController;
import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import com.proyecto7.docedeseosbackend.service.PagoService;
import com.proyecto7.docedeseosbackend.controller.UsuarioController;
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

@WebMvcTest(PagoController.class)
public class PagoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Test
    public void listarPagos_ShouldReturnPagos() throws Exception {
        PagoEntity pago1 = new PagoEntity(1L, 100.0, "boleta1");
        PagoEntity pago2 = new PagoEntity(2L, 200.0, "boleta2");

        List<PagoEntity> pagos = new ArrayList<>(Arrays.asList(pago1, pago2));

        given(pagoService.getPagos()).willReturn(pagos);

        mockMvc.perform(get("/api/v1/pagos/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].boleta", is("boleta1")))
                .andExpect(jsonPath("$[1].boleta", is("boleta2")));
    }

    @Test
    public void getPagoById_ShouldReturnPago() throws Exception {
        PagoEntity pago = new PagoEntity(1L, 100.0, "boleta1");

        given(pagoService.getPagoById(1L)).willReturn(java.util.Optional.of(pago));

        mockMvc.perform(get("/api/v1/pagos/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.boleta", is("boleta1")));
    }

    @Test
    public void savePago_ShouldReturnSavedPago() throws Exception {
        PagoEntity nuevoPago = new PagoEntity(1L, 150.0, "boleta3");

        given(pagoService.savePago(Mockito.any(PagoEntity.class))).willReturn(nuevoPago);

        String pagoJson = """
            {
                "monto": 150.0,
                "boleta": "boleta3"
            }
        """;

        mockMvc.perform(post("/api/v1/pagos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boleta", is("boleta3")));
    }

    @Test
    public void updatePago_ShouldReturnUpdatedPago() throws Exception {

        Long id = 1L;
        PagoEntity pagoActualizado = new PagoEntity(
                id,
                1000.0,
                "boleta_12345"
        );

        given(pagoService.updatePago(eq(id), Mockito.any(PagoEntity.class))).willReturn(pagoActualizado);

        String pagoJson = """
                {
                    "monto": 1000.0,
                    "boleta": "boleta_12345"
                }
            """;

        mockMvc.perform(put("/api/v1/pagos/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto", is(1000.0)))
                .andExpect(jsonPath("$.boleta", is("boleta_12345")));

        verify(pagoService, times(1)).updatePago(eq(id), Mockito.any(PagoEntity.class));
    }

    @Test
    public void deletePagoById_ShouldReturn204() throws Exception {
        when(pagoService.deletePago(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/pagos/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getPagoByBoleta_ShouldReturnPago() throws Exception {
        PagoEntity pago = new PagoEntity(1L, 100.0, "boleta1");

        given(pagoService.getPagoByBoleta("boleta1")).willReturn(pago);

        mockMvc.perform(get("/api/v1/pagos/boleta/{boleta}", "boleta1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.boleta", is("boleta1")));
    }

    @Test
    public void getPagosByMonto_ShouldReturnPagos() throws Exception {
        PagoEntity pago1 = new PagoEntity(1L, 100.0, "boleta1");
        PagoEntity pago2 = new PagoEntity(2L, 100.0, "boleta2");

        List<PagoEntity> pagos = new ArrayList<>(Arrays.asList(pago1, pago2));

        given(pagoService.getPagosByMonto(100.0)).willReturn(pagos);

        mockMvc.perform(get("/api/v1/pagos/monto/{monto}", 100.0))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].boleta", is("boleta1")))
                .andExpect(jsonPath("$[1].boleta", is("boleta2")));
    }
}
