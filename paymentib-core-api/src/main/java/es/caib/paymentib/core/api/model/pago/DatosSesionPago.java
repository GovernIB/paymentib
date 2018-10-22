package es.caib.paymentib.core.api.model.pago;

import java.util.Date;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;

/**
 * Datos sesion pago.
 *
 * @author Indra
 *
 */
public class DatosSesionPago {

    /** codigo */
    private long codigo;

    /** Datos pago. */
    private DatosPago datosPago;

    /** Fecha creación. */
    private Date fechaCreacion;

    /** Id pasarela pagos. */
    private String pasarelaId;

    /** Localizador pago en pasarela. */
    private String localizador;

    /** Estado pago. */
    private TypeEstadoPago estado;

    /** Código error pasarela. */
    private String codigoErrorPasarela;

    /** Mensaje error pasarela. */
    private String mensajeErrorPasarela;

    /** Fecha pago. */
    private Date fechaPago;

    /** Token acceso. */
    private String tokenAcceso;

    /** Url callback aplicación origen. */
    private String urlCallbackOrigen;

    /**
     * usuario confirmacion.
     */
    private String usuarioConfirmacion;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    /**
     * Método de acceso a pasarelaId.
     *
     * @return pasarelaId
     */
    public String getPasarelaId() {
        return pasarelaId;
    }

    /**
     * Método para establecer pasarelaId.
     *
     * @param pasarelaId
     *            pasarelaId a establecer
     */
    public void setPasarelaId(final String pasarelaId) {
        this.pasarelaId = pasarelaId;
    }

    /**
     * Método de acceso a datosPago.
     *
     * @return datosPago
     */
    public DatosPago getDatosPago() {
        return datosPago;
    }

    /**
     * Método para establecer datosPago.
     *
     * @param datosPago
     *            datosPago a establecer
     */
    public void setDatosPago(final DatosPago datosPago) {
        this.datosPago = datosPago;
    }

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
     *            estado a establecer
     */
    public void setEstado(final TypeEstadoPago estado) {
        this.estado = estado;
    }

    /**
     * Método de acceso a localizador.
     *
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Método para establecer localizador.
     *
     * @param localizador
     *            localizador a establecer
     */
    public void setLocalizador(final String localizador) {
        this.localizador = localizador;
    }

    /**
     * Método de acceso a urlCallbackOrigen.
     *
     * @return urlCallbackOrigen
     */
    public String getUrlCallbackOrigen() {
        return urlCallbackOrigen;
    }

    /**
     * Método para establecer urlCallbackOrigen.
     *
     * @param urlCallbackOrigen
     *            urlCallbackOrigen a establecer
     */
    public void setUrlCallbackOrigen(final String urlCallbackOrigen) {
        this.urlCallbackOrigen = urlCallbackOrigen;
    }

    /**
     * Método de acceso a fechaCreacion.
     *
     * @return fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Método para establecer fechaCreacion.
     *
     * @param fechaCreacion
     *            fechaCreacion a establecer
     */
    public void setFechaCreacion(final Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Método de acceso a fechaPago.
     *
     * @return fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * Método para establecer fechaPago.
     *
     * @param fechaPago
     *            fechaPago a establecer
     */
    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * Método de acceso a tokenAcceso.
     *
     * @return tokenAcceso
     */
    public String getTokenAcceso() {
        return tokenAcceso;
    }

    /**
     * Método para establecer tokenAcceso.
     *
     * @param tokenAcceso
     *            tokenAcceso a establecer
     */
    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public void setUsuarioConfirmacion(final String usuarioConfirmacion) {
        this.usuarioConfirmacion = usuarioConfirmacion;
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

}
