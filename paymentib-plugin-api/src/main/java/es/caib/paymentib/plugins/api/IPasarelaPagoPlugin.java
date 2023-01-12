package es.caib.paymentib.plugins.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface pasarela pago.
 *
 * @author Indra
 *
 */
public interface IPasarelaPagoPlugin extends IPlugin {

	/** Prefix. */
	public static final String PAGO_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES + "paymentib.";

	/**
	 * Devuelve identificador pasarela pagos.
	 *
	 * @return identificador pasarela pagos
	 */
	String getPasarelaId();

	/**
	 * Obtiene entidades de pago gestionadas por la pasarela.
	 *
	 * @param idioma
	 *            idioma
	 * @return entidades
	 */
	List<EntidadPago> obtenerEntidadesPagoElectronico(TypeIdioma idioma, String metodosPago) throws PasarelaPagoException;

	/**
	 * Inicia pago a través de la pasarela de pago.
	 *
	 * @param entidadPagoId
	 *            Código entidad de pago a través de la que se realiza el pago.
	 * @param datosPago
	 *            Datos pago
	 * @param urlCallback
	 *            Url callback tras finalizar pago electrónico
	 * @return Redirección pasarela de pagos
	 */
	UrlRedireccionPasarelaPago iniciarPagoElectronico(DatosPago datosPago, String entidadPagoId, String urlCallback)
			throws PasarelaPagoException;

	/**
	 * Verifica estado pago tras retorno de la pasarela (urlCallback).
	 *
	 * @param datosPago
	 *            Datos pago
	 * @param localizador
	 *            Localizador pago en la pasarela
	 * @param parametrosRetorno
	 *            Parámetros retorno devueltos por la pasarela de pagos.
	 * @return estado pago
	 */
	EstadoPago verificarRetornoPagoElectronico(DatosPago datosPago, String localizador,
			Map<String, String[]> parametrosRetorno) throws PasarelaPagoException;

	/**
	 * Verifica estado pago contra pasarela de pago.
	 *
	 * @param datosPago
	 *            Datos pago
	 * @param localizador
	 *            Localizador pago en la pasarela
	 * @return estado pago
	 */
	EstadoPago verificarPagoElectronico(DatosPago datosPago, String localizador) throws PasarelaPagoException;

	/**
	 * Obtiene justificante de pago
	 *
	 * @param datosPago
	 *            Datos pago
	 * @param localizador
	 *            Localizador pago en la pasarela
	 * @return Justificante de pago (nulo si la pasarela no genera justificante).
	 */
	byte[] obtenerJustificantePagoElectronico(DatosPago datosPago, String localizador, Date fechaCreacion) throws PasarelaPagoException;

	/**
	 * Obtiene importe tasa.
	 *
	 * @param idTasa
	 *            id tasa
	 * @return importe (en cents)
	 * @throws PasarelaPagoException
	 */
	int consultaTasa(String idTasa) throws PasarelaPagoException;

	/**
	 * Indica si permite pago presencial.
	 *
	 * @return true si permite pago presencial.
	 */
	boolean permitePagoPresencial();

	/**
	 * Obtiene carta de pago presencial.
	 *
	 * @param datosPago
	 *            Datos pago
	 * @return carta de pago presencial
	 */
	byte[] obtenerCartaPagoPresencial(DatosPago datosPago) throws PasarelaPagoException;

}
