package es.caib.paymentib.plugins.api;
/**
 * Estado pago.
 *
 * @author Indra
 *
 */

import java.util.Date;

public class EstadoPago {

    /** Estado. */
    private TypeEstadoPago estado;

    /** Fecha pago. */
    private Date fechaPago;

    /** Identificador pago pasarela en caso de estar pagado. */
    private String localizador;

    /** Método pago (depende de la pasarela). */
    private String metodoPago;

    /** Codigo error pasarela. */
    private String codigoErrorPasarela;

    /** Mensaje error pasarela. */
    private String mensajeErrorPasarela;

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
    public void setEstado(TypeEstadoPago estado) {
        this.estado = estado;
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
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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
     * Método de acceso a identificadorPagoPasarela.
     * 
     * @return identificadorPagoPasarela
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Método para establecer identificadorPagoPasarela.
     * 
     * @param identificadorPagoPasarela
     *            identificadorPagoPasarela a establecer
     */
    public void setLocalizador(String identificadorPagoPasarela) {
        this.localizador = identificadorPagoPasarela;
    }

    /**
     * Método de acceso a metodoPago.
     *
     * @return metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * Método para establecer metodoPago.
     *
     * @param metodoPago
     *            metodoPago a establecer
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
