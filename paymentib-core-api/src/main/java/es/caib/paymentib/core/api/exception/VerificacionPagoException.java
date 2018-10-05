package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion verificar pago contra la pasarela de pagos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class VerificacionPagoException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public VerificacionPagoException(final String identificador,
            final Throwable cause) {
        super("Error verificando pago " + identificador + ": "
                + cause.getMessage(), cause);
    }

}
