package es.caib.paymentib.rest.api.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estado pago.
 *
 * @author Indra
 *
 */
@ApiModel(value = "Estado pago", description = "Estado pago")
public class REstadoPago {

    /**
     * Estado: No iniciado("v") / No pagado ("n") / Pagado ("p") / Desconocido
     * ("x")
     */
    @ApiModelProperty(value = "estado")
    private String estado;

    /** Fecha pago en caso de estar pagado (dd/mm/yyyy hh:mi:ss). */
    @ApiModelProperty(value = "fechaPago")
    private String fechaPago;

    /** Identificador pago pasarela en caso de estar pagado. */
    @ApiModelProperty(value = "localizador")
    private String localizador;

    /** Código error pasarela, opcional en caso de estado desconocido. */
    @ApiModelProperty(value = "codigoErrorPasarela")
    private String codigoErrorPasarela;

    /** Mensaje error pasarela, opcional en caso de estado desconocido. */
    @ApiModelProperty(value = "mensajeErrorPasarela")
    private String mensajeErrorPasarela;

    /**
     * Método de acceso a estado.
     *
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     *
     * @param estado
     *            estado a establecer
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Método de acceso a fechaPago.
     *
     * @return fechaPago
     */
    public String getFechaPago() {
        return fechaPago;
    }

    /**
     * Método para establecer fechaPago.
     *
     * @param fechaPago
     *            fechaPago a establecer
     */
    public void setFechaPago(String fechaPago) {
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

}
