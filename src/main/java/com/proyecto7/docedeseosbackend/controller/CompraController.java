package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.service.CompraService;
import com.proyecto7.docedeseosbackend.service.CuponFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/compras")
@CrossOrigin("*")
public class CompraController {

    @Autowired
    CompraService compraService;

    /**
     * Obtener todas las compras realizadas.
     * @return Lista de compras.
     */
    @GetMapping("/")
    public List<CompraEntity> getAllCompras() {
        return compraService.getAllCompras();
    }

    /**
     * Obtener todas las compras realizadas por un usuario.
     * @param idUsuario ID del usuario.
     * @return Lista de compras del usuario.
     */
    @GetMapping("/idUsuario/{idUsuario}")
    public ResponseEntity<List<CompraEntity>> getComprasByUserId(@PathVariable Long idUsuario) {
        List<CompraEntity> compras = compraService.getAllComprasByUserId(idUsuario);
        return ResponseEntity.ok(compras);
    }

    /**
     * Crear una nueva compra junto con los cupones finales asociados.
     * @param compra Compra a guardar, incluyendo los cupones relacionados.
     * @return Compra guardada con los cupones asociados.
     */
    @PostMapping("/saveCompraWithCupones")
    public ResponseEntity<CompraEntity> saveCompraWithCupones(@RequestBody CompraEntity compra) {
        try {
            // Vincular cada cupón con la compra
            if (compra.getCuponesFinales() != null) {
                compra.getCuponesFinales().forEach(cupon -> cupon.setCompra(compra));
            }

            // Guardar la compra junto con los cupones asociados
            CompraEntity savedCompra = compraService.save(compra);
            return ResponseEntity.ok(savedCompra);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Obtener una compra por su ID.
     * @param id ID de la compra.
     * @return Compra correspondiente.
     */
    @GetMapping("/{id}")
    public CompraEntity getCompraById(@PathVariable Long id) {
        return compraService.getCompraById(id);
    }

    /**
     * Eliminar una compra por su ID.
     * @param id ID de la compra.
     * @return Respuesta de eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCompraById(@PathVariable Long id) throws Exception {
        boolean isDeleted = compraService.deleteCompra(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(500).build();
    }
}