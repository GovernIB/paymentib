package es.caib.paymentib.frontend.model;

import java.util.List;

import es.caib.paymentib.plugins.api.EntidadPago;

/**
 * Datos entidades pago y commit/version.
 *
 * @author Indra
 *
 */
public final class DatosSession {

	/** Entidades pago. */
	private List<EntidadPago> entidadesPago;

	/** Version **/
	private String version;

	/** Commit **/
	private String commit;

	/** Entorno **/
	private String entorno;

	public List<EntidadPago> getEntidadesPago() {
		return entidadesPago;
	}

	public void setEntidadesPago(final List<EntidadPago> entidadesPago) {
		this.entidadesPago = entidadesPago;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(final String commit) {
		this.commit = commit;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

}
