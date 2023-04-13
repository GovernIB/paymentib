package es.caib.paymentib.core.api.exception;

import es.caib.paymentib.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que ha habido un problema a la hora de cargar la
 * configuración.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class CargaConfiguracionException extends ServiceRollbackException {

	public CargaConfiguracionException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public CargaConfiguracionException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public CargaConfiguracionException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public CargaConfiguracionException(final String messageSNRE) {
		super(messageSNRE);
	}

}
