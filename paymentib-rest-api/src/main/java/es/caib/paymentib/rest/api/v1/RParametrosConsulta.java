package es.caib.paymentib.rest.api.v1;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos pago y urlCallback.
 *
 * @author Indra
 *
 */
@ApiModel(value = "Parametros datos pago", description = "Parametros pago")
public class RParametrosConsulta {

	/** Datos de pago */
	@ApiModelProperty(value = "Filtro de pago")
	private RFiltroPago filtro;

	/** fechaCreacion */
	@ApiModelProperty(value = "fechaDesde")
	private Date fechaDesde;

	/** fechaPago */
	@ApiModelProperty(value = "fechaHasta")
	private Date fechaHasta;

	/** numPag */
	@ApiModelProperty(value = "numPag")
	private Long numPag;

	/** maxNumElem */
	@ApiModelProperty(value = "maxNumElem")
	private Long maxNumElem;

	/**
	 * @return the filtro
	 */
	public final RFiltroPago getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public final void setFiltro(RFiltroPago filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the fechaCre
	 */
	public final Date getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param fechaCre the fechaCre to set
	 */
	public final void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * @return the fechaPago
	 */
	public final Date getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public final void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	/**
	 * @return the numPag
	 */
	public final Long getNumPag() {
		return numPag;
	}

	/**
	 * @param numPag the numPag to set
	 */
	public final void setNumPag(Long numPag) {
		this.numPag = numPag;
	}

	/**
	 * @return the maxNumElem
	 */
	public final Long getMaxNumElem() {
		return maxNumElem;
	}

	/**
	 * @param maxNumElem the maxNumElem to set
	 */
	public final void setMaxNumElem(Long maxNumElem) {
		this.maxNumElem = maxNumElem;
	}

}
