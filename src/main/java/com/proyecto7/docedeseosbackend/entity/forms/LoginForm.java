package com.proyecto7.docedeseosbackend.entity.forms;

/**
 * Clase que representa el formulario de inicio de sesión.
 * Contiene los campos necesarios para autenticar a un usuario.
 */

public class LoginForm {
    /**
     * Correo electrónico del usuario.
     */
    private String correo;
    /**
     * Contrasena del usuario.
     */
    private String password;

    /**
     * Constructor para inicializar el formulario con correo y contraseña.
     * @param correo Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    public LoginForm(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    /**
     * Obtiene el correo electrónico.
     * @return Correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico.
     * @param email Correo electrónico del usuario.
     */
    public void setCorreo(String email) {
        this.correo = email;
    }

    /**
     * Obtiene la contraseña.
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Establece la contraseña.
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
