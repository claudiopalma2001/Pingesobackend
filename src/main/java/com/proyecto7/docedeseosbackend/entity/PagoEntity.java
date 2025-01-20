package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
/**
 * Clase que representa la entidad 'Pago' en la base de datos.
 * Contiene los atributos que describen a un pago y se utiliza
 * para mapear la tabla 'pagos' en la base de datos.
 */

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {
    /**
     * Identificador único del pago.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Monto del pago.
     */
    private double monto;
    /**
     * Boleta asociada al pago.
     */
    private String boleta;
}
