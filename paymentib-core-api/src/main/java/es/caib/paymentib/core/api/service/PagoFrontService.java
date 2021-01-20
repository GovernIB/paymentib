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
	 *                                 Pasarela de pago a utilizar
	 * @param datosPago
	 *                                 Datos del pago
	 * @param urlCallbackAppOrigen
	 *                                 Url callback aplicación origen
	 * @return Datos sesión pago
	 */
	DatosSesionPago crearPagoElectronico(String pasarelaId, DatosPago datosPago, String urlCallbackAppOrigen);

	/**
	 * Recupera pago electrónico por token.
	 *
	 * @param tokenSesion
	 *                        Token
	 * @return pago electrónico
	 */
	DatosSesionPago recuperarPagoElectronicoByToken(String tokenSesion);

	/**
	 * Recupera pago electrónico por identificador.
	 *
	 * @param identificador
	 *                          identificador
	 * @return pago electrónico
	 */
	DatosSesionPago recuperarPagoElectronico(String identificador);

	/**
	 * Inicia pago electrónico contra pasarela.
	 *
	 * @param identificador
	 *                                 identificador
	 * @param entidadPagoId
	 *                                 entidad
	 * @param urlCallbackCompPagos
	 *                                 url callback
	 * @return url redirección pasarela
	 */
	UrlRedireccionPasarelaPago redirigirPasarelaPago(String identificador, String entidadPagoId,
			String urlCallbackCompPagos);

	/**
	 * Obtiene entidades pago electrónico.
	 *
	 * @param identificador
	 *                          identificador pago
	 * @return entidades pago electrónico
	 */
	List<EntidadPago> obtenerEntidadesPagoElectronico(String identificador);

	/**
	 * Verifica pago electrónico a partir info retornada por pasarela.
	 *
	 * @param identificador
	 *                              identificdor pago
	 * @param parametrosRetorno
	 *                              info suministrada por pasarela
	 * @return Estado pago
	 */
	EstadoPago verificarRetornoPagoElectronico(String identificador, Map<String, String[]> parametrosRetorno);

	/**
	 * Verifica estado pago electrónico.
	 *
	 * @param identificador
	 *                          identificador pago
	 * @return Estado pago
	 */
	EstadoPago verificarPagoElectronico(String identificador);

	/**
	 * Obtiene justificante pago electrónico.
	 *
	 * @param identificador
	 *                          identificador
	 * @return justificante
	 */
	byte[] obtenerJustificantePagoElectronico(String identificador);

	/**
	 * Consulta tasa a pasarela.
	 *
	 * @param pasarelaId
	 *                       id pasarela
	 * @param idTasa
	 *                       id tasa
	 * @return importe tasa (cents)
	 */
	int consultaTasa(String pasarelaId, String idTasa);

	/**
	 * Obtiene carta de pago presencial.
	 *
	 * @param pasarelaId
	 *                       id pasarela
	 * @param datosPago
	 *                       datos pago
	 * @return carta de pago presencial
	 */
	byte[] obtenerCartaPagoPresencial(String pasarelaId, DatosPago datosPago);

	/**
	 * Indica si pasarela permite pago presencial.
	 *
	 * @param pasarelaId
	 *                       id pasarela
	 * @return true si permite
	 */
	boolean permitePagoPresencial(String pasarelaId);
}
