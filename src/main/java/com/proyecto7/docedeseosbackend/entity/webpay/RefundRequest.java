package com.proyecto7.docedeseosbackend.entity.webpay;

/**
 * Clase que representa una solicitud de reembolso en Webpay.
 * Contiene el monto necesario para procesar un reembolso.
 */
public class RefundRequest {
    /**
     * Monto a reembolsar.
     */
    private double amount;

    // Getters y setters
    /**
     * Obtiene el monto a reembolsar.
     * @return Monto a reembolsar.
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Establece el monto a reembolsar.
     * @param amount Monto a reembolsar.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
