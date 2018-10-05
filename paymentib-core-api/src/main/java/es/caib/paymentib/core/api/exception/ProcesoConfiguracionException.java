package es.caib.paymentib.core.api.exception;

/**
 * Excepcion que indica que no esta bien configurado la parte de procesos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ProcesoConfiguracionException extends ServiceRollbackException {

    public ProcesoConfiguracionException(String messageSRE) {
        super(messageSRE);
    }

    public ProcesoConfiguracionException(String pmessageSRE,
            Throwable pcauseSRE) {
        super(pmessageSRE, pcauseSRE);
    }

}
