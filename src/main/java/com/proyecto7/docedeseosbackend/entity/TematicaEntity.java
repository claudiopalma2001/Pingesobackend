package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
/**
 * Clase que representa la entidad 'Tematica' en la base de datos.
 * Contiene los atributos que describen a una tematica y se utiliza
 * para mapear la tabla 'tematicas' en la base de datos.
 */
@Entity
@Table(name = "tematicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TematicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombreTematica;
    private String descripcion;
}
