package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion consultar tasa.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConsultarTasaException extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public ConsultarTasaException(final String idTasa, final Throwable cause) {
        super("Error consultando tasa " + idTasa + ": " + cause.getMessage(),
                cause);
    }

}
