package es.caib.paymentib.core.api.exception;

import es.caib.paymentib.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que faltan datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FaltanDatosException extends ServiceRollbackException {

	public FaltanDatosException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public FaltanDatosException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public FaltanDatosException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public FaltanDatosException(final String messageSNRE) {
		super(messageSNRE);
	}

}
