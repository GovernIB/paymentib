package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion al recupear sesion pago por token.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TokenSesionPagoException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public TokenSesionPagoException(final String token) {
        super("No se encuentra sesion pago activa con token " + token);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public TokenSesionPagoException(final String token, final Throwable cause) {
        super("No se encuentra sesion pago activa con token " + token + ": "
                + cause.getMessage(), cause);
    }

    /**
     * Constructor.
     *
     * @param mensaje
     *            mensaje
     */
    public TokenSesionPagoException(final String token, final String mensaje) {
        super("No se encuentra sesion pago activa con token " + token + ": "
                + mensaje);
    }

}
