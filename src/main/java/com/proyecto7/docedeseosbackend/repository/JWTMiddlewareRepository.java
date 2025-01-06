package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;

/**
 * Interfaz para la gestión de operaciones relacionadas con la generación y validación de tokens JWT.
 */

public interface JWTMiddlewareRepository {

    /**
     * Genera un token JWT basado en las credenciales proporcionadas en el formulario de inicio de sesión.
     *
     * @param form el formulario de inicio de sesión que contiene el correo y la contraseña del usuario.
     * @return el token JWT generado como una cadena.
     */
    public String generateToken(LoginForm form);

    /**
     * Valida un token JWT para verificar su autenticidad.
     *
     * @param token el token JWT que se desea validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public Boolean validateToken(String token);

    /**
     * Decodifica un token JWT y extrae la información contenida en él.
     *
     * @param token el token JWT que se desea decodificar.
     * @return un objeto LoginForm que contiene los datos decodificados del token.
     */
    public LoginForm decodeJWT(String token);
}
