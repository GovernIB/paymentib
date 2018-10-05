package es.caib.paymentib.plugins.api;

/**
 * Entidad a través de la que se realiza el pago a través de la pasarela.
 *
 * @author Indra
 *
 */
public class EntidadPago {

	/**
	 * Código entidad.
	 */
	private String codigo;

	/**
	 * Descripción entidad.
	 */
	private String descripcion;

	/**
	 * Url del logo.
	 */
	private String logo;

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo
	 *            codigo a establecer
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion
	 *            descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
