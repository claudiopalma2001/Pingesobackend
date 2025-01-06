package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.RolEntity;
import com.proyecto7.docedeseosbackend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las operaciones relacionadas con los roles.
 * Proporciona métodos para listar, obtener, guardar, actualizar y eliminar roles.
 */

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin("*")
public class RolController {

    @Autowired
    RolService rolService;

    /**
     * Método para obtener todos los roles.
     *
     * @return Una lista de objetos {@link RolEntity} con los roles disponibles.
     */
    @GetMapping("/")
    public ResponseEntity<List<RolEntity>> listarRoles() {
        List<RolEntity> roles = rolService.getRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Método para obtener un rol por su ID.
     *
     * @param id El ID del rol a obtener.
     * @return Un objeto {@link RolEntity} con los detalles del rol.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<RolEntity> getRolById(@PathVariable Long id) {
        RolEntity rol = rolService.getRolById(id);
        return ResponseEntity.ok(rol);
    }

    /**
     * Método para obtener un rol por su nombre.
     *
     * @param nombreRol El nombre del rol a buscar.
     * @return Un objeto {@link Optional} de {@link RolEntity}, que puede estar vacío si no se encuentra el rol.
     */
    @GetMapping("/nombreRol/{nombreRol}")
    public ResponseEntity<Optional<RolEntity>> getRolByNombre(@PathVariable String nombreRol) {
        Optional<RolEntity> rol = rolService.getRolByNombre(nombreRol);
        return ResponseEntity.ok(rol);
    }

    /**
     * Método para guardar un nuevo rol.
     *
     * @param rol El objeto {@link RolEntity} que se desea guardar.
     * @return El rol guardado.
     */
    @PostMapping("/save")
    public ResponseEntity<RolEntity> saveRol(@RequestBody RolEntity rol) {
        RolEntity rolNuevo = rolService.save(rol);
        return ResponseEntity.ok(rolNuevo);
    }

    /**
     * Método para actualizar un rol existente.
     *
     * @param rol El objeto {@link RolEntity} con los datos actualizados.
     * @return El rol actualizado.
     */
    @PutMapping("/update")
    public ResponseEntity<RolEntity> updateRol(@RequestBody RolEntity rol){
        RolEntity rolActualizado = rolService.updateRol(rol);
        return ResponseEntity.ok(rolActualizado);
    }

    /**
     * Método para eliminar un rol por su ID.
     *
     * @param id El ID del rol que se desea eliminar.
     * @return Una respuesta sin contenido si la eliminación fue exitosa.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRolById(@PathVariable Long id) throws Exception {
        var isDeleted = rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}