package es.caib.paymentib.core.service;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.paymentib.core.api.exception.CartaPagoException;
import es.caib.paymentib.core.api.exception.ConsultarTasaException;
import es.caib.paymentib.core.api.exception.EstadoSesionPagoException;
import es.caib.paymentib.core.api.exception.InicioPagoException;
import es.caib.paymentib.core.api.exception.JustificantePagoException;
import es.caib.paymentib.core.api.exception.NoExisteSesionPagoException;
import es.caib.paymentib.core.api.exception.ObtenerEntidadesException;
import es.caib.paymentib.core.api.exception.TokenSesionPagoException;
import es.caib.paymentib.core.api.exception.VerificacionPagoException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.core.api.util.GeneradorId;
import es.caib.paymentib.core.service.component.ConfiguracionComponent;
import es.caib.paymentib.core.service.repository.dao.PagoDao;
import es.caib.paymentib.core.service.util.GeneradorJustificantePago;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;
import es.caib.paymentib.plugins.api.PasarelaPagoException;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

/**
 * Implementación PagoFrontService.
 *
 * @author Indra
 */
@Service
@Transactional
public final class PagoFrontServiceImpl implements PagoFrontService {

	// TODO Meter en las excepciones el identificador del pago

	/** Log. */
	private final org.slf4j.Logger log = LoggerFactory.getLogger(PagoFrontServiceImpl.class);

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	/** Dao. */
	@Autowired
	private PagoDao dao;

	@Override
	public DatosSesionPago crearPagoElectronico(final String pasarelaId, final DatosPago datosPago,
			final String urlCallbackAppOrigen) {

		// Almacenar en persistencia los datos del pago y retorna token acceso
		final String tokenAcceso = GeneradorId.generarId();
		final String identificador = dao.create(pasarelaId, datosPago, urlCallbackAppOrigen, tokenAcceso);
		log.debug("Creado pago electronico con identificador: " + identificador + " y token acceso: " + tokenAcceso);

		return this.recuperarSesionPagoByIdentificador(identificador);
	}

	@Override
	public DatosSesionPago recuperarPagoElectronicoByToken(final String tokenSesion) {
		log.debug("Recuperando pago con token: " + tokenSesion);
		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByToken(tokenSesion);
		log.debug("Recuperado pago  " + dp.getDatosPago().getIdentificador());
		// Retornamos datos sesión pago
		return dp;
	}

	@Override
	public DatosSesionPago recuperarPagoElectronico(final String identificador) {
		log.debug("Recuperando pago con identificador: " + identificador);
		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByIdentificador(identificador);
		log.debug("Recuperado pago  " + dp.getDatosPago().getIdentificador());
		// Retornamos datos sesión pago
		return dp;
	}

	@Override
	public UrlRedireccionPasarelaPago redirigirPasarelaPago(final String identificador, final String entidadPagoId,
			final String urlCallbackCompPagos) {

		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByIdentificador(identificador);
		if (dp.getEstado() != TypeEstadoPago.NO_INICIADO) {
			throw new EstadoSesionPagoException("El estado debe ser no iniciado");
		}

		// Genera token y anexa a url callback
		final String token = GeneradorId.generarId();
		final String urlCallbackToken = urlCallbackCompPagos + "/" + token + ".html";

		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(dp.getPasarelaId());

		// Genera pago en pasarela
		UrlRedireccionPasarelaPago url = null;
		try {
			url = plgPago.iniciarPagoElectronico(dp.getDatosPago(), entidadPagoId, urlCallbackToken);
		} catch (final PasarelaPagoException e) {
			throw new InicioPagoException(e);
		}

		// Almacena pago en persistencia cambiando estado
		dao.iniciar(dp.getDatosPago().getIdentificador(), url.getLocalizador(), token);

		return url;

	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final String identificador) {

		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByIdentificador(identificador);
		if (dp == null) {
			throw new NoExisteSesionPagoException(identificador);
		}

		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(dp.getPasarelaId());

		// Obtiene entidades de pago
		try {
			return plgPago.obtenerEntidadesPagoElectronico(dp.getDatosPago().getIdioma());
		} catch (final PasarelaPagoException e) {
			throw new ObtenerEntidadesException(e);
		}
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final String identificador,
			final Map<String, String[]> parametrosRetorno) {
		final EstadoPago res = verificarPagoImpl(identificador, true, parametrosRetorno);
		return res;
	}

