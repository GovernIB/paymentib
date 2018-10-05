package es.caib.paymentib.core.api.exception;

import org.springframework.dao.DataAccessException;

/**
 *
 * Capturamos en una excepción de negocio la excepcion que genera spring para
 * errores de base de datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatabaseException extends ServiceRollbackException {

    @Override
    public boolean isNegocioException() {
        return false;
    }

    /**
     * Constructor DatabaseException.
     *
     * @param cause
     *            Causa
     */
    public DatabaseException(final DataAccessException cause) {
        super("Error en la capa de base de datos: " + cause.getMessage(),
                cause);
    }

    /**
     * Constructor DatabaseException.
     *
     * @param cause
     *            Causa
     */
    public DatabaseException(final String cause) {
        super("Error en la capa de base de datos: " + cause);
    }

}
