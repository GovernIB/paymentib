package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepciónque indica que no existe plugin indicado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PluginErrorException extends ServiceRollbackException {

    /**
     * Constructor PluginNoExisteException
     *
     * @param message
     *            message
     */
    public PluginErrorException(final String message) {
        super(message);
    }

    /**
     * Constructor PluginNoExisteException
     *
     * @param message
     *            message
     * @param ex
     *            excepción
     */
    public PluginErrorException(final String message, Exception ex) {
        super(message, ex);
    }
}
