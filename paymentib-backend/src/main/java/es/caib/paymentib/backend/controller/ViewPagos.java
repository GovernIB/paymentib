package es.caib.paymentib.backend.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import es.caib.paymentib.backend.model.DialogResult;
import es.caib.paymentib.backend.model.types.TypeParametroVentana;
import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.exception.MaxNumFilasException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;
import es.caib.paymentib.core.api.model.types.TypeRoleAcceso;
import es.caib.paymentib.core.api.service.PagoBackService;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.core.api.exception.CargaConfiguracionException;

/**
 * Mantenimiento de entidades.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewPagos extends ViewControllerBase {

	/**
	 * Enlace servicio.
	 */
	/**
	 * Enlace servicio.
	 */
	@Inject
	private PagoBackService pagoBackService;

	@Inject
	private PagoFrontService pagoFrontService;

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;
	private Date filtroFechaDesde;
	private Date filtroFechaHasta;
	private TypeFiltroFecha filtroFecha;
	private String filtroClaveTramitacion;
	private String filtroTramite;
	private Integer filtroVersion;
	private String filtroPasarela;
	private String filtroEntidad;
	private String filtroAplicacion;
	private String filtroLocATIB;

	/**
	 * Lista de datos.
	 */
	private List<DatosSesionPago> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DatosSesionPago datoSeleccionado;

	private String portapapeles;

	private String errorCopiar;

	private List<String> listaPasarelas;
	private List<String> listaEntidades;
	private List<String> listaAplicaciones;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Control acceso
		UtilJSF.verificarAcceso();
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		String propiedad = propiedadesLocales.getProperty("filtroInicial");

		try {
			int num = Integer.parseInt(propiedad);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getToday()); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, -num); // numero de días a añadir, o restar en caso de días<0
			filtroFechaDesde = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		} catch (final NumberFormatException e) {
			filtroFechaDesde = getToday();
		}

		filtroFecha = TypeFiltroFecha.CREACION;

		listaPasarelas = pagoBackService.listaPasarelas();
		listaEntidades = pagoBackService.listaEntidades();
		listaAplicaciones = pagoBackService.listaAplicaciones();
	}

	public boolean getFilaSeleccionada() {
		return datoSeleccionado != null;
	}

	public boolean getPermiteVerificar() {
		return ((TypeEstadoPago.DESCONOCIDO.equals(datoSeleccionado.getEstado())
				|| TypeEstadoPago.NO_PAGADO.equals(datoSeleccionado.getEstado()))
				&& UtilJSF.isAccesoSuperAdministrador());
	}

	public boolean getPermiteConfirmar() {
		return ((TypeEstadoPago.DESCONOCIDO.equals(datoSeleccionado.getEstado())
				|| TypeEstadoPago.NO_PAGADO.equals(datoSeleccionado.getEstado()))
				&& UtilJSF.isAccesoSuperAdministrador());
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.copiar"));
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogPagos.class, TypeModoAcceso.CONSULTA, params, true, 1100, 540);
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void confirmar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogPagosConfirmar.class, TypeModoAcceso.EDICION, params, true, 400, 200);
	}

	/**
	 * Retorno dialogo confirmar
	 *
	 * @param event respuesta dialogo
	 ***/
	public void returnDialogoConfirmar(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			final DatosSesionPago pagado = (DatosSesionPago) respuesta.getResult();
			final int indice = listaDatos.indexOf(datoSeleccionado);
			listaDatos.set(indice, pagado);
			datoSeleccionado = pagado;
		}
	}

	public void verificar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		final EstadoPago nuevoEstado = pagoFrontService
				.verificarPagoElectronico(getDatoSeleccionado().getDatosPago().getIdentificador());

		if (!nuevoEstado.getEstado().equals(getDatoSeleccionado().getEstado())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("info.estado.cambio"));

			final int indice = listaDatos.indexOf(datoSeleccionado);
			final DatosSesionPago cambiado = pagoBackService.getPagoByCodigo(datoSeleccionado.getCodigo());
			listaDatos.set(indice, cambiado);
			datoSeleccionado = cambiado;
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("info.estado.noCambio"));
		}
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtroFechaDesde == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fecha.inicio.falta"));
			return;
		}

		if (filtroFechaDesde != null && filtroFechaHasta != null && filtroFechaDesde.after(filtroFechaHasta)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fecha.fin.anterior"));
			return;
		}
		if (filtroFecha == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fecha.tipo"));
			return;
		}

		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Buscar
		this.buscar();
	}

	/**
	 * Método final que se encarga de realizar la búsqueda
	 */
	private void buscar() {
		// Filtra

		try {
			listaDatos = pagoBackService.listaPagos(filtro, filtroFechaDesde, filtroFechaHasta, filtroFecha, filtroClaveTramitacion, filtroTramite, filtroVersion,
																filtroPasarela, filtroEntidad, filtroAplicacion, filtroLocATIB);
		} catch (final EJBException e) {
			/*if (e.getCause() instanceof MaxNumFilasException) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.maxnumfilas"));
				return;
			} else {*/
				throw e;
			//}

		}

		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("pagos");
	}

	private Properties recuperarConfiguracionProperties() {
		final String pathProperties = System.getProperty("es.caib.paymentib.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}

	private Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<DatosSesionPago> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<DatosSesionPago> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public DatosSesionPago getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final DatosSesionPago datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	public TypeFiltroFecha getFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(final TypeFiltroFecha filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public Date getFiltroFechaDesde() {
		return filtroFechaDesde;
	}

	public void setFiltroFechaDesde(final Date filtroFechaDesde) {
		this.filtroFechaDesde = filtroFechaDesde;
	}

	public Date getFiltroFechaHasta() {
		return filtroFechaHasta;
	}

	public void setFiltroFechaHasta(final Date filtroFechaFin) {
		this.filtroFechaHasta = filtroFechaFin;
	}

	public String getFiltroClaveTramitacion() {
		return filtroClaveTramitacion;
	}

	public void setFiltroClaveTramitacion(final String filtroClaveTramitacion) {
		this.filtroClaveTramitacion = filtroClaveTramitacion;
	}

	public String getFiltroTramite() {
		return filtroTramite;
	}

	public void setFiltroTramite(final String filtroTramite) {
		this.filtroTramite = filtroTramite;
	}

	public Integer getFiltroVersion() {
		return filtroVersion;
	}

	public void setFiltroVersion(final Integer filtroVersion) {
		this.filtroVersion = filtroVersion;
	}

	public String getFiltroPasarela() {
		return filtroPasarela;
	}

	public void setFiltroPasarela(String filtroPasarela) {
		this.filtroPasarela = filtroPasarela;
	}

	public List<String> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<String> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public String getFiltroEntidad() {
		return filtroEntidad;
	}

	public void setFiltroEntidad(String filtroEntidad) {
		this.filtroEntidad = filtroEntidad;
	}

	public String getFiltroAplicacion() {
		return filtroAplicacion;
	}

	public void setFiltroAplicacion(String filtroAplicacion) {
		this.filtroAplicacion = filtroAplicacion;
	}

	public List<String> getListaPasarelas() {
		return listaPasarelas;
	}

	public void setListaPasarelas(List<String> listaPasarelas) {
		this.listaPasarelas = listaPasarelas;
	}

	public List<String> getListaAplicaciones() {
		return listaAplicaciones;
	}

	public void setListaAplicaciones(List<String> listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}

	public String getFiltroLocATIB() {
		return filtroLocATIB;
	}

	public void setFiltroLocATIB(String filtroLocATIB) {
		this.filtroLocATIB = filtroLocATIB;
	}

}
