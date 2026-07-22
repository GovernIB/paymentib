package es.caib.paymentib.plugins.tpv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import es.caib.paymentib.plugins.api.*;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

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
	public List<EntidadPago> obtenerEntidadesPagoElectronico(TypeIdioma idioma) {
		return recuperarEntidadPagos();
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma, final String metodosPago) throws PasarelaPagoException {
		// No realizamos ningun filtro, solo hay 1
		return recuperarEntidadPagos();
	}

	@Override
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final DatosPago datosPago, final String entidadPagoId,
			final String urlCallback) throws PasarelaPagoException {

		// No esta permitido multiplicador > 1
		if (datosPago.getMultiplicador() > 1) {
			throw new PasarelaPagoException("TPV no permite multiplicador > 1");
		}

		// Obtiene datos organismo TPV
		final MerchantData merchantData = obtenerMerchantData(datosPago);
		// Genera url redireccion pago
		final UrlRedireccionPasarelaPago res = TpvUtil.generarUrlPago(datosPago, merchantData, urlCallback);

		return res;
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId,
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
		res.setLocalizador(localizador);
		res.setMetodoPago(entidadPagoId);
		res.setEstado(confPago.getEstado());
		res.setFechaPago(confPago.getFecha());
		return res;

	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId)
			throws PasarelaPagoException {
		// TPV no permite consultar estado pago por localizador
		final EstadoPago res = new EstadoPago();
		res.setEstado(TypeEstadoPago.DESCONOCIDO);
		res.setFechaPago(new Date());
		return res;
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago, final String localizador, final Date fechaCreacion)
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

	@Override
	public TypeModoValidacion obtenerModoValidacion() {
		return TypeModoValidacion.CONFIRMACION_MANUAL;
	}

	@Override
	public TypeValidacionPagoExterno verificarPagoExterno(DatosPago datosPago, String localizador, Date fechaPago) throws PasarelaPagoException {
		// NO PERMITE VERIFICAR PAGO EXTERNO POR LOCALIZADOR
		return TypeValidacionPagoExterno.NO_VERIFICADO;
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
		md.setMerchantName(this.getProperty("organismo." + entidadId + ".merchantName"));
		md.setMerchantCode(this.getProperty("organismo." + entidadId + ".merchantCode"));
		md.setMerchantTerminal(this.getProperty("organismo." + entidadId + ".merchantTerminal"));
		md.setMerchantPassword(this.getProperty("organismo." + entidadId + ".merchantPassword"));

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


	/**
	 * Recupera entidades pago.
	 * @return entidades pago
	 */
	private static List<EntidadPago> recuperarEntidadPagos() {
		final List<EntidadPago> res = new ArrayList<>();
		final EntidadPago ep = new EntidadPago();
		ep.setCodigo("TPV");
		ep.setTitulo("TPV");
		ep.setDescripcion("TPV");
		ep.setLogo("/paymentibfront/imgs/tpv.png");
		res.add(ep);
		return res;
	}

}