	@Override
	public EstadoPago verificarPagoElectronico(final String identificador) {
		final EstadoPago res = verificarPagoImpl(identificador, false, null);
		return res;
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final String identificador) {
		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByIdentificador(identificador);
		if (dp == null) {
			throw new NoExisteSesionPagoException(identificador);
		}

		// Verificamos estado pagado
		if (dp.getEstado() != TypeEstadoPago.PAGADO) {
			throw new EstadoSesionPagoException("El pago no está completado");
		}

		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(dp.getPasarelaId());

		// Obtiene justificante
		byte[] justif = null;
		try {
			justif = plgPago.obtenerJustificantePagoElectronico(dp.getDatosPago(), dp.getLocalizador());

			// Si la pasarela no provee justificante, proveemos justificante
			// genérico
			if (justif == null) {
				justif = GeneradorJustificantePago.generarJustificantePago(config.obtenerDirectorioConfiguracion(), dp);
			}

		} catch (final PasarelaPagoException e) {
			throw new JustificantePagoException(e);
		}

		return justif;

	}

	@Override
	public int consultaTasa(final String pasarelaId, final String idTasa) {
		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(pasarelaId);
		// Consulta tasa
		try {
			return plgPago.consultaTasa(idTasa);
		} catch (final PasarelaPagoException e) {
			throw new ConsultarTasaException(idTasa, e);
		}
	}

	@Override
	public boolean permitePagoPresencial(final String pasarelaId) {
		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(pasarelaId);
		// Consulta si permite paso presencial
		return plgPago.permitePagoPresencial();
	}

	@Override
	public byte[] obtenerCartaPagoPresencial(final String pasarelaId, final DatosPago datosPago) {
		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = crearPlugin(pasarelaId);
		// Consulta tasa
		try {
			return plgPago.obtenerCartaPagoPresencial(datosPago);
		} catch (final PasarelaPagoException e) {
			throw new CartaPagoException(e);
		}
	}

	@Override
	public String obtenerUrlFrontal() {
		return config.obtenerPropiedadConfiguracion("frontal.url");
	}

	// --------------------------------------------------------
	// Funciones privadas
	// --------------------------------------------------------

	/**
	 * Crea plugin pasarela pago.
	 *
	 * @param pasarelaId id pasarela
	 * @return plugin pago
	 */
	private IPasarelaPagoPlugin crearPlugin(final String pasarelaId) {
		final IPasarelaPagoPlugin plgPago = config.obtenerPluginPasarelaPago(pasarelaId);
		return plgPago;
	}

	private DatosSesionPago recuperarSesionPagoByToken(final String tokenSesion) {
		final DatosSesionPago dp = dao.getByToken(tokenSesion);
		if (dp == null) {
			throw new TokenSesionPagoException(tokenSesion);
		}
		return dp;
	}

	private DatosSesionPago recuperarSesionPagoByIdentificador(final String identificador) {
		final DatosSesionPago dp = dao.getByIdentificador(identificador);
		if (dp == null) {
			throw new NoExisteSesionPagoException(identificador);
		}
		return dp;
	}

	private EstadoPago verificarPagoImpl(final String identificador, final boolean retornoPago,
			final Map<String, String[]> parametrosRetorno) {
		EstadoPago res = null;
		// Recuperamos sesion pago
		final DatosSesionPago dp = recuperarSesionPagoByIdentificador(identificador);
		// Si no esta iniciado o si ya esta pagado, establecemos estado de
		// persistencia
		if (dp.getEstado() == TypeEstadoPago.NO_INICIADO || dp.getEstado() == TypeEstadoPago.PAGADO) {
			res = new EstadoPago();
			res.setEstado(dp.getEstado());
			res.setLocalizador(dp.getLocalizador());
			res.setFechaPago(dp.getFechaPago());
			res.setCodigoErrorPasarela(dp.getCodigoErrorPasarela());
			res.setMensajeErrorPasarela(dp.getMensajeErrorPasarela());
		} else {
			// Verificamos estado contra la pasarela
			// Crea plugin pago
			final IPasarelaPagoPlugin plgPago = crearPlugin(dp.getPasarelaId());
			try {
				// Verifica estado pago según si es retorno o una verifación
				// normal
				EstadoPago ep = null;
				if (retornoPago) {
					ep = plgPago.verificarRetornoPagoElectronico(dp.getDatosPago(), dp.getLocalizador(),
							parametrosRetorno);
				} else {
					ep = plgPago.verificarPagoElectronico(dp.getDatosPago(), dp.getLocalizador());
				}
				// Actualizamos estado
				dao.actualizarEstado(identificador, ep);
				res = ep;
			} catch (final PasarelaPagoException e) {
				throw new VerificacionPagoException(identificador, e);
			}
		}
		return res;
	}

}
