package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion no existe plantilla justificante pago entidad.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExistePlantillaJustificanteEntidadException
        extends ServiceRollbackException {

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public NoExistePlantillaJustificanteEntidadException(
            final String identificador, String pathFichero) {
        super("No se encuentra plantilla justificante generico de pago para entidad "
                + identificador + ", fichero: " + pathFichero);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            Causa
     */
    public NoExistePlantillaJustificanteEntidadException(
            final String identificador, String pathFichero, Throwable t) {
        super("Excepcion al cargar plantilla justificante generico de pago para entidad "
                + identificador + ", fichero: " + pathFichero + ": "
                + t.getMessage(), t);
    }

}
