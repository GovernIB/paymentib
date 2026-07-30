package es.caib.paymentib.plugins.atib;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import es.caib.paymentib.plugins.api.*;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.paymentib.plugins.atib.clientws.ClienteAtib;
import es.caib.paymentib.plugins.atib.clientws.cxf.ArrayOfGuid;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuesta046;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuestaGetUrlPago;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosTasa046;
import es.caib.paymentib.plugins.atib.xml.TaxaXml;

/**
 * Pasarela de pago ATIB.
 *
 * @author Indra
 *
 */
public class AtibPlugin extends AbstractPluginProperties implements IPasarelaPagoPlugin {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(AtibPlugin.class);

	/**
	 *
	 * Constructor.
	 *
	 * @param prefijoPropiedades prefijo
	 * @param properties         propiedades
	 */
	public AtibPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String getPasarelaId() {
		return "ATIB";
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(TypeIdioma idioma) {
		// Recuperamos todas las entidades de pago
		return recuperarEntidadesPago(idioma, null);
	}

	@Override
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final TypeIdioma idioma, final String metodosPago)
			throws PasarelaPagoException {
		// Recuperamos entidades de pago filtradas por metodosPago (si no indican metodos pago, por defecto TJ)
		List<EntidadPago> res = recuperarEntidadesPago(idioma, (metodosPago != null ? metodosPago : "TJ"));
		return res;
	}

