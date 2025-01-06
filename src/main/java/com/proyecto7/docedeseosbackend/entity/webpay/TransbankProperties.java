package com.proyecto7.docedeseosbackend.entity.webpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "transbank")
public class TransbankProperties {
    private String commerceCode;
    private String apiKey;

    // Getters & Setters
    public String getCommerceCode() {
        return commerceCode;
    }

    public void setCommerceCode(String commerceCode) {
        this.commerceCode = commerceCode;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

