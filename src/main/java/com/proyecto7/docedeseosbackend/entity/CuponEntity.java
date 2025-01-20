package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
/**
 * Clase que representa la entidad 'Cupon' en la base de datos.
 * Contiene los atributos que describen a un cupon y se utiliza
 * para mapear la tabla 'cupones' en la base de datos.
 */
@Entity
@Table(name = "cupones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuponEntity {
    /**
     * Identificador único del cupón.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Nombre del cupón.
     */
    private String nombreCupon;
    /**
     * Tipo de cupón, puede representar la categoría o clase del cupón.
     */
    private String tipo;
    /**
     * Identificador temático asociado al cupón.
     */
    private int idTematica;
    /**
     * Precio del cupón.
     */
    private int precio;
}
