package com.proyecto7.docedeseosbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Clase que representa la entidad 'CuponFinal' en la base de datos.
 * Esta clase mapea la tabla 'cuponfinal' y se utiliza para almacenar los cupones finales
 * que se asignan a los usuarios, incluyendo detalles como los campos del cupón,
 * la fecha de creación, y la relación con los cupones, usuarios y plantillas.
 */

@Entity
@Table(name = "cuponfinal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuponFinalEntity {
    /**
     * Identificador único del cupón final.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Campo que especifica el remitente del cupón.
     */
    private String campoDe;
    /**
     * Campo que especifica el destinatario del cupón.
     */
    private String campoPara;
    /**
     * Campo que describe lo que incluye el cupón.
     */
    private String campoIncluye;
    /**
     * Fecha en la que se puede cobrar el cupón.
     */
    private LocalDate fecha;
    /**
     * Identificador del cupón relacionado.
     */
    private Long idCupon;
    /**
     * Identificador del usuario al que se asigna el cupón.
     */
    private Long idUsuario;
    /**
     * Identificador de la plantilla asociada al cupón.
     */
    private Long idPlantilla;
    /**
     * Precio final del cupón.
     */
    private int precioF;
    /**
     * Relación con la compra a la que pertenece este cupón.
     * Se mapea mediante una relación muchos a uno con la entidad 'CompraEntity'.
     */
    @ManyToOne
    @JoinColumn(name = "compra_id")
    @JsonBackReference
    private CompraEntity compra;
}
