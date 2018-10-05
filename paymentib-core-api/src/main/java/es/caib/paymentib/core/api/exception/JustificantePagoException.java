package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion obtener entidades pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class JustificantePagoException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public JustificantePagoException(final Throwable cause) {
        super("Error obtener justificante pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor.
     *
     * @param mensaje
     *            mensaje
     */
    public JustificantePagoException(final String mensaje) {
        super("Error obtener justificante pago: " + mensaje);
    }

}
