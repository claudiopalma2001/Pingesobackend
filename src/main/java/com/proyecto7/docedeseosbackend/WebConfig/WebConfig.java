package com.proyecto7.docedeseosbackend.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para habilitar CORS (Cross-Origin Resource Sharing) en la aplicación.
 * Esta configuración permite que los recursos sean accesibles desde diferentes dominios.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS para la aplicación.
     *
     * @param registry el registro de configuraciones de CORS que permite definir qué orígenes
     *                 y métodos son permitidos para las solicitudes CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Especifica el origen permitido
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}