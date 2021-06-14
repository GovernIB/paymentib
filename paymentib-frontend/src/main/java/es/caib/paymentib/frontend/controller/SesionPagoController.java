package es.caib.paymentib.frontend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.paymentib.core.api.exception.ServiceException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.frontend.SesionHttp;
import es.caib.paymentib.frontend.model.DatosSession;
import es.caib.paymentib.frontend.model.ErrorCodes;
import es.caib.paymentib.frontend.model.ModuleConfig;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * Sesión de pagos.
 *
 * @author Indra
 *
 */
@Controller
public final class SesionPagoController {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SesionPagoController.class);

	/** Info sesión web. */
	@Autowired
	private SesionHttp sesionHttp;

	/** Configuracion. */
	@Autowired
	private ModuleConfig config;

	/** Service. */
	@Autowired
	private PagoFrontService service;

	/** Mensaje error particularizado usuario. */
	private static final String ERROR_MESSAGE_USER = "ERROR_MESSAGE_USER";

	/** Mapping redirección pasarela. */
	private final static String URL_REDIRIGIR_PASARELA = "redirigirPagoPasarela";

	/** Mapping selección entidad pago. */
	private final static String URL_SELECCION_ENTIDAD_PAGO = "seleccionEntidadPago";

	/** Mapping retorno pasarela. */
	private final static String URL_RETORNO_PASARELA = "retornoPagoPasarela";

	/** Confirmacion pago pasarela (tpv). */
	private final static String URL_CONFIRMACION_PASARELA = "confirmacionPagoPasarela";

	/**
	 * Muestra pagina de bienvenida.
	 *
	 * @return pagina bienvenida para test
	 */
	@RequestMapping("/index.html")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	/**
	 * Inicio proceso de pago.
	 *
	 * @param token
	 *                  token
	 * @return Redirige inicio pago
	 */
	@RequestMapping(value = "/inicioPago/{token}.html")
	public ModelAndView inicioPago(@PathVariable("token") final String token) {
		// Recupera datos pago
		final DatosSesionPago dp = service.recuperarPagoElectronicoByToken(token);
		// Almacena identificador en sesión
		sesionHttp.setIdentificador(dp.getDatosPago().getIdentificador());
		sesionHttp.setIdioma(dp.getDatosPago().getIdioma().toString());
		// Redirige a selección entidad pago
		return new ModelAndView("redirect:/" + URL_SELECCION_ENTIDAD_PAGO + ".html");
	}

	/**
	 * Seleccion entidad de pago.
	 *
	 * @param token
	 *                  token
	 * @return Redirige inicio pago
	 */
	@RequestMapping(value = "/" + URL_SELECCION_ENTIDAD_PAGO + ".html")
	public ModelAndView seleccionEntidadPago() {
		// Identificador sesión
		final String identificador = sesionHttp.getIdentificador();
		// Obtiene entidades de pago
		final List<EntidadPago> entidadesPago = service.obtenerEntidadesPagoElectronico(identificador);
		ModelAndView res = null;
		if (entidadesPago.size() == 1) {
			// Si solo hay una entidad, la seleccionamos automáticamente
			res = new ModelAndView(
					"redirect:/" + URL_REDIRIGIR_PASARELA + ".html?entidadPagoId=" + entidadesPago.get(0).getCodigo());
		} else {
			// Si hay más de una entidad, mostramos pantalla de seleccion
			final DatosSession datos = new DatosSession();
			datos.setEntidadesPago(entidadesPago);
			if (config.getCommitGit() != null && !config.getCommitGit().isEmpty()) {
				datos.setCommit(config.getCommitGit());
			} else {
				datos.setCommit(config.getCommitSvn());
			}
			datos.setEntorno(config.getEntorno());
			datos.setVersion(config.getVersion());
			res = new ModelAndView(URL_SELECCION_ENTIDAD_PAGO, "datos", datos);
		}
		return res;
	}

	/**
	 * Redirige pasarela pago.
	 *
	 * @param token
	 *                  token
	 * @return Redirige inicio pago
	 */
	@RequestMapping(value = "/" + URL_REDIRIGIR_PASARELA + ".html")
	public ModelAndView redirigirPagoPasarela(@RequestParam("entidadPagoId") final String entidadPagoId) {
		// Identificador sesión
		final String identificador = sesionHttp.getIdentificador();
		// Genera url callback (se adicionará token en service)
		final String urlCallback = service.obtenerUrlFrontal() + "/" + URL_RETORNO_PASARELA;
		// Obtiene url redirección pago
		final UrlRedireccionPasarelaPago url = service.redirigirPasarelaPago(identificador, entidadPagoId,
				urlCallback);
		// Eliminamos identificador de sesion
		sesionHttp.setIdentificador(null);
		// Muestra pantalla para redirigir pago
		return new ModelAndView(URL_REDIRIGIR_PASARELA, "urlPasarela", url);
	}

	/**
	 * Retorno pasarela pagos.
	 *
	 * @param token
	 *                  token
	 * @return Vuelve a aplicación origen
	 */
	@RequestMapping(value = "/" + URL_RETORNO_PASARELA + "/{token}.html")
	public ModelAndView retornoPagoPasarela(@PathVariable("token") final String token,
			final HttpServletRequest request) {

		// Recupera datos pago
		final DatosSesionPago dp = service.recuperarPagoElectronicoByToken(token);
		sesionHttp.setIdioma(dp.getDatosPago().getIdioma().toString());

		if (dp.getEstado() != TypeEstadoPago.PAGADO) {
			// Verifica pago electrónico
			try {
				final EstadoPago res = service.verificarRetornoPagoElectronico(dp.getDatosPago().getIdentificador(),
						request.getParameterMap());
				log.debug("Resultado pago:" + res.getEstado());
			} catch (final Exception ex) {
				// Blindamos para que siempre retorne a aplicación origen
				log.debug("No se puede verificar pago, retornamos aplicación origen");
			}
		}

		// Retorna aplicación origen
		return new ModelAndView("redirect:" + dp.getUrlCallbackOrigen());
	}

	/**
	 * Confirmacion pago pasarela pagos.
	 *
	 * @param token
	 *                  token
	 * @return Vuelve a aplicación origen
	 */
	@RequestMapping(value = "/" + URL_CONFIRMACION_PASARELA + "/{identificador}.html")
	public ResponseEntity<Object> confirmacionPagoPasarela(@PathVariable("identificador") final String identificador,
			final HttpServletRequest request) {

		// TODO TPV VER SI SE DEBE PROTEGER ACCESO X IP

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		try {

			// Recupera datos pago
			final DatosSesionPago dp = service.recuperarPagoElectronico(identificador);
			sesionHttp.setIdioma(dp.getDatosPago().getIdioma().toString());

			if (dp.getEstado() == TypeEstadoPago.PAGADO) {
				status = HttpStatus.OK;
			} else {
				// Verifica pago electrónico

				final EstadoPago estado = service.verificarRetornoPagoElectronico(dp.getDatosPago().getIdentificador(),
						request.getParameterMap());
				if (estado.getEstado() == TypeEstadoPago.PAGADO) {
					status = HttpStatus.OK;
				}
				log.debug("Resultado pago:" + estado.getEstado());
			}

		} catch (final Exception ex) {
			// Blindamos para que siempre retorne a aplicación origen
			log.debug("No se puede verificar pago, retornamos aplicación origen");
		}

		// Retorna resultado
		return new ResponseEntity<>(status);

	}

	/**
	 * Muestra error.
	 *
	 * @param errorCode
	 *                      codigo error
	 * @return pagina error
	 */
	@RequestMapping("/error.html")
	public ModelAndView error(@RequestParam("code") final String errorCode, final HttpServletRequest request) {

		// Mostramos pagina generica de error
		ErrorCodes error = ErrorCodes.fromString(errorCode);
		String view = "errorGeneral";
		if (error == null) {
			error = ErrorCodes.ERROR_GENERAL;
		}
		String errorMsg = error.toString();

		// En caso de existir error particular, mostramos mensaje particular
		if (request.getSession().getAttribute(ERROR_MESSAGE_USER) != null) {
			view = "errorDetalle";
			errorMsg = (String) request.getSession().getAttribute(ERROR_MESSAGE_USER);
		}

		return new ModelAndView(view, "mensaje", errorMsg);

	}

	/**
	 * Handler de excepciones de negocio.
	 *
	 * @param pex
	 *                    Excepción
	 * @param request
	 *                    Request
	 * @return Respuesta JSON indicando el mensaje producido
	 */
	@ExceptionHandler({ Exception.class })
	public ModelAndView handleServiceException(final Exception pex, final HttpServletRequest request) {

		// Si no es una excepcion de negocio, generamos log
		if (!(pex instanceof ServiceException)) {
			log.error("Excepcion en capa front: " + pex.getMessage(), pex);
		}

		// Mostramos pagina generica de error
		return new ModelAndView("redirect:/error.html?code=" + ErrorCodes.ERROR_GENERAL.toString());
	}

}
