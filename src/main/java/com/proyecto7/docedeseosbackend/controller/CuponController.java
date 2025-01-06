package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.service.CuponService;
import com.proyecto7.docedeseosbackend.service.PlantillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cupones")
@CrossOrigin("*")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private PlantillaController plantillaController;

    private static final Logger log = LoggerFactory.getLogger(CuponController.class);

    /**
     * Obtiene una lista de todos los cupones.
     *
     * @return una respuesta con la lista de todos los cupones.
     */
    @GetMapping
    public ResponseEntity<List<CuponEntity>> getAllCupones() {
        List<CuponEntity> cupones = cuponService.getCupones();
        return ResponseEntity.ok(cupones);
    }

    /**
     * Obtiene un cupón específico por su ID.
     *
     * @param id el identificador del cupón a buscar.
     * @return una respuesta con el cupón encontrado o una respuesta vacía si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuponEntity> getCuponById(@PathVariable Long id) {
        CuponEntity cupon = cuponService.getCuponById(id);
        return cupon != null ? ResponseEntity.ok(cupon) : ResponseEntity.notFound().build();
    }

    /**
     * Obtiene un cupón específico por su nombre.
     *
     * @param nombre el nombre del cupón a buscar.
     * @return una respuesta con el cupón encontrado o una respuesta vacía si no se encuentra.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CuponEntity> getCuponByNombre(@PathVariable String nombre) {
        CuponEntity cupon = cuponService.getCuponByNombre(nombre);
        return cupon != null ? ResponseEntity.ok(cupon) : ResponseEntity.notFound().build();
    }

    /**
     * Obtiene una lista de cupones por su tipo (o categoría).
     *
     * @param tipo el tipo de cupón.
     * @return una respuesta con la lista de cupones filtrados por tipo.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CuponEntity>> getCuponesByTipo(@PathVariable String tipo) {
        List<CuponEntity> cupones = cuponService.getCuponesByTipo(tipo);
        return ResponseEntity.ok(cupones);
    }

    /**
     * Obtiene una lista de cupones por su temática.
     *
     * @param idTematica el identificador de la temática.
     * @return una respuesta con la lista de cupones filtrados por temática.
     */
    @GetMapping("/tematica/{idTematica}")
    public ResponseEntity<List<CuponEntity>> getCuponesByIdTematica(@PathVariable int idTematica) {
        List<CuponEntity> cupones = cuponService.getCuponesByIdTematica(idTematica);
        return ResponseEntity.ok(cupones);
    }

    /**
     * Crea un nuevo cupón.
     *
     * @param cupon la entidad del cupón a guardar.
     * @return una respuesta con el cupón guardado.
     */
    @PostMapping
    public ResponseEntity<CuponEntity> createCupon(@RequestBody CuponEntity cupon) {
        CuponEntity savedCupon = cuponService.save(cupon);
        return ResponseEntity.ok(savedCupon);
    }

    /**
     * Actualiza un cupón existente.
     *
     * @param id el identificador del cupón a actualizar.
     * @param cupon la entidad del cupón con los nuevos datos.
     * @return una respuesta con el cupón actualizado o una respuesta vacía si el cupón no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuponEntity> updateCupon(@PathVariable Long id, @RequestBody CuponEntity cupon) {
        try {
            CuponEntity updatedCupon = cuponService.updateCupon(id, cupon);
            return ResponseEntity.ok(updatedCupon);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un cupón por su ID.
     *
     * @param idCupon el identificador del cupón a eliminar.
     * @return una respuesta con el estado de la operación.
     */
    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCupon(@PathVariable Long id) {
        try {
            boolean deleted = cuponService.deleteCupon(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }*/
    @DeleteMapping("/{idCupon}")
    public ResponseEntity<Void> deleteCuponById(@PathVariable int idCupon) {
        try {
            // Convertir el int a Long antes de pasarlo al servicio
            Long idCuponLong = Long.valueOf(idCupon);

            // Obtener las plantillas asociadas al cupón
            List<PlantillaEntity> plantillas = plantillaService.getPlantillasByIdCupon(idCupon);

            // Eliminar imágenes asociadas a las plantillas usando PlantillaController
            for (PlantillaEntity plantilla : plantillas) {
                plantillaController.deleteImage(plantilla.getUrlImagen());
            }

            // Eliminar las plantillas asociadas
            plantillaService.deletePlantillasByIdCupon(idCupon);

            // Eliminar el cupón
            cuponService.deleteCupon(idCuponLong); // Se pasa el Long

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar el cupón y sus plantillas asociadas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


        /**
         * Actualiza solo el precio de un cupón existente.
         *
         * @param id el identificador del cupón a actualizar.
         * @param nuevoPrecio el nuevo precio del cupón.
         * @return una respuesta con el cupón actualizado o un mensaje de error si el cupón no existe.
         */
    @PatchMapping("/{id}/precio")
    public ResponseEntity<CuponEntity> updatePrecio(@PathVariable Long id, @RequestBody int nuevoPrecio) {
        try {
            CuponEntity updatedCupon = cuponService.updatePrecio(id, nuevoPrecio);
            return ResponseEntity.ok(updatedCupon);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
