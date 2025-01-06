package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.repository.JWTMiddlewareRepository;
import com.proyecto7.docedeseosbackend.repository.UsuarioRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Servicio para manejar la generación, validación y decodificación de tokens JWT (JSON Web Tokens).
 * Este servicio implementa la interfaz JWTMiddlewareRepository.
 */

@Service
public class JWTMiddlewareService implements JWTMiddlewareRepository {
    private final SecretKey secretKey;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public JWTMiddlewareService(SecretKey secretKey){
        this.secretKey = secretKey;
    }

    /**
     * Genera un token JWT a partir de un objeto LoginForm.
     *
     * @param form objeto que contiene el correo y la contraseña del usuario.
     * @return el token JWT generado como cadena.
     */
    @Override
    public String generateToken(LoginForm form){
        Date expiration_date = new Date(System.currentTimeMillis() + 14400000);
        /*Permisos del usuario que se intenta logear*/
        UsuarioEntity usuario = usuarioRepository.findByCorreo(form.getCorreo());
        return Jwts
                .builder()
                .claim("correo", form.getCorreo())
                .claim("password", form.getPassword())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration_date)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Valida un token JWT comprobando su firma y su fecha de expiración.
     *
     * @param token el token JWT a validar.
     * @return true si el token es válido; false en caso contrario.
     */
    @Override
    public Boolean validateToken(String token){
        try{
            Jws<Claims> jws = Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return !jws.getPayload().getExpiration().before(new Date());
        }catch(ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch(MalformedJwtException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Decodifica un token JWT y extrae los datos contenidos en él.
     *
     * @param token el token JWT a decodificar.
     * @return un objeto LoginForm que contiene el correo y la contraseña del usuario.
     */
    @Override
    public LoginForm decodeJWT(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String correo = claims.get("correo", String.class);
        String password = claims.get("password", String.class);
        return new LoginForm(correo, password);
    }
}