	@Override
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final DatosPago datosPago, final String entidadPagoId,
			final String urlCallback) throws PasarelaPagoException {
		try {
			// Genera XML de pago
			final String xmlPago = TaxaXml.generarXml("pagar", datosPago, isEnviarMultiplicador1());
			final String xmlPagoB64 = Base64.getEncoder().encodeToString(xmlPago.getBytes("UTF-8"));

			log.debug("XML Pago: \n" + xmlPago);

			// Generamos cliente
			final ClienteAtib cliente = this.crearClienteAtib();

			// Enviamos pago
			final DatosRespuesta046 resInserta046 = cliente.inserta46(xmlPagoB64);
			if (resInserta046.getCodError() != null) {
				throw new PasarelaPagoException("Error enviando datos pago: " + resInserta046.getCodError() + " - "
						+ resInserta046.getTextError());
			}

			// Obtenemos url pago
			final ArrayOfGuid refsModelos = new ArrayOfGuid();
			refsModelos.getGuid().add(resInserta046.getToken());
			String idioma = datosPago.getIdioma().toString().equals("es") ? "02" : "01";
			DatosRespuestaGetUrlPago resUrlPago = null;
			if (isModoSimulado()) {
				// TODO MODO SIMULADO
				throw new RuntimeException("PENDIENTE DE IMPLEMENTAR MODO SIMULADO");
			} else {
			 	resUrlPago= cliente.getUrlPago(refsModelos, entidadPagoId, urlCallback,
						idioma);
			}

			if (resUrlPago.getUrl() == null) {
				throw new PasarelaPagoException("Error obteniendo url pago ");
			}

			// Retornamos datos inicio pago
			final UrlRedireccionPasarelaPago resultado = new UrlRedireccionPasarelaPago();
			resultado.setLocalizador(resInserta046.getLocalizador());
			resultado.setUrl(resUrlPago.getUrl());
			return resultado;
		} catch (final Exception ex) {
			throw new PasarelaPagoException("Excepcion invocando pasarela: " + ex.getMessage(), ex);
		}
	}

	@Override
	public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId,
			final Map<String, String[]> parametrosRetorno) throws PasarelaPagoException {
		DatosRespuesta046 resVerificacion = verificarPagoImpl(localizador);
		final EstadoPago estadoPago = generarEstadoPago(resVerificacion, entidadPagoId);
		return estadoPago;
	}

	@Override
	public EstadoPago verificarPagoElectronico(final DatosPago datosPago, final String localizador, final String entidadPagoId)
			throws PasarelaPagoException {
		DatosRespuesta046 resVerificacion = verificarPagoImpl(localizador);
		final EstadoPago estadoPago = generarEstadoPago(resVerificacion, entidadPagoId);
		return estadoPago;
	}

	@Override
	public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago, final String localizador, final Date fechaPago)
			throws PasarelaPagoException {

		int importe = datosPago.getImporte();
		String sujetoPasivoNif = datosPago.getSujetoPasivoNif();

		return obtenerJustificantePasarela(localizador, fechaPago, importe, sujetoPasivoNif);
	}

	@Override
	public int consultaTasa(final String idTasa) throws PasarelaPagoException {
		log.debug("Consulta tasa: " + idTasa);
		final ClienteAtib clienteAtib = crearClienteAtib();
		final DatosTasa046 res = clienteAtib.consultaDatosTasa046(idTasa);
		if (res.getCodError() != null) {
			throw new PasarelaPagoException(
					"Error consultando tasa: " + res.getCodError() + " - " + res.getDescripcion());
		}
		log.debug("Tasa: " + res.getImporte() + " cents");
		return Integer.parseInt(res.getImporte());
	}

	@Override
	public boolean permitePagoPresencial() {
		return true;
	}

	@Override
	public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago) throws PasarelaPagoException {
		try {
			log.debug("Obtener carta pago");

			// Genera XML de pago
			final String xmlPago = TaxaXml.generarXml("imprimir", datosPago, isEnviarMultiplicador1());
			final String xmlPagoB64 = Base64.getEncoder().encodeToString(xmlPago.getBytes("UTF-8"));

			// Generamos cliente
			final ClienteAtib cliente = this.crearClienteAtib();

			// Enviamos pago
			final DatosRespuesta046 resInserta046 = cliente.inserta46(xmlPagoB64);
			if (resInserta046.getCodError() != null) {
				throw new PasarelaPagoException("Error enviando datos pago: " + resInserta046.getCodError() + " - "
						+ resInserta046.getTextError());
			}

			// Obtenemos PDF
			final byte[] resPDF = cliente.getPdf046(resInserta046.getLocalizador(),
					centsToEur( Integer.toString(datosPago.getImporte())), datosPago.getSujetoPasivoNif(),
					new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

			if (resPDF == null) {
				throw new PasarelaPagoException("Error obteniendo carta pago");
			}

			log.debug("Carta de pago obtenida");

			return resPDF;

		} catch (final Exception ex) {
			throw new PasarelaPagoException("Excepcion invocando pasarela: " + ex.getMessage(), ex);
		}
	}

	@Override
	public TypeModoValidacion obtenerModoValidacion() {
		return TypeModoValidacion.VERIFICACION;
	}

	@Override
	public TypeValidacionPagoExterno verificarPagoExterno(DatosPago datosPago, String localizador, Date fechaPago) throws PasarelaPagoException {

		// Resultado
		TypeValidacionPagoExterno resultado = TypeValidacionPagoExterno.NO_VALIDO;

		// Verifica si esta pagado por localizador
		DatosRespuesta046 resVerificacion = verificarPagoImpl(localizador);
		final EstadoPago estadoPago = generarEstadoPago(resVerificacion, IPasarelaPagoPlugin.ENTIDAD_PAGO_EXTERNO);

		// Si esta pagado, validamos datos
		if (estadoPago.getEstado() == TypeEstadoPago.PAGADO) {
			// Nif sujeto pasivo
			if (!StringUtils.equalsIgnoreCase(datosPago.getSujetoPasivoNif(), resVerificacion.getNifSujetoPasivo())) {
				// NIF sujeto pasivo (indicamos que no valido para no dar pistas)
				resultado = TypeValidacionPagoExterno.NO_VALIDO;
			} else if (!esMismaFechaPago(fechaPago, estadoPago.getFechaPago())) {
				// Fecha pago
				resultado = TypeValidacionPagoExterno.FECHA_NO_COINCIDE;
			} else if (datosPago.getImporte() != Integer.parseInt(resVerificacion.getImportePago())) {
				// Importe
				resultado = TypeValidacionPagoExterno.IMPORTE_NO_COINCIDE;
			} else if (!StringUtils.equalsIgnoreCase(datosPago.getTasaId(), resVerificacion.getIdTasa())) {
				// Tasa
				resultado = TypeValidacionPagoExterno.TASA_NO_COINCIDE;
			} else {
				// Si pasa validaciones, se da como correcto
				resultado = TypeValidacionPagoExterno.VERIFICADO;
			}
		}
		// Retornamos justificante (puede ser nulo si no se ha podido obtener)
		return resultado;
	}

	/**
	 * Crea cliente ws ATIB.
	 *
	 * @throws PasarelaPagoException
	 */
	private ClienteAtib crearClienteAtib() throws PasarelaPagoException {
		try {
			return new ClienteAtib(this.getProperty("url"), this.getProperty("usr"), this.getProperty("pwd"));
		} catch (final Exception e) {
			throw new PasarelaPagoException("Excepcion obteniendo cliente ATIB: " + e.getMessage(), e);
		}
	}

	/**
	 * Convierte de cents a euros.
	 *
	 * @param importe importe en cents
	 * @return importe en euros
	 */
	private String centsToEur(final String importe) {
		final double impDec = Double.parseDouble(importe) / 100;
		final DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance();
		f.setDecimalSeparatorAlwaysShown(true);
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setMinimumIntegerDigits(1);
		final String importeDec = f.format(impDec);
		return importeDec;
	}

	/**
	 * Verifica pago
	 *
	 * @param localizador
	 * @return
	 * @throws PasarelaPagoException
	 */
	private DatosRespuesta046 verificarPagoImpl(final String localizador) throws PasarelaPagoException {

		// TODO VER QUE PASA SI SE LE PASA LOCALIZADOR QUE NO EXISTE
		try {
			log.debug("Verificar estado pago");

			// Generamos cliente
			final ClienteAtib cliente = this.crearClienteAtib();

			// Verifica estado pago
			DatosRespuesta046 resEstado = null;
			if (isModoSimulado()) {
				// TODO MODO SIMULADO
				throw new RuntimeException("PENDIENTE DE IMPLEMENTAR MODO SIMULADO");
			} else {
				resEstado = cliente.estado046(localizador);
			}
			log.debug("Estado pago: CodError: " + resEstado.getCodError() + " MensajeError: " + resEstado.getTextError()
					+ " Estado: " + resEstado.getEstadoPago());

			return resEstado;

		} catch (final Exception ex) {
			throw new PasarelaPagoException("Excepcion invocando pasarela: " + ex.getMessage(), ex);
		}
	}

/**
 * Obtiene entidades de pago.
  * @param idioma Idioma
 * @param metodos Filtro por metodos
 * @return Lista de entidades de pago
 */
private List<EntidadPago> recuperarEntidadesPago(TypeIdioma idioma, String metodos) {
	List<String> metodosPagoList = null;
	if (metodos != null) {
		metodosPagoList = Arrays.asList(metodos.split(";"));
	}

	final List<EntidadPago> res = new ArrayList<>();
	final String entidadesPago[] = this.getProperty("entidadesPago").split(";");
	for (final String id : entidadesPago) {
		// Si esta habilitado filtro, filtramos
		if (metodosPagoList != null && !metodosPagoList.contains(id)) {
			continue;
		}
		// Añadimos a lista
		EntidadPago ep = new EntidadPago();
		ep.setCodigo(id);
		ep.setTitulo(this.getProperty("entidadPago." + id + ".titulo." + idioma.toString()));
		ep.setDescripcion(this.getProperty("entidadPago." + id + ".descripcion." + idioma.toString()));
		ep.setLogo(this.getProperty("entidadPago." + id + ".logo"));
		res.add(ep);
	}
	// Retornamos resultado
	return res;
}

	/**
	 * Obtiene justificante de pago.
	 *
	 * @param localizador       Localizador pago en la pasarela
	 * @param fechaPago    Fecha de creación del pago
	 * @param importe          Importe del pago
	 * @param sujetoPasivoNif  NIF del sujeto pasivo
	 * @return Justificante de pago
	 * @throws PasarelaPagoException En caso de error
	 */
	private byte[] obtenerJustificantePasarela(String localizador, Date fechaPago, int importe, String sujetoPasivoNif) throws PasarelaPagoException {
		try {
			log.debug("Obtener justificante pago");

			// Generamos cliente
			final ClienteAtib cliente = this.crearClienteAtib();

			// Obtenemos PDF
			final byte[] resPDF = cliente.getPdf046(localizador, centsToEur(importe + ""),
					sujetoPasivoNif, new SimpleDateFormat("dd/MM/yyyy").format(fechaPago));

			if (resPDF == null) {
				throw new PasarelaPagoException("Error obteniendo justificante pago");
			}

			log.debug("Justificante de pago obtenido");

			return resPDF;

		} catch (final Exception ex) {
			throw new PasarelaPagoException("Excepcion invocando pasarela: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Indica si está en modo simulado.
	 * @return true si está en modo simulado, false en caso contrario
	 */
	private boolean isModoSimulado() {
        return "true".equals(this.getProperty("modoSimulado"));
	}

	/**
	 * Indica si se debe enviar multiplicador 1 (fuerza validación tasa en ATIB)
	 * @return true si se debe enviar multiplicador 1, false en caso contrario
	 */
	private boolean isEnviarMultiplicador1() {
        return "true".equals(this.getProperty("enviarMultiplicador1"));
	}


	/**
	 * Genera EstadoPago a partir de respuesta ATIB.
	 *
	 * @param resEstado     Respuesta ATIB
	 * @param entidadPagoId
	 * @return EstadoPago generado
	 */
	private static EstadoPago generarEstadoPago(DatosRespuesta046 resEstado, String entidadPagoId) throws PasarelaPagoException {
		try {
			final EstadoPago estadoPago = new EstadoPago();
			estadoPago.setLocalizador(resEstado.getLocalizador());
			estadoPago.setMetodoPago(entidadPagoId);
			if (resEstado.getCodError() != null) {
				estadoPago.setEstado(TypeEstadoPago.DESCONOCIDO);
				estadoPago.setCodigoErrorPasarela(resEstado.getCodError().toString());
				estadoPago.setMensajeErrorPasarela(resEstado.getTextError());
			} else {
				if ("OK".equals(resEstado.getEstadoPago())) {
					estadoPago.setEstado(TypeEstadoPago.PAGADO);
					estadoPago.setFechaPago(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(resEstado.getFechaPago()));
				} else {
					estadoPago.setEstado(TypeEstadoPago.NO_PAGADO);
				}
			}
			return estadoPago;
		} catch (final Exception ex) {
			throw new PasarelaPagoException("Error generando estado pago: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Verifica si es misma fecha (no tiene en cuenta horas).
	 * @param d1 Fecha 1
	 * @param d2 Fecha 2
	 * @return true si son la misma fecha, false en caso contrario
	 */
	public boolean esMismaFechaPago(Date d1, Date d2) {
		LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ld2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld1.compareTo(ld2) == 0;
	}


}
