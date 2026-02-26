package es.caib.paymentib.plugins.mock;

import java.util.*;

import es.caib.paymentib.plugins.api.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock plugin.
 *
 * @author Indra
 *
 */
public class MockPlugin implements IPasarelaPagoPlugin {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(MockPlugin.class);

	@Override
	public String getPasarelaId() {
		return "MOCK";
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(TypeIdioma idioma) {
		return recuperarListaEntidadesPago();
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma, final String metodosPago) throws PasarelaPagoException {
		// Para Mock no realizamos ningún filtro
		return recuperarListaEntidadesPago();
	}

	@Override
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final DatosPago datosPago, final String entidadPagoId,
			final String urlCallback) throws PasarelaPagoException {
		// Retornamos directamente al retorno del pago
		final UrlRedireccionPasarelaPago res = new UrlRedireccionPasarelaPago();
		res.setLocalizador("LOC-" + System.currentTimeMillis());
		res.setUrl(urlCallback);
		return res;
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId,
			final Map<String, String[]> parametrosRetorno) throws PasarelaPagoException {
		return getEstadoPagoMock(localizador, datosPago, entidadPagoId);
	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId)
			throws PasarelaPagoException {
		return getEstadoPagoMock(localizador, datosPago, entidadPagoId);
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago, final String localizador, final Date fechaCreacion)
			throws PasarelaPagoException {
		return getMockPdf();
	}

	@Override
	public int consultaTasa(final String idTasa) throws PasarelaPagoException {
		return 100;
	}

	@Override
	public boolean permitePagoPresencial() {
		return true;
	}

	@Override
	public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago) throws PasarelaPagoException {
		return getMockPdf();
	}

	@Override
	public TypeModoValidacion obtenerModoValidacion() {
		return TypeModoValidacion.CONFIRMACION_MANUAL;
	}

	@Override
	public TypeValidacionPagoExterno verificarPagoExterno(DatosPago datosPago, String localizador, Date fechaPago) throws PasarelaPagoException {
		// Mockeado (verifica fecha sea la de fcAyer)
		boolean result = false;
		Calendar fcAyer = Calendar.getInstance();
		fcAyer.add(Calendar.DAY_OF_MONTH, -1);
		if (fechaPago != null) {
			Calendar fcPago = Calendar.getInstance();
			fcPago.setTime(fechaPago);
			result = (fcAyer.get(Calendar.YEAR) == fcPago.get(Calendar.YEAR)
					&& fcAyer.get(Calendar.DAY_OF_YEAR) == fcPago.get(Calendar.DAY_OF_YEAR));
		}
		return result? TypeValidacionPagoExterno.VERIFICADO : TypeValidacionPagoExterno.FECHA_NO_COINCIDE;
	}

	/**
	 * Obtiene pago-mock.pdf
	 * @return El contenido del PDF pago-mock.pdf
	 * @throws PasarelaPagoException
	 */
	private byte[] getMockPdf() throws PasarelaPagoException {
		// Obtiene pago-mock.pdf
		try {
			return IOUtils.toByteArray(getClass().getResourceAsStream("/pago-mock.pdf"));
		} catch (Exception e) {
			throw new PasarelaPagoException("Error al obtener pago-mock.pdf", e);
		}
	}

	/**
	 * Obtiene el estado del pago simulado segun entidad seleccionada.
	 *
	 * @param localizador
	 * @param datosPago
	 * @return
	 * @throws PasarelaPagoException
	 */
	private EstadoPago getEstadoPagoMock(String localizador, DatosPago datosPago, String entidadPagoId) throws PasarelaPagoException {
		final EstadoPago res = new EstadoPago();
		res.setLocalizador(localizador);
		res.setMetodoPago(entidadPagoId);
		switch (entidadPagoId) {
			case "MKN":
				res.setEstado(TypeEstadoPago.NO_PAGADO);
				break;
			case "MKP":
				res.setEstado(TypeEstadoPago.PAGADO);
				res.setFechaPago(new Date());
				break;
			case "MKX":
				res.setEstado(TypeEstadoPago.DESCONOCIDO);
				break;
			default:
				throw new PasarelaPagoException("Entidad de pago no reconocida: " + datosPago.getEntidadId());
		}
		log.info("ESTADO PAGO MOCK: " + datosPago.getEntidadId() + " -> " + res.getEstado() );
		return res;
	}

	/**
	 * Recupera la lista de entidades de pago mock.
	 * @return Lista de entidades de pago mock
	 */
	private List<EntidadPago> recuperarListaEntidadesPago() {
		final List<EntidadPago> res = new ArrayList<>();
		EntidadPago ep;
		// Pagado
		ep = new EntidadPago();
		ep.setCodigo("MKP");
		ep.setTitulo("MOCK - Pagado");
		ep.setDescripcion("MOCK - Pagado");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);
		// No pagado
		ep = new EntidadPago();
		ep.setCodigo("MKN");
		ep.setTitulo("MOCK - No pagado");
		ep.setDescripcion("MOCK - No pagado");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);
		// Desconocido
		ep = new EntidadPago();
		ep.setCodigo("MKX");
		ep.setTitulo("MOCK - Desconocido");
		ep.setDescripcion("MOCK - Desconocido");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);
		// Externo
		ep = new EntidadPago();
		ep.setCodigo(IPasarelaPagoPlugin.ENTIDAD_PAGO_EXTERNO);
		ep.setTitulo("EXTERNO");
		ep.setDescripcion("EXTERNO");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);

		return res;
	}

}
