package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.repository.CompraRepository;
import com.proyecto7.docedeseosbackend.service.CompraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private CompraRepository compraRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Crear una compra
    @Test
    void createCompra() {
        CompraEntity compra = new CompraEntity(null, 1L, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());
        CompraEntity saved = new CompraEntity(1L, 1L, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());

        when(compraRepository.save(any(CompraEntity.class))).thenReturn(saved);

        CompraEntity resultado = compraService.save(compra);

        assertNotNull(resultado, "La compra no puede ser nula");
        assertEquals(saved.getIdUsuario(), resultado.getIdUsuario());
        assertEquals(saved.getFechaCompra(), resultado.getFechaCompra());
        verify(compraRepository, times(1)).save(compra);
    }

    // 2. Obtener compra por ID
    @Test
    void getCompraById() {
        Long id = 1L;
        CompraEntity compra = new CompraEntity(id, 1L, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());

        when(compraRepository.findById(id)).thenReturn(Optional.of(compra));
        CompraEntity result = compraService.getCompraById(id);

        assertNotNull(result, "La compra no puede ser nula");
        assertEquals(id, result.getId());
        verify(compraRepository, times(1)).findById(id);
    }

    // 3. Obtener todas las compras
    @Test
    void getAllCompras() {
        CompraEntity compra1 = new CompraEntity(1L, 1L, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());
        CompraEntity compra2 = new CompraEntity(2L, 2L, LocalDate.of(2024, 11, 6), 150000, new ArrayList<>());
        List<CompraEntity> comprasList = Arrays.asList(compra1, compra2);

        when(compraRepository.findAll()).thenReturn(comprasList);

        List<CompraEntity> resultado = compraService.getAllCompras();

        assertNotNull(resultado, "La lista de compras no puede ser nula");
        assertEquals(2, resultado.size());
        assertEquals(100000, resultado.get(0).getMontoTotal());
        assertEquals(150000, resultado.get(1).getMontoTotal());
        verify(compraRepository, times(1)).findAll();
    }

    // 4. Actualizar compra
    @Test
    void updateCompra() {
        // Datos de prueba
        CompraEntity compra = new CompraEntity(1L, 1L, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());
        CompraEntity updated = new CompraEntity(1L, 1L, LocalDate.of(2024, 11, 4), 90000, new ArrayList<>());

        // Configura los mocks
        when(compraRepository.findById(compra.getId())).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(CompraEntity.class))).thenReturn(updated);

        // Ejecuta el método de servicio
        CompraEntity result = compraService.updateCompra(updated);

        // Verificaciones
        assertNotNull(result, "La compra no puede ser nula");
        assertEquals(90000, result.getMontoTotal());
        verify(compraRepository, times(1)).findById(compra.getId());
        verify(compraRepository, times(1)).save(any(CompraEntity.class));
    }

    // 5. Eliminar compra
    @Test
    void deleteCompra() {
        Long id = 1L;
        doNothing().when(compraRepository).deleteById(id);

        boolean result = compraService.deleteCompra(id);

        assertTrue(result, "La compra debería eliminarse con éxito");
        verify(compraRepository, times(1)).deleteById(id);
    }

    // 6. Eliminar compra inexistente
    @Test
    void deleteCompra_shouldReturnFalseWhenNotFound() {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Compra no encontrada")).when(compraRepository).deleteById(id);

        boolean result = compraService.deleteCompra(id);

        assertFalse(result, "La compra no debería eliminarse si no existe");
        verify(compraRepository, times(1)).deleteById(id);
    }

    // 7. Obtener compras por usuario
    @Test
    void getAllComprasByUserId() {
        Long userId = 1L;
        CompraEntity compra1 = new CompraEntity(1L, userId, LocalDate.of(2024, 11, 4), 100000, new ArrayList<>());
        CompraEntity compra2 = new CompraEntity(2L, userId, LocalDate.of(2024, 11, 5), 120000, new ArrayList<>());
        List<CompraEntity> comprasList = Arrays.asList(compra1, compra2);

        when(compraRepository.findByIdUsuario(userId)).thenReturn(comprasList);

        List<CompraEntity> resultado = compraService.getAllComprasByUserId(userId);

        assertNotNull(resultado, "La lista de compras no puede ser nula");
        assertEquals(2, resultado.size());
        assertEquals(100000, resultado.get(0).getMontoTotal());
        assertEquals(120000, resultado.get(1).getMontoTotal());
        verify(compraRepository, times(1)).findByIdUsuario(userId);
    }
}
