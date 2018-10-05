package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion ticket no valido.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionException extends ServiceRollbackException {

    /**
     * Constructor ConfiguracionException.
     *
     * @param cause
     *            Causa
     */
    public ConfiguracionException(final Throwable cause) {
        super("Error cargando configuracion: " + cause.getMessage(), cause);
    }

    /**
     * Constructor ConfiguracionException.
     *
     * @param mensaje
     *            mensaje
     */
    public ConfiguracionException(final String mensaje) {
        super("Error configuracion: " + mensaje);
    }

}
