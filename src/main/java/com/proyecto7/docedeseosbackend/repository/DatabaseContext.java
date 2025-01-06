package com.proyecto7.docedeseosbackend.repository;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

import javax.crypto.SecretKey;

/**
 * Clase de configuración para establecer conexiones con la base de datos y proporcionar una clave secreta
 * para la firma de tokens JWT. Esta clase utiliza las propiedades configuradas en el archivo de propiedades
 * de la aplicación.
 */

@Configuration
public class DatabaseContext {
    @Value("${database.url}")
    private String url;

    @Value("${database.user}")
    private String user;

    @Value("${database.pass}")
    private String pass;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Crea un bean Sql2o para la conexión a la base de datos.
     *
     * @return una instancia de Sql2o configurada con la URL, usuario y contraseña especificados.
     */
    @Bean
    public Sql2o sql2o() {
        return new Sql2o(url, user, pass);
    }

    /**
     * Crea un bean SecretKey para la firma de tokens JWT.
     *
     * @return una clave secreta generada a partir de la cadena base64 especificada en la configuración.
     */
    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
