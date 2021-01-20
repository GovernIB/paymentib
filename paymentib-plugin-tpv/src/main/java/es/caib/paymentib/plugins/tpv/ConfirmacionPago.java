package es.caib.paymentib.plugins.tpv;

import java.util.Date;

import es.caib.paymentib.plugins.api.TypeEstadoPago;

/**
 * Datos confirmación pago.
 *
 * @author Indra
 *
 */
public class ConfirmacionPago {

	/** Estado pago. */
	private TypeEstadoPago estado;

	/** Fecha pago. */
	private Date fecha;

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoPago getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param estado
	 *                   estado a establecer
	 */
	public void setEstado(final TypeEstadoPago estado) {
		this.estado = estado;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param fecha
	 *                  fecha a establecer
	 */
	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

}
