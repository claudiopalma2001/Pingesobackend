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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String campoDe;
    private String campoPara;
    private String campoIncluye;
    private LocalDate fecha;
    private Long idCupon;
    private Long idUsuario;
    private Long idPlantilla;
    private int precioF;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    @JsonBackReference
    private CompraEntity compra;
}
