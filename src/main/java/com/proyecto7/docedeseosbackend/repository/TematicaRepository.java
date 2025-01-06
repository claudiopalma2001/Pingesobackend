package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con las tematicas.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */

@Repository
public interface TematicaRepository extends JpaRepository<TematicaEntity, Long> {

    /**
     * Busca una temática por su nombre.
     *
     * @param nombreTematica el nombre de la temática a buscar.
     * @return la entidad {@link TematicaEntity} de la temática encontrada, o null si no se encuentra.
     */
    TematicaEntity findByNombreTematica(String nombreTematica);

    /**
     * Busca temáticas que contienen una determinada descripción.
     *
     * @param descripcion la descripción a buscar dentro de las temáticas.
     * @return una lista de entidades {@link TematicaEntity} que contienen la descripción especificada.
     */
    List<TematicaEntity> findByDescripcionContaining(String descripcion);
}
