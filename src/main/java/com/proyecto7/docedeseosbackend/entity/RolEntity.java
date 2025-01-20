package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Clase que representa la entidad 'Rol' en la base de datos.
 * Esta clase mapea la tabla 'roles' y se utiliza para definir los diferentes roles
 * que los usuarios pueden tener dentro del sistema, como 'Usuario' o 'Administrador'.
 */

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolEntity {
    /**
     * Identificador único del rol.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Nombre del rol asignado al usuario.
     */
    private String nombreRol;
}
