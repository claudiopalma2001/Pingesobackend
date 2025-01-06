package com.proyecto7.docedeseosbackend.entity.forms;

/**
 * Clase que representa el formulario de inicio de sesión.
 * Contiene los campos necesarios para autenticar a un usuario.
 */

public class LoginForm {
    private String correo;
    private String password;

    public LoginForm(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String email) {
        this.correo = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
