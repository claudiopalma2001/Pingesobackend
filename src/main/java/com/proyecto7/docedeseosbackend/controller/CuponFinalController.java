package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.service.CuponFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con los cupones finales.
 * Proporciona métodos para listar, obtener, guardar, actualizar y eliminar cupones finales.
 */

@RestController
@RequestMapping("/api/v1/cuponfinal")
@CrossOrigin("*")
public class CuponFinalController {

    @Autowired
    CuponFinalService cuponFinalService;

    /**
     * Método para obtener todos los cupones finales.
     *
     * @return Una lista de objetos {@link CuponFinalEntity} con los cupones finales disponibles.
     */
    @GetMapping("/")
    public ResponseEntity<List<CuponFinalEntity>> listarCuponesFinales() {
        List<CuponFinalEntity> cuponesFinales = cuponFinalService.getCuponesFinales();
        return ResponseEntity.ok(cuponesFinales);
    }

    /**
     * Método para obtener un cupón final por su ID.
     *
     * @param id El ID del cupón final a obtener.
     * @return Un objeto {@link CuponFinalEntity} con los detalles del cupón final.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<CuponFinalEntity> getCuponFinalById(@PathVariable Long id) {
        CuponFinalEntity cuponCompra = cuponFinalService.getCuponFinalById(id);
        return ResponseEntity.ok(cuponCompra);
    }

    /**
     * Método para obtener cupones finales por el ID del cupón.
     *
     * @param idCupon El ID del cupón que se utiliza para filtrar los cupones finales.
     * @return Una lista de objetos {@link CuponFinalEntity} asociados con el cupón especificado.
     */
    @GetMapping("/idCupon/{idCupon}")
    public ResponseEntity<List<CuponFinalEntity>> getCuponesFinalesByIdCupon(@PathVariable Long idCupon) {
        List<CuponFinalEntity> cupones = cuponFinalService.getCuponesFinalesByIdCupon(idCupon);
        return ResponseEntity.ok(cupones);
    }

    /**
     * Método para obtener cupones finales por el ID del usuario.
     *
     * @param idUsuario El ID del usuario que se utiliza para filtrar los cupones finales.
     * @return Una lista de objetos {@link CuponFinalEntity} asociados con el usuario especificado.
     */
    @GetMapping("/idUsuario/{idUsuario}")
    public ResponseEntity<List<CuponFinalEntity>> getCuponesFinalesByIdUsuario(@PathVariable Long idUsuario) {
        List<CuponFinalEntity> cupones = cuponFinalService.getCuponesFinalesByIdUsuario(idUsuario);
        return ResponseEntity.ok(cupones);
    }

    /**
     * Método para obtener cupones finales por el ID de plantilla.
     *
     * @param idPlantilla El ID de plantilla que se utiliza para filtrar los cupones finales.
     * @return Una lista de objetos {@link CuponFinalEntity} asociados con la plantilla especificada.
     */
    @GetMapping("/idPlantilla/{idPlantilla}")
    public ResponseEntity<List<CuponFinalEntity>> getUsuariosByIdRol(@PathVariable Long idPlantilla) {
        List<CuponFinalEntity> cupones = cuponFinalService.getCuponesFinalesByIdPlantilla(idPlantilla);
        return ResponseEntity.ok(cupones);
    }

    /**
     * Método para guardar un nuevo cupón final.
     *
     * @param cuponFinal El objeto {@link CuponFinalEntity} que se desea guardar.
     * @return El cupón final guardado.
     */
    @PostMapping("/save")
    public ResponseEntity<CuponFinalEntity> saveCuponFinal(@RequestBody CuponFinalEntity cuponFinal) {
        CuponFinalEntity cuponFinalNuevo = cuponFinalService.saveCuponFinal(cuponFinal);
        return ResponseEntity.ok(cuponFinalNuevo);
    }

    /**
     * Método para guardar múltiples cupones finales.
     *
     * @param cuponesFinales La lista de objetos {@link CuponFinalEntity} que se desea guardar.
     * @return La lista de cupones finales guardados.
     */
    @PostMapping("/saveAll")
    public ResponseEntity<List<CuponFinalEntity>> saveAllCuponesFinales(@RequestBody List<CuponFinalEntity> cuponesFinales) {
        List<CuponFinalEntity> cuponesGuardados = cuponFinalService.saveAllCuponesFinales(cuponesFinales);
        return ResponseEntity.ok(cuponesGuardados);
    }

    /**
     * Método para actualizar un cupón final existente.
     *
     * @param id El ID del cupón final que se desea actualizar.
     * @param cuponFinal El objeto {@link CuponFinalEntity} con los datos actualizados.
     * @return El cupón final actualizado o un mensaje de error si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCuponFinal(@PathVariable Long id, @RequestBody CuponFinalEntity cuponFinal) {
        try {
            CuponFinalEntity updatedCuponFinal = cuponFinalService.updateCuponFinal(id, cuponFinal);
            return new ResponseEntity<>(updatedCuponFinal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Método para eliminar un cupón final por su ID.
     *
     * @param id El ID del cupón final que se desea eliminar.
     * @return Una respuesta sin contenido si la eliminación fue exitosa.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCuponFinalById(@PathVariable Long id) throws Exception {
        var isDeleted = cuponFinalService.deleteCuponFinal(id);
        return ResponseEntity.noContent().build();
    }

}
