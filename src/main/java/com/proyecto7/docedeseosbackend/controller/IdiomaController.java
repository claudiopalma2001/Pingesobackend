package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import com.proyecto7.docedeseosbackend.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/idiomas")
@CrossOrigin("*")
public class IdiomaController {

    @Autowired
    private IdiomaService idiomaService;

    /**
     * Obtiene una lista de todos los idiomas disponibles.
     *
     * @return una respuesta con la lista de idiomas.
     */
    @GetMapping("/")
    public ResponseEntity<List<IdiomaEntity>> listarIdiomas() {
        List<IdiomaEntity> idiomas = idiomaService.getIdiomas();
        return ResponseEntity.ok(idiomas);
    }

    /**
     * Obtiene un idioma por su identificador.
     *
     * @param id el identificador del idioma.
     * @return una respuesta con el idioma encontrado o un error si no se encuentra.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<IdiomaEntity> getIdiomaById(@PathVariable Long id) {
        IdiomaEntity idioma = idiomaService.getIdiomaById(id);
        return (idioma != null) ? ResponseEntity.ok(idioma) : ResponseEntity.notFound().build();
    }

    /**
     * Obtiene un idioma por su nombre.
     *
     * @param nombreIdioma el nombre del idioma.
     * @return una respuesta con el idioma encontrado o un error si no se encuentra.
     */
    @GetMapping("/nombre/{nombreIdioma}")
    public ResponseEntity<IdiomaEntity> getIdiomaByNombre(@PathVariable String nombreIdioma) {
        IdiomaEntity idioma = idiomaService.getIdiomaByNombre(nombreIdioma);
        return (idioma != null) ? ResponseEntity.ok(idioma) : ResponseEntity.notFound().build();
    }

    /**
     * Guarda un nuevo idioma en el sistema.
     *
     * @param idioma la entidad IdiomaEntity a guardar.
     * @return una respuesta con el idioma creado.
     */
    @PostMapping("/save")
    public ResponseEntity<IdiomaEntity> saveIdioma(@RequestBody IdiomaEntity idioma) {
        IdiomaEntity idiomaNuevo = idiomaService.saveIdioma(idioma);
        return ResponseEntity.created(null).body(idiomaNuevo);
    }

    /**
     * Actualiza un idioma existente.
     *
     * @param id el ID del idioma a actualizar.
     * @param idioma los datos actualizados del idioma.
     * @return una respuesta con el idioma actualizado o un error si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateIdioma(@PathVariable Long id, @RequestBody IdiomaEntity idioma) {
        try {
            IdiomaEntity updatedIdioma = idiomaService.updateIdioma(id, idioma);
            return ResponseEntity.ok(updatedIdioma);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un idioma por su identificador.
     *
     * @param id el identificador del idioma a eliminar.
     * @return una respuesta indicando si la eliminación fue exitosa.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIdiomaById(@PathVariable Long id) {
        try {
            boolean eliminado = idiomaService.deleteIdioma(id);
            return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}