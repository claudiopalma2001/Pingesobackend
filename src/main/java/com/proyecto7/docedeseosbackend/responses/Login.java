package com.proyecto7.docedeseosbackend.responses;

/**
 * Clase que representa la respuesta de un intento de inicio de sesión.
 * Contiene un indicador de éxito y un JWT (JSON Web Token) que se devuelve
 * cuando el inicio de sesión es exitoso.
 */

public class Login {
    private boolean success;
    private String jwt;

    /**
     * Constructor para inicializar el estado de éxito y el token JWT.
     *
     * @param success indica si el inicio de sesión fue exitoso.
     * @param jwt el token JWT generado para la sesión.
     */
    public Login(boolean success, String jwt) {
        this.success = success;
        this.jwt = jwt;
    }

    /**
     * Verifica si el inicio de sesión fue exitoso.
     *
     * @return true si fue exitoso, false en caso contrario.
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * Establece el estado de éxito del inicio de sesión.
     *
     * @param success el estado de éxito.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    /**
     * Obtiene el token JWT.
     *
     * @return el token JWT.
     */
    public String getJwt() {
        return jwt;
    }
    /**
     * Establece el token JWT.
     *
     * @param jwt el token JWT.
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
