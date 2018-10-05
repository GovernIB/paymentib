package es.caib.paymentib.rest.api.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos pago y urlCallback.
 *
 * @author Indra
 *
 */
@ApiModel(value = "Datos de inicio pago: datos pago y urlCallback", description = "Datos de inicio pago: datos pago y urlCallback")
public class RDatosInicioPago {

    /** Datos de pago */
    @ApiModelProperty(value = "Datos de pago")
    private RDatosPago datosPago;

    /** urlCallback */
    @ApiModelProperty(value = "urlCallback")
    private String urlCallback;

    public RDatosPago getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(RDatosPago rDatosPago) {
        this.datosPago = rDatosPago;
    }

    public String getUrlCallback() {
        return urlCallback;
    }

    public void setUrlCallback(String urlCallback) {
        this.urlCallback = urlCallback;
    }
}
