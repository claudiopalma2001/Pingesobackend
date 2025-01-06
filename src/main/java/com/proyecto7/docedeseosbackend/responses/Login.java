package com.proyecto7.docedeseosbackend.responses;

/**
 * Clase que representa la respuesta de un intento de inicio de sesión.
 * Contiene un indicador de éxito y un JWT (JSON Web Token) que se devuelve
 * cuando el inicio de sesión es exitoso.
 */

public class Login {
    private boolean success;
    private String jwt;

    public Login(boolean success, String jwt) {
        this.success = success;
        this.jwt = jwt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
