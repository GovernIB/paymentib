package es.caib.paymentib.rest.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.rest.api.v1.RDatosInicioPago;
import es.caib.paymentib.rest.api.v1.RDatosPago;
import es.caib.paymentib.rest.api.v1.REstadoPago;
import es.caib.paymentib.rest.api.v1.RFiltroPago;
import es.caib.paymentib.rest.api.v1.RParametrosConsulta;
import es.caib.paymentib.rest.api.v1.RRedireccionPago;
import es.caib.paymentib.core.api.model.pago.FiltroPago;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones API Rest.
 *
 * @author Indra
 */

@RestController
@RequestMapping("/v1")
@Api(value = "v1", produces = "application/json")
public class ApiRestController {

	/** Service. */
	@Autowired
	private PagoFrontService service;

	/**
	 * Inicia pago electrónico.
	 *
	 * @param rPago Datos inicio pago
	 * @return Redirección al pago (identificador pago + url)
	 * @throws RPagoPluginException
	 */
	@ApiOperation(value = "iniciarPagoElectronico", notes = "Inicia pago electrónico.", response = RRedireccionPago.class)
	@RequestMapping(value = "/iniciarPagoElectronico", method = RequestMethod.POST)
	public RRedireccionPago iniciarPagoElectronico(@RequestBody final RDatosInicioPago rPago) {

		// Crea pago electrónico
		final DatosPago datosPago = convertDatosPago(rPago.getDatosPago());

		final DatosSesionPago datosSesionPago = service.crearPagoElectronico(rPago.getDatosPago().getPasarelaId(),
				datosPago, rPago.getUrlCallback());

		// Devolvemos url redirección pago
		final RRedireccionPago res = new RRedireccionPago();
		res.setIdentificador(datosSesionPago.getDatosPago().getIdentificador());
		res.setUrlPago(service.obtenerUrlFrontal() + "/inicioPago/" + datosSesionPago.getTokenAcceso() + ".html");
		return res;
	}

	/**
	 * Verifica estado pago contra pasarela de pago.
	 *
	 * @param identificador identificador pago
	 * @return estado pago
	 */
	@ApiOperation(value = "verificarPagoElectronico", notes = "Verifica estado pago contra pasarela de pago.", response = REstadoPago.class)
	@RequestMapping(value = "/verificarPagoElectronico/{identificador}", method = RequestMethod.GET)
	public REstadoPago verificarPagoElectronico(@PathVariable("identificador") final String identificador) {
		final EstadoPago estado = service.verificarPagoElectronico(identificador);
		final REstadoPago res = new REstadoPago();
		res.setEstado(estado.getEstado().toString());
		res.setFechaPago(formateaFecha(estado.getFechaPago()));
		res.setCodigoErrorPasarela(estado.getCodigoErrorPasarela());
		res.setMensajeErrorPasarela(estado.getMensajeErrorPasarela());
		return res;
	}

	/**
	 * Obtiene justificante de pago
	 *
	 * @param identificador identificador pago
	 * @return Justificante de pago (nulo si la pasarela no genera justificante).
	 */
	@ApiOperation(value = "obtenerJustificantePagoElectronico", notes = "Obtiene justificante de pago electrónico")
	@RequestMapping(value = "/obtenerJustificantePagoElectronico/{identificador}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] obtenerJustificantePagoElectronico(@PathVariable("identificador") final String identificador) {
		return service.obtenerJustificantePagoElectronico(identificador);
	}

	/**
	 * Obtiene importe tasa.
	 *
	 * * @param idPasarela id pasarela
	 *
	 * @param idTasa id tasa
	 * @return importe (en cents)
	 * @throws RPagoPluginException
	 */
	@ApiOperation(value = "consultaTasa", notes = "Obtiene importe tasa.")
	@RequestMapping(value = "/consultaTasa/{idPasarela}/{idTasa}", method = RequestMethod.GET)
	public int consultaTasa(@PathVariable("idPasarela") final String idPasarela,
			@PathVariable("idTasa") final String idTasa) {
		return service.consultaTasa(idPasarela, idTasa);
	}

