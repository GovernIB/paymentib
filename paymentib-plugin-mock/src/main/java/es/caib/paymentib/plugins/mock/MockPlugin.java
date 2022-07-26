package es.caib.paymentib.plugins.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;
import es.caib.paymentib.plugins.api.PasarelaPagoException;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * Mock plugin.
 *
 * @author Indra
 *
 */
public class MockPlugin implements IPasarelaPagoPlugin {

	@Override
	public String getPasarelaId() {
		return "MOCK";
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma, final String metodosPago) throws PasarelaPagoException {
		final List<EntidadPago> res = new ArrayList<>();
		EntidadPago ep = null;
		for (int i = 1; i <= 3; i++) {
			ep = new EntidadPago();
			ep.setCodigo("EP" + i);
			ep.setDescripcion("Entidad pago " + i);
			ep.setLogo("/paymentibfront/imgs/entidad" + i + ".png");
			res.add(ep);
		}
		return res;
	}

	@Override
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final DatosPago datosPago, final String entidadPagoId,
			final String urlCallback) throws PasarelaPagoException {
		final UrlRedireccionPasarelaPago res = new UrlRedireccionPasarelaPago();
		res.setLocalizador("LOC-" + System.currentTimeMillis());

		// Retornamos directamente al retorno del pago
		res.setUrl(urlCallback);
		return res;
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago, final String localizador,
			final Map<String, String[]> parametrosRetorno) throws PasarelaPagoException {
		final EstadoPago res = new EstadoPago();
		res.setEstado(TypeEstadoPago.PAGADO);
		res.setFechaPago(new Date());
		return res;
	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador)
			throws PasarelaPagoException {
		final EstadoPago res = new EstadoPago();
		res.setEstado(TypeEstadoPago.PAGADO);
		res.setFechaPago(new Date());
		return res;
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago, final String localizador)
			throws PasarelaPagoException {
		return null;
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
		return "CARTAPAGO".getBytes();
	}

}
