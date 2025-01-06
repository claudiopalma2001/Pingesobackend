package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio que maneja las operaciones de acceso a datos para la entidad 'Rol'.
 * Extiende de JpaRepository, proporcionando métodos estándar para realizar operaciones CRUD
 * sobre la tabla 'roles' en la base de datos.
 *
 * Además de los métodos CRUD proporcionados por JpaRepository, esta interfaz incluye un
 * método personalizado para obtener un rol por su nombre.
 */

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    /**
     * Método para obtener un rol por su nombre.
     *
     * @param nombreRol El nombre del rol que se desea buscar.
     * @return Un objeto Optional que contiene el rol encontrado, o vacío si no se encuentra ningún rol con el nombre proporcionado.
     */
    Optional<RolEntity> findByNombreRol(String nombreRol);
}
