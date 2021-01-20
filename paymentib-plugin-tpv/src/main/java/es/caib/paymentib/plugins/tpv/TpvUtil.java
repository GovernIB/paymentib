package es.caib.paymentib.plugins.tpv;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.PasarelaPagoException;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * Util TPV.
 *
 * @author Indra
 *
 */
public class TpvUtil {

	/** Moneda: Euros. */
	private static final String MERCHANT_CURRENCY = "978";

	/** Moneda: Euros. */
	private static final String MERCHANT_TRANSACTION_TYPE = "0";

	/**
	 * Generar url pago.
	 *
	 * @param datosPago
	 *                         Datos pago
	 * @param merchantData
	 *                         Datos organo TPV
	 * @param urlCallback
	 *                         Url callback
	 * @return url pago
	 *
	 * @throws PasarelaPagoException
	 */
	public static UrlRedireccionPasarelaPago generarUrlPago(final DatosPago datosPago, final MerchantData merchantData,
			final String urlCallback) throws PasarelaPagoException {

		try {

			// Para TPV usamos como localizador el mismo id de pago
			final String localizador = datosPago.getIdentificador();

			final ApiMacSha256 api = new ApiMacSha256();
			api.setParameter("Ds_Merchant_Currency", MERCHANT_CURRENCY);
			api.setParameter("Ds_Merchant_TransactionType", MERCHANT_TRANSACTION_TYPE);
			api.setParameter("Ds_Merchant_MerchantName", merchantData.getMerchantName());
			api.setParameter("Ds_Merchant_MerchantCode", merchantData.getMerchantCode());
			api.setParameter("Ds_Merchant_Terminal", merchantData.getMerchantTerminal());
			api.setParameter("Ds_Merchant_ConsumerLanguage", getMerchantLang(datosPago.getIdioma().toString()));
			api.setParameter("Ds_Merchant_ProductDescription", StringUtils.substring(datosPago.getConcepto(), 0, 100));
			api.setParameter("Ds_Merchant_Titular", StringUtils.substring(datosPago.getSujetoPasivoNombre(), 0, 60));
			api.setParameter("Ds_Merchant_MerchantData", datosPago.getIdentificador());
			api.setParameter("Ds_Merchant_Order", localizador);
			api.setParameter("Ds_Merchant_MerchantURL", merchantData.getMerchantUrlNotificacion());
			api.setParameter("Ds_Merchant_UrlOK", urlCallback);
			api.setParameter("Ds_Merchant_UrlKO", urlCallback);
			api.setParameter("Ds_Merchant_Amount", Integer.toString(datosPago.getImporte()));

			final String merchantParameters = api.createMerchantParameters();
			final String merchantSignature = api.createMerchantSignature(merchantData.getMerchantPassword());

			final UrlRedireccionPasarelaPago urlPago = new UrlRedireccionPasarelaPago();
			urlPago.setUrl(merchantData.getMerchantUrlInicio());
			urlPago.setLocalizador(localizador);

			urlPago.getParametersPost().put("Ds_SignatureVersion", "HMAC_SHA256_V1");
			urlPago.getParametersPost().put("Ds_MerchantParameters", merchantParameters);
			urlPago.getParametersPost().put("Ds_Signature", merchantSignature);

			return urlPago;
		} catch (final Exception ex) {
			throw new PasarelaPagoException("Error al generar petición TPV: " + ex.getMessage(), ex);
		}

	}

	/**
	 * Verifica confirmación pago.
	 *
	 * @param merchantData
	 *                               Datos organo TPV
	 * @param merchantParameters
	 *                               merchantParameters
	 * @param signatureVersion
	 *                               signatureVersion
	 * @param signature
	 *                               signature
	 * @return confirmación pago
	 * @throws PasarelaPagoException
	 */
	public static ConfirmacionPago verificarConfirmacionPago(final MerchantData merchantData,
			final String merchantParameters, final String signatureVersion, final String signature)
			throws PasarelaPagoException {

		try {
			final ConfirmacionPago res = new ConfirmacionPago();
			res.setEstado(TypeEstadoPago.DESCONOCIDO);

			if (StringUtils.isNotBlank(merchantParameters) && StringUtils.isNotBlank(signatureVersion)
					&& StringUtils.isNotBlank(signature)) {

				// Interpretamos respuesta
				final ApiMacSha256 api = new ApiMacSha256();
				api.decodeMerchantParameters(merchantParameters);

				// Verifica firma
				final String firma = api.createMerchantSignatureNotif(merchantData.getMerchantPassword(),
						merchantParameters);
				if (!signature.equals(firma)) {
					throw new PasarelaPagoException("No coincide firma");
				}

				// Verifica si esta pagado
				if (api.getParameter("Ds_Response") != null && api.getParameter("Ds_Response").startsWith("00")) {
					res.setEstado(TypeEstadoPago.PAGADO);
				} else {
					res.setEstado(TypeEstadoPago.NO_PAGADO);
				}

				// Fecha confirmacion pago
				final String dsDate = api.getParameter("Ds_Date");
				final String dsHour = api.getParameter("Ds_Hour");
				res.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dsDate + " " + dsHour));

				// TODO TPV NECESARIO AUDITAR MAS DATOS?
				// api.getParameter("Ds_AuthorisationCode")

			}

			return res;

		} catch (final Exception ex) {
			throw new PasarelaPagoException("Error al generar petición TPV: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Devuelve código idioma TPV.
	 *
	 * @param idioma
	 *                   idioma
	 * @return código idioma TPV.
	 */
	private static String getMerchantLang(final String idioma) {
		String lang = "001";
		if ("ca".equals(idioma)) {
			lang = "003";
		} else if ("en".equals(idioma)) {
			lang = "002";
		}
		return lang;
	}

}
