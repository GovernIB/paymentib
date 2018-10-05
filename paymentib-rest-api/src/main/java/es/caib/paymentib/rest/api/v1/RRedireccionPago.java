package es.caib.paymentib.rest.api.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Redirección pago.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RedireccionPago", description = "RedireccionPago")
public class RRedireccionPago {

    /** Identificador pago. */
    @ApiModelProperty(value = "identificador")
    private String identificador;

    /** Url pago. */
    @ApiModelProperty(value = "urlPago")
    private String urlPago;

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a urlPago.
     *
     * @return urlPago
     */
    public String getUrlPago() {
        return urlPago;
    }

    /**
     * Método para establecer urlPago.
     *
     * @param urlPago
     *            urlPago a establecer
     */
    public void setUrlPago(String urlPago) {
        this.urlPago = urlPago;
    }

}
