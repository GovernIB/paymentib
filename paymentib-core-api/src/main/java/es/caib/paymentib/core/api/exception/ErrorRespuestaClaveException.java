package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion al interpretar respuesta Clave.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorRespuestaClaveException
        extends ServiceRollbackException {

    /**
     * Constructor GenerarPeticionClaveException.
     *
     * @param cause
     *            Causa
     */
    public ErrorRespuestaClaveException(final Throwable cause) {
        super("Error al interpretar respuesta Clave: " + cause.getMessage(),
                cause);
    }

    /**
     * Constructor GenerarPeticionClaveException.
     *
     * @param cause
     *            Causa
     */
    public ErrorRespuestaClaveException(final String cause) {
        super("Error al interpretar respuesta Clave: " + cause);
    }

}
