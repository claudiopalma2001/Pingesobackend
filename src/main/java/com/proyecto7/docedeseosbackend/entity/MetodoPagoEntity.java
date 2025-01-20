package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Clase que representa la entidad 'MetodoPago' en la base de datos.
 * Contiene los atributos que describen a un metod de pago y se utiliza
 * para mapear la tabla 'metodoPagos' en la base de datos.
 */
@Entity
@Table(name = "metodopagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagoEntity {
    /**
     * Identificador único del método de pago.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Nombre del método de pago.
     */
    private String nombreMetodo;
    /**
     * Identificador asociado al pago.
     */
    private int idPago;
}
