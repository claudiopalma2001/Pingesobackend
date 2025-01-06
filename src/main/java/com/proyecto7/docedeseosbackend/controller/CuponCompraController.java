package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import com.proyecto7.docedeseosbackend.service.CuponCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con los cupones de compra.
 * Proporciona métodos para listar, obtener, guardar y eliminar cupones de compra.
 */

@RestController
@RequestMapping("/api/v1/cuponcompra")
@CrossOrigin("*")
public class CuponCompraController {

    @Autowired
    CuponCompraService cuponCompraService;

    /**
     * Método para obtener todos los cupones de compra.
     *
     * @return Una lista de objetos {@link CuponCompraEntity} con los cupones de compra disponibles.
     */
    @GetMapping("/")
    public ResponseEntity<List<CuponCompraEntity>> listarCuponesCompra() {
        List<CuponCompraEntity> cuponesCompra = cuponCompraService.getCuponesCompra();
        return ResponseEntity.ok(cuponesCompra);
    }

    /**
     * Método para obtener un cupón de compra por su ID.
     *
     * @param id El ID del cupón de compra a obtener.
     * @return Un objeto {@link CuponCompraEntity} con los detalles del cupón de compra.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<CuponCompraEntity> getCuponCompraById(@PathVariable Long id) {
        CuponCompraEntity cuponCompra = cuponCompraService.getCuponCompraById(id);
        return ResponseEntity.ok(cuponCompra);
    }

    /**
     * Método para guardar un nuevo cupón de compra.
     *
     * @param cuponCompra El objeto {@link CuponCompraEntity} que se desea guardar.
     * @return El cupón de compra guardado.
     */
    @PostMapping("/save")
    public ResponseEntity<CuponCompraEntity> saveCuponCompra(@RequestBody CuponCompraEntity cuponCompra) {
        CuponCompraEntity cuponCompraNuevo = cuponCompraService.saveCuponCompra(cuponCompra);
        return ResponseEntity.ok(cuponCompraNuevo);
    }

    /**
     * Método para eliminar un cupón de compra por su ID.
     *
     * @param id El ID del cupón de compra que se desea eliminar.
     * @return Una respuesta sin contenido si la eliminación fue exitosa.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCuponCompraById(@PathVariable Long id) throws Exception {
        var isDeleted = cuponCompraService.deleteCuponCompra(id);
        return ResponseEntity.noContent().build();
    }
}
