package com.proyecto7.docedeseosbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad 'Plantilla' en la base de datos.
 * Contiene los atributos que describen a una plantilla y se utiliza
 * para mapear la tabla 'plantillas' en la base de datos.
 */
@Entity
@Table(name = "plantillas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaEntity {
    /**
     * Identificador único de la plantilla.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Identificador del cupón asociado a la plantilla.
     */
    private int idCupon;
    /**
     * Identificador del idioma asociado a la plantilla.
     */
    private int idIdioma;
    /**
     * Identificador de la plataforma asociada a la plantilla.
     */
    private int idPlataforma;
    /**
     * URL de la imagen asociada a la plantilla.
     */
    private String urlImagen;
}
