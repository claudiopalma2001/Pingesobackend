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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombreCupon;
    private String tipo;
    private int idTematica;
    private int precio;
}
