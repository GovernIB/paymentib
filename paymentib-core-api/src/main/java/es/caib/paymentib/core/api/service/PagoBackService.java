package es.caib.paymentib.core.api.service;

import java.util.Date;
import java.util.List;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;

/**
 * Acceso a funciones invocadas desde el back de pagos.
 *
 * @author Indra
 *
 */
public interface PagoBackService {

	/**
	 * Lista pagos.
	 *
	 * @param filtro
	 *            filtro
	 * @param fechaDesde
	 *            fecha desde
	 * @param fechaHasta
	 *            fecha hasta
	 * @param tipoFecha
	 *            tipo fecha
	 * @return lista de pagos
	 */
	List<DatosSesionPago> listaPagos(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha, final String filtroClaveTramitacion, final String filtroTramite, final Integer filtroVersion,
			final String filtroPasarela, final String filtroEntidad, final String filtroAplicacion, final String filtroLocATIB);

	/**
	 * Lista pasarelas.
	 *
	 * @return lista de pasarelas
	 */
	List<String> listaPasarelas();

	/**
	 * Lista entidades.
	 *
	 * @return lista de entidades
	 */
	List<String> listaEntidades();

	/**
	 * Lista aplicaciones.
	 *
	 * @return lista de aplicaciones
	 */
	List<String> listaAplicaciones();

	/**
	 * Recupera datos sesión pago por Codigo.
	 *
	 * @param codigo
	 *            codigo
	 * @return datos sesión pago
	 */
	DatosSesionPago getPagoByCodigo(Long codigo);

	/**
	 * Confirmar pago.
	 *
	 * @param identificador
	 *            identificador pago
	 * @param fechaPago
	 *            fecha pago
	 * @param usuarioConfirmacion
	 *            usuario confirmacion
	 */
	void confirmarPago(final String identificador, final Date fechaPago, final String usuarioConfirmacion);

}
