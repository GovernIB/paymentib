package es.caib.paymentib.rest.api.v1;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class RFiltroPago {
	@ApiModelProperty(value = "identificador")
	private String id;

	@ApiModelProperty(value = "nif")
	private String nif;

	@ApiModelProperty(value = "nombre")
	private String nombre;

	@ApiModelProperty(value = "fechaCreacion")
	private Date fechaCre;

	@ApiModelProperty(value = "pasarelaId")
	private String pasarela;

	@ApiModelProperty(value = "aplicacionId")
	private String aplicacion;

	@ApiModelProperty(value = "importe")
	private Integer importe;

	@ApiModelProperty(value = "estado")
	private String estado;

	@ApiModelProperty(value = "fechaPago")
	private Date fechaPago;

	@ApiModelProperty(value = "entidad")
	private String entidad;

	@ApiModelProperty(value = "claveTramitacion")
	private String claveTramitacion;

	@ApiModelProperty(value = "idTramite")
	private String idTramite;

	@ApiModelProperty(value = "versionTramite")
	private Integer versionTramite;

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the fechaCre
	 */
	public final Date getFechaCre() {
		return fechaCre;
	}

	/**
	 * @param fechaCre the fechaCre to set
	 */
	public final void setFechaCre(Date fechaCre) {
		this.fechaCre = fechaCre;
	}

	/**
	 * @return the pasarela
	 */
	public final String getPasarela() {
		return pasarela;
	}

	/**
	 * @param pasarela the pasarela to set
	 */
	public final void setPasarela(String pasarela) {
		this.pasarela = pasarela;
	}

	/**
	 * @return the aplicacion
	 */
	public final String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public final void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the importe
	 */
	public final Integer getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public final void setImporte(Integer importe) {
		this.importe = importe;
	}

	/**
	 * @return the estado
	 */
	public final String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public final void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaPago
	 */
	public final Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public final void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the entidad
	 */
	public final String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public final void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the claveTramitacion
	 */
	public String getClaveTramitacion() {
		return claveTramitacion;
	}

	/**
	 * @param claveTramitacion the claveTramitacion to set
	 */
	public void setClaveTramitacion(String claveTramitacion) {
		this.claveTramitacion = claveTramitacion;
	}

	/**
	 * @return the idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the versionTramite
	 */
	public Integer getVersionTramite() {
		return versionTramite;
	}

	/**
	 * @param versionTramite the versionTramite to set
	 */
	public void setVersionTramite(Integer versionTramite) {
		this.versionTramite = versionTramite;
	}
}
