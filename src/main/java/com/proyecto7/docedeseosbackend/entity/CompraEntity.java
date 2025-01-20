package com.proyecto7.docedeseosbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa la entidad 'Compra' en la base de datos.
 * Esta clase mapea la tabla 'compras' y contiene los atributos que describen una compra realizada por un usuario.
 * Los atributos incluyen el ID de la compra, el ID del usuario que realizó la compra,
 * la fecha en que se realizó la compra y el monto total de la misma.
 *
 * Se utiliza en el contexto de un sistema para registrar las compras realizadas por los usuarios.
 */
@Entity
@Table(name = "compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraEntity {

    /**
     * Identificador único de la compra.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Identificador del usuario que realizó la compra.
     */
    private Long idUsuario;
    /**
     * Fecha en la que se realizó la compra.
     */
    private LocalDate fechaCompra;
    /**
     * Monto total de la compra.
     */
    private float montoTotal;
    /**
     * Lista de cupones finales asociados a la compra.
     * Se define una relación uno a muchos con la entidad 'CuponFinalEntity'.
     * Los cupones se eliminan en cascada si la compra es eliminada.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compra", orphanRemoval = true)
    @JsonManagedReference
    private List<CuponFinalEntity> cuponesFinales;

}
