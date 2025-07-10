package es.caib.paymentib.plugins.mock;

import java.io.FileInputStream;
import java.io.IOException;
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
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma, final String metodosPago) throws PasarelaPagoException {
		final List<EntidadPago> res = new ArrayList<>();
		EntidadPago ep;
		// Pagado
		ep = new EntidadPago();
		ep.setCodigo("MKP");
		ep.setDescripcion("MOCK - Pagado");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);
		// No pagado
		ep = new EntidadPago();
		ep.setCodigo("MKN");
		ep.setDescripcion("MOCK - No pagado");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);
		// Desconocido
		ep = new EntidadPago();
		ep.setCodigo("MKX");
		ep.setDescripcion("MOCK - Desconocido");
		ep.setLogo("/paymentibfront/imgs/mock.png");
		res.add(ep);

		return res;
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
		return getEstadoPagoMock(datosPago, entidadPagoId);
	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId)
			throws PasarelaPagoException {
		return getEstadoPagoMock(datosPago, entidadPagoId);
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
	 * @param datosPago
	 * @return
	 * @throws PasarelaPagoException
	 */
	private EstadoPago getEstadoPagoMock(DatosPago datosPago, String entidadPagoId) throws PasarelaPagoException {
		final EstadoPago res = new EstadoPago();
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

}
