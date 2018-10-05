package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion estado sesión pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadoSesionPagoException extends ServiceRollbackException {

    /**
     * Constructor InicioPagoException.
     *
     * @param cause
     *            Causa
     */
    public EstadoSesionPagoException(final Throwable cause) {
        super("Error estado sesión pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor InicioPagoException.
     *
     * @param mensaje
     *            mensaje
     */
    public EstadoSesionPagoException(final String mensaje) {
        super("Error estado sesión pago:: " + mensaje);
    }

}
