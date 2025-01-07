package com.proyecto7.docedeseosbackend.entity.webpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Clase que representa las propiedades de configuración para Transbank.
 * Permite la configuración del código de comercio y la clave API
 * mediante el uso de propiedades definidas en el archivo de configuración.
 */
@Configuration
@ConfigurationProperties(prefix = "transbank")
public class TransbankProperties {
    /**
     * Código de comercio asignado por Transbank.
     */
    private String commerceCode;
    /**
     * Clave API proporcionada por Transbank.
     */
    private String apiKey;

    // Getters & Setters
    /**
     * Obtiene el código de comercio.
     * @return Código de comercio.
     */
    public String getCommerceCode() {
        return commerceCode;
    }
    /**
     * Establece el código de comercio.
     * @param commerceCode Código de comercio.
     */
    public void setCommerceCode(String commerceCode) {
        this.commerceCode = commerceCode;
    }
    /**
     * Obtiene la clave API.
     * @return Clave API.
     */
    public String getApiKey() {
        return apiKey;
    }
    /**
     * Establece la clave API.
     * @param apiKey Clave API.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

