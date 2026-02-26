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
     * @param mensaje
     *            mensaje
     */
    public EstadoSesionPagoException(final String idSesionPago, final String mensaje) {
        super(idSesionPago + " - Error estado sesión pago: " + mensaje);
    }

}
