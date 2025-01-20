package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Clase que representa la entidad 'Plataforma' en la base de datos.
 * Esta clase mapea la tabla 'plataforma' y se utiliza para almacenar información
 * sobre las plataformas asociadas al sistema, incluyendo el tipo de plataforma.
 */

@Entity
@Table(name = "plataforma")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlataformaEntity {
    /**
     * Identificador único de la plataforma.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Tipo de plataforma asociada al sistema.
     */
    private String tipoPlataforma;
}
