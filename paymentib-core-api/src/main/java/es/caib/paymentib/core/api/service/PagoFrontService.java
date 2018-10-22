package es.caib.paymentib.core.api.service;

import java.util.List;
import java.util.Map;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * Acceso a funciones sesion de pagos (para proceso pago).
 *
 * @author Indra
 *
 */
public interface PagoFrontService {

    /**
     * Obtiene url frontal.
     *
     * @return obtiene url frontal
     */
    String obtenerUrlFrontal();

    /**
     * Crea pago electrónico.
     *
     * @param pasarelaId
     *            Pasarela de pago a utilizar
     * @param datosPago
     *            Datos del pago
     * @param urlCallbackAppOrigen
     *            Url callback aplicación origen
     * @return Datos sesión pago
     */
    DatosSesionPago crearPagoElectronico(String pasarelaId, DatosPago datosPago,
            String urlCallbackAppOrigen);

    DatosSesionPago recuperarPagoElectronico(String tokenSesion);

    UrlRedireccionPasarelaPago iniciarPagoElectronico(String identificador,
            String entidadPagoId, String urlCallbackCompPagos);

    List<EntidadPago> obtenerEntidadesPagoElectronico(String identificador);

    EstadoPago verificarRetornoPagoElectronico(String identificador,
            Map<String, String[]> parametrosRetorno);

    EstadoPago verificarPagoElectronico(String identificador);

    byte[] obtenerJustificantePagoElectronico(String identificador);

    int consultaTasa(String pasarelaId, String idTasa);

    byte[] obtenerCartaPagoPresencial(String pasarelaId, DatosPago datosPago);
}
