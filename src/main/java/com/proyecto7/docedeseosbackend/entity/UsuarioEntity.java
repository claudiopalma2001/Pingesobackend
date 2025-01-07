package com.proyecto7.docedeseosbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Clase que representa la entidad 'Usuario' en la base de datos.
 * Contiene los atributos que describen a un usuario y se utiliza
 * para mapear la tabla 'usuarios' en la base de datos.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    /**
     * Identificador único del usuario.
     * Se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    /**
     * Nombre del usuario.
     */
    private String nombre;
    /**
     * Correo electrónico del usuario.
     */
    private String correo;
    /**
     * Contrasena del usuario.
     */
    private String password;
    /**
     * Edad del usuario.
     */
    private int edad;
    /**
     * Plan del usuario (por ejemplo, gratuito o premium).
     */
    private String planUsuario;
    /**
     * Identificador del rol asociado al usuario.
     */
    private int idRol;

}
