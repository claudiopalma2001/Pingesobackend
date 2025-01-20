package com.proyecto7.docedeseosbackend.entity.webpay;

/**
 * Clase que representa una solicitud de transacción en Webpay.
 * Contiene los datos necesarios para iniciar una transacción,
 * incluyendo la orden de compra, el ID de sesión, el monto y la URL de retorno.
 */
public class TransactionRequest {
    /**
     * Orden de compra única asociada a la transacción.
     */
    private String buyOrder;
    /**
     * ID de sesión único para la transacción.
     */
    private String sessionId;
    /**
     * Monto total de la transacción.
     */
    private double amount;
    /**
     * URL de retorno a la que se redirige después de completar la transacción.
     */
    private String returnUrl;

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
     * Obtiene el ID de sesión.
     * @return ID de sesión.
     */
    public String getSessionId() {
        return sessionId;
    }
    /**
     * Establece el ID de sesión.
     * @param sessionId ID de sesión.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    /**
     * Obtiene el monto de la transacción.
     * @return Monto de la transacción.
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Establece el monto de la transacción.
     * @param amount Monto de la transacción.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    /**
     * Obtiene la URL de retorno.
     * @return URL de retorno.
     */
    public String getReturnUrl() {
        return returnUrl;
    }
    /**
     * Establece la URL de retorno.
     * @param returnUrl URL de retorno.
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
