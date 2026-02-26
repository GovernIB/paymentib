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
    public JustificantePagoException(final String idSesionPago, final Throwable cause) {
        super(idSesionPago + " - Error obtener justificante pago: " + cause.getMessage(), cause);
    }

}
