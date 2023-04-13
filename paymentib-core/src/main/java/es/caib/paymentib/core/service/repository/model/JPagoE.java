package es.caib.paymentib.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;

/**
 * JPagoE
 *
 */
@Entity
@Table(name = "PIB_PAGOE")
public class JPagoE implements IModelApi {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PIB_PAGOE_SEQ", allocationSize = 1, sequenceName = "PIB_PAGOE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PIB_PAGOE_SEQ")
    @Column(name = "PAE_CODIGO", unique = true, nullable = false, precision = 18)
    private Long codigo;

    @Column(name = "PAE_APPID", nullable = false, length = 100)
    private String aplicacionId;

    @Column(name = "PAE_CONCEPTO", nullable = false, length = 1000)
    private String concepto;

    @Column(name = "PAE_DETPAE", length = 1000)
    private String detallePago;

    @Column(name = "PAE_ENTID", nullable = false, length = 100)
    private String entidadId;

    @Column(name = "PAE_ESTADO", length = 1)
    private String estado;

    @Column(name = "PAE_PASERC")
    private String codigoErrorPasarela;

    @Column(name = "PAE_PASERM")
    private String mensajeErrorPasarela;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAE_FECHA", nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAE_FECPAG")
    private Date fechaPago;

    @Column(name = "PAE_IDENTI", nullable = false, length = 100)
    private String identificador;

    @Column(name = "PAE_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "PAE_IMPORT", nullable = false, precision = 10)
    private Integer importe;

    @Column(name = "PAE_LOCALI", length = 100)
    private String localizador;

    @Column(name = "PAE_NIF", nullable = false, length = 10)
    private String sujetoPasivoNif;

    @Column(name = "PAE_NOMBRE", nullable = false, length = 1000)
    private String sujetoPasivoNombre;

    @Column(name = "PAE_MODELO", nullable = false, length = 100)
    private String modelo;

    @Column(name = "PAE_ORGID", length = 100)
    private String organoId;

    @Column(name = "PAE_PASPID", nullable = false, length = 100)
    private String pasarelaId;

    @Column(name = "PAE_TASAID", length = 100)
    private String tasaId;

    @Column(name = "PAE_TOKEN", length = 100)
    private String token;

    @Column(name = "PAE_URLCBK", nullable = false, length = 500)
    private String urlCallbackOrigen;

    @Column(name = "PAE_USUCFM", length = 100)
    private String usuarioConfirmacion;

    @Column(name = "PAE_METPAG", nullable = true, length = 20)
    private String metodosPago;

    @Column(name = "PAE_IDTRA", nullable = false, length = 100)
    private String idTramite;

	@Column(name = "PAE_VERTRA", nullable = false, precision = 2, scale = 0)
    private Integer versionTramite;

    public JPagoE() {
        super();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    public String getAplicacionId() {
        return aplicacionId;
    }

    public void setAplicacionId(final String aplicacionId) {
        this.aplicacionId = aplicacionId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }

    public String getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(final String detallePago) {
        this.detallePago = detallePago;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(final String entidadId) {
        this.entidadId = entidadId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(final String identificador) {
        this.identificador = identificador;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(final String idioma) {
        this.idioma = idioma;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(final Integer importe) {
        this.importe = importe;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(final String localizador) {
        this.localizador = localizador;
    }

    public String getSujetoPasivoNif() {
        return sujetoPasivoNif;
    }

    public void setSujetoPasivoNif(final String sujetoPasivoNif) {
        this.sujetoPasivoNif = sujetoPasivoNif;
    }

    public String getSujetoPasivoNombre() {
        return sujetoPasivoNombre;
    }

    public void setSujetoPasivoNombre(final String sujetoPasivoNombre) {
        this.sujetoPasivoNombre = sujetoPasivoNombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(final String modelo) {
        this.modelo = modelo;
    }

    public String getOrganoId() {
        return organoId;
    }

    public void setOrganoId(final String organoId) {
        this.organoId = organoId;
    }

    public String getPasarelaId() {
        return pasarelaId;
    }

    public void setPasarelaId(final String pasarelaId) {
        this.pasarelaId = pasarelaId;
    }

    public String getTasaId() {
        return tasaId;
    }

    public void setTasaId(final String tasaId) {
        this.tasaId = tasaId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getUrlCallbackOrigen() {
        return urlCallbackOrigen;
    }

    public void setUrlCallbackOrigen(final String urlCallbackOrigen) {
        this.urlCallbackOrigen = urlCallbackOrigen;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public void setUsuarioConfirmacion(final String usuarioConfirmacion) {
        this.usuarioConfirmacion = usuarioConfirmacion;
    }

    public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

    /**
     * Método de acceso a codigoErrorPasarela.
     *
     * @return codigoErrorPasarela
     */
    public String getCodigoErrorPasarela() {
        return codigoErrorPasarela;
    }

    /**
     * Método para establecer codigoErrorPasarela.
     *
     * @param codigoErrorPasarela
     *            codigoErrorPasarela a establecer
     */
    public void setCodigoErrorPasarela(String codigoErrorPasarela) {
        this.codigoErrorPasarela = codigoErrorPasarela;
    }

    /**
     * Método de acceso a mensajeErrorPasarela.
     *
     * @return mensajeErrorPasarela
     */
    public String getMensajeErrorPasarela() {
        return mensajeErrorPasarela;
    }

    /**
     * Método para establecer mensajeErrorPasarela.
     *
     * @param mensajeErrorPasarela
     *            mensajeErrorPasarela a establecer
     */
    public void setMensajeErrorPasarela(String mensajeErrorPasarela) {
        this.mensajeErrorPasarela = mensajeErrorPasarela;
    }

    /**
     * Método de acceso a metodosPago.
     *
     * @return metodosPago
     */
    public String getMetodosPago() {
        return metodosPago;
    }

    /**
     * Método para establecer metodosPago.
     *
     * @param metodosPago
     *            metodosPago a establecer
     */
    public void setMetodosPago(final String metodosPago) {
        this.metodosPago = metodosPago;
    }


    public DatosSesionPago toModel() {
        final DatosSesionPago pago = new DatosSesionPago();
        final DatosPago datosPago = new DatosPago();
        pago.setCodigo(codigo);

        datosPago.setIdentificador(identificador);
        datosPago.setAplicacionId(aplicacionId);
        datosPago.setEntidadId(entidadId);
        datosPago.setOrganismoId(organoId);
        datosPago.setDetallePago(detallePago);
        datosPago.setIdioma(TypeIdioma.fromString(idioma));
        datosPago.setSujetoPasivoNif(sujetoPasivoNif);
        datosPago.setSujetoPasivoNombre(sujetoPasivoNombre);
        datosPago.setConcepto(concepto);
        datosPago.setTasaId(tasaId);
        datosPago.setImporte(importe);
        datosPago.setModelo(modelo);
        datosPago.setMetodosPago(metodosPago);
        datosPago.setIdTramite(idTramite);
        datosPago.setVersionTramite(versionTramite);

        pago.setDatosPago(datosPago);
        pago.setFechaCreacion(fechaCreacion);
        pago.setEntidadId(entidadId);
        pago.setPasarelaId(pasarelaId);
        pago.setLocalizador(localizador);
        pago.setEstado(TypeEstadoPago.fromString(estado));
        pago.setCodigoErrorPasarela(codigoErrorPasarela);
        pago.setMensajeErrorPasarela(mensajeErrorPasarela);
        pago.setFechaPago(fechaPago);
        pago.setUrlCallbackOrigen(urlCallbackOrigen);
        pago.setTokenAcceso(token);
        pago.setUsuarioConfirmacion(usuarioConfirmacion);

        return pago;
    }

}