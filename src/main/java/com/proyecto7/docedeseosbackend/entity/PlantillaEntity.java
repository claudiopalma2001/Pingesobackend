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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private int idCupon;
    private int idIdioma;
    private int idPlataforma;
    private String urlImagen;
}
