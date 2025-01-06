package com.proyecto7.docedeseosbackend.service;
import com.proyecto7.docedeseosbackend.entity.RolEntity;
import com.proyecto7.docedeseosbackend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    /**
     * Obtiene una lista de todos los roles.
     *
     * @return Una lista de roles.
     */
    public List<RolEntity> getRoles() {
        return (List<RolEntity>) rolRepository.findAll();
    }

    /**
     * Obtiene un rol por su ID.
     *
     * @param id El ID del rol que se desea obtener.
     * @return El rol correspondiente al ID.
     */
    public RolEntity getRolById(Long id) {
        return rolRepository.findById(id).get();
    }

    /**
     * Obtiene un rol por su nombre.
     *
     * @param nombreRol El nombre del rol que se desea obtener.
     * @return Un objeto Optional que contiene el rol si existe, o está vacío si no se encuentra.
     */
    public Optional<RolEntity> getRolByNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    /**
     * Guarda un nuevo rol en la base de datos.
     *
     * @param rol El rol que se desea guardar.
     * @return El rol guardado.
     */
    public RolEntity save(RolEntity rol) {
        return rolRepository.save(rol);
    }

    /**
     * Actualiza un rol existente en la base de datos.
     *
     * @param rol El rol que se desea actualizar.
     * @return El rol actualizado.
     */
    public RolEntity updateRol(RolEntity rol) {
        return rolRepository.save(rol);
    }

    /**
     * Elimina un rol por su ID.
     *
     * @param id El ID del rol que se desea eliminar.
     * @return True si la eliminación fue exitosa, de lo contrario, lanza una excepción.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public boolean deleteRol(Long id) throws Exception {
        try {
            rolRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
