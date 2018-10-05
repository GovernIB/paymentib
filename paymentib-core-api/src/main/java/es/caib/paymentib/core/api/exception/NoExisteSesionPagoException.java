package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion al recuperar sesion pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExisteSesionPagoException
        extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public NoExisteSesionPagoException(final String identificador) {
        super("No se encuentra sesion pago activa con identificador "
                + identificador);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public NoExisteSesionPagoException(final String identificador,
            final Throwable cause) {
        super("No se encuentra sesion pago activa con identificador "
                + identificador + ": " + cause.getMessage(), cause);
    }

    /**
     * Constructor.
     *
     * @param mensaje
     *            mensaje
     */
    public NoExisteSesionPagoException(final String identificador,
            final String mensaje) {
        super("No se encuentra sesion pago activa con identificador "
                + identificador + ": " + mensaje);
    }

}