	/**
	 * Indica si pasarela permite pago presencial.
	 *
	 * @param datosPago Datos pago
	 * @return carta de pago presencial
	 */
	@ApiOperation(value = "permitePagoPresencial", notes = "Indica si pasarela permite pago presencial")
	@RequestMapping(value = "/permitePagoPresencial/{pasarelaId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean permitePagoPresencial(@PathVariable("pasarelaId") final String pasarelaId) {
		return service.permitePagoPresencial(pasarelaId);
	}

	/**
	 * Obtiene carta de pago presencial (PDF).
	 *
	 * @param datosPago Datos pago
	 * @return carta de pago presencial
	 */
	@ApiOperation(value = "obtenerCartaPagoPresencial", notes = "Obtiene carta de pago presencial")
	@RequestMapping(value = "/obtenerCartaPagoPresencial", method = RequestMethod.POST)
	@ResponseBody
	public byte[] obtenerCartaPagoPresencial(@RequestBody final RDatosPago datosPago) {
		final DatosPago dp = convertDatosPago(datosPago);
		return service.obtenerCartaPagoPresencial(datosPago.getPasarelaId(), dp);
	}

	/**
	 * Obtiene datos pago a partif datos inicio pago
	 *
	 * @param rDatosPago
	 * @return
	 */
	private DatosPago convertDatosPago(final RDatosPago rDatosPago) {
		final DatosPago datosPago = new DatosPago();
		datosPago.setAplicacionId(rDatosPago.getAplicacionId());
		datosPago.setEntidadId(rDatosPago.getEntidadId());
		datosPago.setOrganismoId(rDatosPago.getOrganismoId());
		datosPago.setDetallePago(rDatosPago.getDetallePago());
		datosPago.setIdioma(TypeIdioma.fromString(rDatosPago.getIdioma()));
		datosPago.setSujetoPasivoNif(rDatosPago.getSujetoPasivoNif());
		datosPago.setSujetoPasivoNombre(rDatosPago.getSujetoPasivoNombre());
		datosPago.setModelo(rDatosPago.getModelo());
		datosPago.setConcepto(rDatosPago.getConcepto());
		datosPago.setTasaId(rDatosPago.getTasaId());
		datosPago.setImporte(rDatosPago.getImporte());
		datosPago.setMetodosPago(rDatosPago.getMetodosPago());
		return datosPago;
	}

	private String formateaFecha(final Date pFecha) {
		String res = null;
		if (pFecha != null) {
			final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			df.setLenient(false);
			res = df.format(pFecha);
		}
		return res;
	}

	@ApiOperation(value = "datosPago", notes = "Devuelve datos de pago según parámetros")
	@RequestMapping(value = "/datosPago", method = RequestMethod.POST)
	@ResponseBody
	public List<DatosSesionPago> datosPago(@RequestBody(required = false) final RParametrosConsulta parametros) {
		final FiltroPago filtro = convertFiltroPago(parametros.getFiltro());
		return service.obtenerPagos(filtro, parametros.getFechaDesde(), parametros.getFechaHasta(),
				parametros.getNumPag(), parametros.getMaxNumElem());
	}

	private FiltroPago convertFiltroPago(final RFiltroPago rf) {
		FiltroPago f = new FiltroPago();
		if (rf != null) {
			f.setAplicacion(rf.getAplicacion());
			f.setEntidad(rf.getEntidad());
			f.setEstado(rf.getEstado());
			f.setId(rf.getId());
			f.setImporte(rf.getImporte());
			f.setNif(rf.getNif());
			f.setNombre(rf.getNombre());
			f.setPasarela(rf.getPasarela());
			f.setFechaCre(rf.getFechaCre());
			f.setFechaPago(rf.getFechaPago());
		}
		return f;
	}
}
