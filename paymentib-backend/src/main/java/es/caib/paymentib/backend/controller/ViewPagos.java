package es.caib.paymentib.backend.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.paymentib.backend.model.DialogResult;
import es.caib.paymentib.backend.model.types.TypeParametroVentana;
import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.exception.MaxNumFilasException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;
import es.caib.paymentib.core.api.service.PagoBackService;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.plugins.api.TypeEstadoPago;

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

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;
	private Date filtroFechaDesde;
	private Date filtroFechaHasta;
	private TypeFiltroFecha filtroFecha;

	/**
	 * Lista de datos.
	 */
	private List<DatosSesionPago> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DatosSesionPago datoSeleccionado;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Control acceso
		UtilJSF.verificarAccesoSuperAdministrador();
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			filtroFechaDesde = formatter.parse(formatter.format(new Date()));
		} catch (final ParseException e) {
		}

		filtroFecha = TypeFiltroFecha.CREACION;
	}

	public boolean getFilaSeleccionada() {
		return datoSeleccionado != null;
	}

	public boolean getPermiteVerificar() {
		return (TypeEstadoPago.DESCONOCIDO.equals(datoSeleccionado.getEstado())
				|| TypeEstadoPago.NO_PAGADO.equals(datoSeleccionado.getEstado()));
	}

	public boolean getPermiteConfirmar() {
		return (TypeEstadoPago.DESCONOCIDO.equals(datoSeleccionado.getEstado())
				|| TypeEstadoPago.NO_PAGADO.equals(datoSeleccionado.getEstado()));
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
		UtilJSF.openDialog(DialogPagos.class, TypeModoAcceso.CONSULTA, params, true, 1100, 500);
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
	 * @param event
	 *            respuesta dialogo
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

		final TypeEstadoPago nuevoEstado = pagoFrontService
				.verificarPagoElectronico(getDatoSeleccionado().getDatosPago().getIdentificador());

		if (!nuevoEstado.equals(getDatoSeleccionado().getEstado())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("info.estado.cambio"));

			final int indice = listaDatos.indexOf(datoSeleccionado);
			final DatosSesionPago cambiado = pagoBackService.getPagoByCodigo(datoSeleccionado.getCodigo());
			listaDatos.set(indice, cambiado);
			datoSeleccionado = cambiado;
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
			listaDatos = pagoBackService.listaPagos(filtro, filtroFechaDesde, filtroFechaHasta, filtroFecha);
		} catch (final EJBException e) {
			if (e.getCause() instanceof MaxNumFilasException) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.maxnumfilas"));
				return;
			} else {
				throw e;
			}

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
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
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
	 * @param listaDatos
	 *            the listaDatos to set
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
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
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

}
