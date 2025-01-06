package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import com.proyecto7.docedeseosbackend.service.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con las plataformas.
 * Proporciona métodos para listar, obtener, guardar y eliminar plataformas.
 */

@RestController
@RequestMapping("/api/v1/plataforma")
@CrossOrigin("*")
public class PlataformaController {

    @Autowired
    PlataformaService plataformaService;

    /**
     * Método para obtener todas las plataformas.
     *
     * @return Una lista de objetos {@link PlataformaEntity} con las plataformas disponibles.
     */
    @GetMapping("/")
    public ResponseEntity<List<PlataformaEntity>> listarPlataformas() {
        List<PlataformaEntity> plataformas = plataformaService.getPlataformas();
        return ResponseEntity.ok(plataformas);
    }

    /**
     * Método para obtener una plataforma por su ID.
     *
     * @param id El ID de la plataforma a obtener.
     * @return Un objeto {@link PlataformaEntity} con los detalles de la plataforma.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<PlataformaEntity> getPlataformaById(@PathVariable Long id) {
        PlataformaEntity plataforma = plataformaService.getPlataformaById(id);
        return ResponseEntity.ok(plataforma);
    }

    /**
     * Método para guardar una nueva plataforma.
     *
     * @param plataforma El objeto {@link PlataformaEntity} que se desea guardar.
     * @return La plataforma guardada.
     */
    @PostMapping("/save")
    public ResponseEntity<PlataformaEntity> saveCuponCompra(@RequestBody PlataformaEntity plataforma) {
        PlataformaEntity plataformaNueva = plataformaService.savePlataforma(plataforma);
        return ResponseEntity.ok(plataformaNueva);
    }

    /**
     * Método para eliminar una plataforma por su ID.
     *
     * @param id El ID de la plataforma que se desea eliminar.
     * @return Una respuesta sin contenido si la eliminación fue exitosa.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCuponCompraById(@PathVariable Long id) throws Exception {
        var isDeleted = plataformaService.deletePlataforma(id);
        return ResponseEntity.noContent().build();
    }
}
