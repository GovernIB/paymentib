package es.caib.paymentib.core.api.exception;

import es.caib.paymentib.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que faltan datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class MaxNumFilasException extends ServiceRollbackException {

	public MaxNumFilasException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public MaxNumFilasException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public MaxNumFilasException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public MaxNumFilasException(final String messageSNRE) {
		super(messageSNRE);
	}

}
