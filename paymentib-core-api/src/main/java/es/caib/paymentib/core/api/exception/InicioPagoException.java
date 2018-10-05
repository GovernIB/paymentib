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
    public InicioPagoException(final Throwable cause) {
        super("Error iniciando pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor InicioPagoException.
     *
     * @param mensaje
     *            mensaje
     */
    public InicioPagoException(final String mensaje) {
        super("Error iniciando pago: " + mensaje);
    }

}
