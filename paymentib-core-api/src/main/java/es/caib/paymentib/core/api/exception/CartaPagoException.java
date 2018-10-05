package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion carta pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class CartaPagoException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public CartaPagoException(final Throwable cause) {
        super("Error obteniendo carta pago: " + cause.getMessage(), cause);
    }

    /**
     * Constructor.
     *
     * @param mensaje
     *            mensaje
     */
    public CartaPagoException(final String mensaje) {
        super("Error obteniendo carta pago: " + mensaje);
    }

}
