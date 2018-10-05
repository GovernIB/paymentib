package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion obtener entidades pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ObtenerEntidadesException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public ObtenerEntidadesException(final Throwable cause) {
        super("Error obtener entidades pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor.
     *
     * @param mensaje
     *            mensaje
     */
    public ObtenerEntidadesException(final String mensaje) {
        super("Error obtener entidades pago: " + mensaje);
    }

}
