package com.proyecto7.docedeseosbackend.entity.webpay;

/**
 * Clase que representa una solicitud de captura en Webpay.
 * Contiene los datos necesarios para capturar un pago autorizado,
 * incluyendo la orden de compra, el código de autorización y el monto.
 */
public class CaptureRequest {
    /**
     * Orden de compra única asociada a la captura.
     */
    private String buyOrder;
    /**
     * Código de autorización para capturar el pago.
     */
    private String authorizationCode;
    /**
     * Monto total a capturar.
     */
    private double amount;

    // Getters y setters
    /**
     * Obtiene la orden de compra.
     * @return Orden de compra.
     */
    public String getBuyOrder() {
        return buyOrder;
    }
    /**
     * Establece la orden de compra.
     * @param buyOrder Orden de compra.
     */
    public void setBuyOrder(String buyOrder) {
        this.buyOrder = buyOrder;
    }
    /**
     * Obtiene el código de autorización.
     * @return Código de autorización.
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }
    /**
     * Establece el código de autorización.
     * @param authorizationCode Código de autorización.
     */
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
    /**
     * Obtiene el monto a capturar.
     * @return Monto a capturar.
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Establece el monto a capturar.
     * @param amount Monto a capturar.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
