package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con los idiomas.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */

@Repository
public interface IdiomaRepository extends JpaRepository<IdiomaEntity, Long> {
    /**
     * Busca un idioma por su nombre.
     *
     * @param nombreIdioma el nombre del idioma que se desea buscar.
     * @return el objeto IdiomaEntity correspondiente al nombre, o null si no se encuentra.
     */
    public IdiomaEntity findByNombreIdioma(String nombreIdioma);

    /**
     * Obtiene una lista de todos los idiomas disponibles.
     *
     * @return una lista de objetos IdiomaEntity con todos los idiomas.
     */
    public List<IdiomaEntity> findAll();
}
