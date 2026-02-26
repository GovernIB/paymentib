package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion inicio pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class InicioPagoException extends ServiceRollbackException {

    /**
     * Constructor InicioPagoException.
     *
     * @param cause
     *            Causa
     */
    public InicioPagoException(final String idSesionPago, final Throwable cause) {
        super(idSesionPago + " - Error iniciando pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor InicioPagoException.
     *
     * @param mensaje
     *            mensaje
     */
    public InicioPagoException(final String idSesionPago, final String mensaje) {
        super(idSesionPago + " - Error iniciando pago: " + mensaje);
    }

}
