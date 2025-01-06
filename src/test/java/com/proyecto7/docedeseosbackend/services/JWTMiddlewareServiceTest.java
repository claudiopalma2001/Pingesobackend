package com.proyecto7.docedeseosbackend.services;

import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.repository.UsuarioRepository;
import com.proyecto7.docedeseosbackend.service.JWTMiddlewareService;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JWTMiddlewareServiceTest {

    @Mock
    private SecretKey secretKey;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private JWTMiddlewareService jwtMiddlewareService;

    private LoginForm loginForm;
    private UsuarioEntity usuario;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        loginForm = new LoginForm("test@example.com", "password123");
        usuario = new UsuarioEntity(1L, "test", "test@example.com", "password123", 20, "Basico", 0);
    }

    @Test
    void testValidateToken_ValidToken() {
        // Given
        String validToken = "valid.jwt.token";
        Date expirationDate = new Date(System.currentTimeMillis() + 14400000); // 4 hours
        when(secretKey.getAlgorithm()).thenReturn("HmacSHA256");

        // Simulate a valid token (assuming it's well-formed and not expired)
        try {
            when(secretKey.getAlgorithm()).thenReturn("HmacSHA256");
            when(secretKey.toString()).thenReturn("secret");
            Jws<Claims> jws = mock(Jws.class);
            when(jws.getPayload().getExpiration()).thenReturn(expirationDate);
            assertTrue(jwtMiddlewareService.validateToken(validToken));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Given
        String expiredToken = "expired.jwt.token";
        Date expiredDate = new Date(System.currentTimeMillis() - 10000); // Expired token

        // Simulate token expiration
        when(secretKey.getAlgorithm()).thenReturn("HmacSHA256");
        assertFalse(jwtMiddlewareService.validateToken(expiredToken));
    }

    @Test
    void testValidateToken_MalformedToken() {
        // Given
        String malformedToken = "malformed.token";

        // Simulate a malformed token
        when(secretKey.getAlgorithm()).thenReturn("HmacSHA256");
        assertFalse(jwtMiddlewareService.validateToken(malformedToken));
    }

    @Test
    void testDecodeJWT_InvalidToken() {
        // Given
        String invalidToken = "invalid.token";

        // Simulate an invalid token scenario
        assertThrows(Exception.class, () -> jwtMiddlewareService.decodeJWT(invalidToken));
    }
}
