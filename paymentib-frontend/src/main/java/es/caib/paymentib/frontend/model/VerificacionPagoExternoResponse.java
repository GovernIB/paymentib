package es.caib.paymentib.frontend.model;

import es.caib.paymentib.plugins.api.TypeValidacionPagoExterno;

/**
 * Resultado de la verificación de un pago externo.
 */
public class VerificacionPagoExternoResponse {

    /** Estado verificación.*/
    private TypeValidacionPagoExterno estado;
    /** URL de redirección tras mostrar resultado verificación (nulo se queda en la pagina). */
    private String url;
    /** Mensaje de la verificación. */
    private VerificacionPagoExternoMensaje mensaje;

    /**
     * Obtiene el estado de la verificación.
     * @return estado de la verificación (CORRECTO o ERROR).
     */
    public TypeValidacionPagoExterno getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la verificación.
     * @param estado estado de la verificación (CORRECTO o ERROR).
     */
    public void setEstado(TypeValidacionPagoExterno estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la URL de redirección tras mostrar el resultado de la verificación.
     * @return URL de redirección o nulo si no se redirige.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL de redirección tras mostrar el resultado de la verificación.
     * @param url URL de redirección o nulo si no se redirige.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el mensaje de la verificación.
     * @return mensaje de la verificación.
     */
    public VerificacionPagoExternoMensaje getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de la verificación.
     * @param mensaje mensaje de la verificación.
     */
    public void setMensaje(VerificacionPagoExternoMensaje mensaje) {
        this.mensaje = mensaje;
    }

}
