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

}
