package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
/**
 * Clase que representa la entidad 'Idioma' en la base de datos.
 * Contiene los atributos que describen a un idioma y se utiliza
 * para mapear la tabla 'idiomas' en la base de datos.
 */
@Entity
@Table(name = "idiomas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdiomaEntity {
    /**
     * Identificador único del idioma.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Nombre del idioma.
     */
    private String nombreIdioma;
}
