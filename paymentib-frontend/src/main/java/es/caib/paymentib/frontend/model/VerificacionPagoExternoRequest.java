package es.caib.paymentib.frontend.model;

/**
 * Resultado de la verificación de un pago externo.
 */
public class VerificacionPagoExternoRequest {

    /** Localizador. */
    private String localizador;
    /** Fecha. */
    private String fecha;


    /**
     * Obtiene el localizador del pago externo.
     * @return el localizador.
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Establece el localizador del pago externo.
     * @param localizador el localizador a establecer.
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    /**
     * Obtiene la fecha del pago externo.
     * @return la fecha.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del pago externo.
     * @param fecha la fecha a establecer.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
