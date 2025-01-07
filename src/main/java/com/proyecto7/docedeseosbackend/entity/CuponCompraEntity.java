package com.proyecto7.docedeseosbackend.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Clase que representa la entidad 'CuponCompra' en la base de datos.
 * Esta clase mapea la tabla 'cuponcompra' y se utiliza para asociar cupones con compras realizadas por los usuarios.
 * Contiene los atributos que permiten identificar la relación entre cupones y compras.
 */

@Entity
@Table(name = "cuponcompra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuponCompraEntity {
    /**
     * Identificador único de la relación entre el cupón y la compra.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Identificador del cupón asociado a la compra.
     */
    private Long idCupon;
    /**
     * Identificador de la compra asociada al cupón.
     */
    private Long idCompra;
}
