package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import com.proyecto7.docedeseosbackend.service.TematicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tematicas")
@CrossOrigin("*")
public class TematicaController {

    @Autowired
    private TematicaService tematicaService;

    /**
     * Obtiene una lista de todas las temáticas.
     *
     * @return una respuesta con la lista de temáticas.
     */
    @GetMapping("/")
    public ResponseEntity<List<TematicaEntity>> listarTematicas() {
        List<TematicaEntity> tematicas = tematicaService.getAllTematicas();
        return ResponseEntity.ok(tematicas);
    }

    /**
     * Obtiene una temática específica por su identificador.
     *
     * @param id el identificador de la temática.
     * @return una respuesta con la temática encontrada o un error si no se encuentra.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<TematicaEntity> getTematicaById(@PathVariable Long id) {
        Optional<TematicaEntity> tematica = tematicaService.getTematicaById(id);
        return tematica.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Guarda una nueva temática.
     *
     * @param tematica la entidad TematicaEntity a guardar.
     * @return una respuesta con la temática creada.
     */
    @PostMapping("/save")
    public ResponseEntity<TematicaEntity> saveTematica(@RequestBody TematicaEntity tematica) {
        TematicaEntity nuevaTematica = tematicaService.save(tematica);
        return ResponseEntity.created(null).body(nuevaTematica);
    }

    /**
     * Actualiza una temática existente.
     *
     * @param id el ID de la temática a actualizar.
     * @param tematica los datos actualizados de la temática.
     * @return una respuesta con la temática actualizada o un error si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTematica(@PathVariable Long id, @RequestBody TematicaEntity tematica) {
        try {
            TematicaEntity updatedTematica = tematicaService.updateTematica(id, tematica);
            return ResponseEntity.ok(updatedTematica);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una temática por su identificador.
     *
     * @param id el identificador de la temática a eliminar.
     * @return una respuesta indicando si la eliminación fue exitosa.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTematicaById(@PathVariable Long id) {
        try {
            boolean eliminado = tematicaService.deleteTematica(id);
            return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
