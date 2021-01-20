package es.caib.paymentib.plugins.tpv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;
import es.caib.paymentib.plugins.api.PasarelaPagoException;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * TPV plugin.
 *
 * @author Indra
 *
 */
public class TpvPlugin extends AbstractPluginProperties implements IPasarelaPagoPlugin {

	/**
	 *
	 * Constructor.
	 *
	 * @param prefijoPropiedades
	 *                               prefijo
	 * @param properties
	 *                               propiedades
	 */
	public TpvPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String getPasarelaId() {
		return "TPV";
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma) throws PasarelaPagoException {
		final List<EntidadPago> res = new ArrayList<>();
		final EntidadPago ep = new EntidadPago();
		ep.setCodigo("TPV");
		ep.setDescripcion("TPV");
		ep.setLogo("/paymentibfront/imgs/tpv.png");
		res.add(ep);
		return res;
	}

	@Override
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final DatosPago datosPago, final String entidadPagoId,
			final String urlCallback) throws PasarelaPagoException {

		// Obtiene datos organismo TPV
		final MerchantData merchantData = obtenerMerchantData(datosPago);
		// Genera url redireccion pago
		final UrlRedireccionPasarelaPago res = TpvUtil.generarUrlPago(datosPago, merchantData, urlCallback);

		return res;
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago, final String localizador,
			final Map<String, String[]> parametrosRetorno) throws PasarelaPagoException {

		// Obtiene datos organismo TPV
		final MerchantData merchantData = obtenerMerchantData(datosPago);

		// Obtiene datos retornados por pasarela
		final String merchantParameters = getParametroRetorno(parametrosRetorno, "Ds_MerchantParameters");
		final String signatureVersion = getParametroRetorno(parametrosRetorno, "Ds_SignatureVersion");
		final String signature = getParametroRetorno(parametrosRetorno, "Ds_Signature");

		// Verifica confirmacion pago
		final ConfirmacionPago confPago = TpvUtil.verificarConfirmacionPago(merchantData, merchantParameters,
				signatureVersion, signature);

		// Devuelve estado pago
		final EstadoPago res = new EstadoPago();
		res.setEstado(confPago.getEstado());
		res.setFechaPago(confPago.getFecha());
		return res;

	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador)
			throws PasarelaPagoException {
		// TPV no permite consultar estado pago por localizador
		final EstadoPago res = new EstadoPago();
		res.setEstado(TypeEstadoPago.DESCONOCIDO);
		res.setFechaPago(new Date());
		return res;
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago, final String localizador)
			throws PasarelaPagoException {
		// TPV no proporciona justificante pago
		// Devolvemos nulo para generar justificante genérico
		return null;
	}

	@Override
	public int consultaTasa(final String idTasa) throws PasarelaPagoException {
		throw new PasarelaPagoException("Plugin no soporta funcionalidad de consulta de tasas");
	}

	@Override
	public boolean permitePagoPresencial() {
		return false;
	}

	@Override
	public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago) throws PasarelaPagoException {
		throw new PasarelaPagoException("Plugin no soporta funcionalidad de pago presencial");
	}

	/**
	 * Recupera datos organismo.
	 *
	 * @param datosPago
	 *                      datosPago
	 * @return datos organismo
	 * @throws PasarelaPagoException
	 */
	private MerchantData obtenerMerchantData(final DatosPago datosPago) throws PasarelaPagoException {

		final String entidadId = datosPago.getOrganismoId();
		if (StringUtils.isBlank(entidadId)) {
			throw new PasarelaPagoException("No se ha establecido organismo TPV");
		}

		final MerchantData md = new MerchantData();
		md.setMerchantName(this.getProperty(entidadId + ".merchantName"));
		md.setMerchantCode(this.getProperty(entidadId + ".merchantCode"));
		md.setMerchantTerminal(this.getProperty(entidadId + ".merchantTerminal"));
		md.setMerchantPassword(this.getProperty(entidadId + ".merchantPassword"));

		if (StringUtils.isBlank(md.getMerchantName()) || StringUtils.isBlank(md.getMerchantCode())
				|| StringUtils.isBlank(md.getMerchantTerminal()) || StringUtils.isBlank(md.getMerchantPassword())) {
			throw new PasarelaPagoException("Faltan datos configuracion para organismo TPV: " + entidadId);
		}

		md.setMerchantUrlInicio(this.getProperty("urlTPV"));
		md.setMerchantUrlNotificacion(
				this.getProperty("urlNotificacion") + "/" + datosPago.getIdentificador() + ".html");

		return md;

	}

	/**
	 * Obtiene nombre parametro.
	 *
	 * @param parametrosRetorno
	 *                              parametros
	 * @param paramName
	 *                              nombre parametro
	 * @return valor parametro
	 */
	private String getParametroRetorno(final Map<String, String[]> parametrosRetorno, final String paramName) {
		String res = null;
		if (parametrosRetorno.containsKey(paramName)) {
			res = parametrosRetorno.get(paramName)[0];
		}
		return res;
	}

}
