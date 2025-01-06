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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombre;
    private String correo;
    private String password;
    private int edad;
    private String planUsuario;
    private int idRol;

}
