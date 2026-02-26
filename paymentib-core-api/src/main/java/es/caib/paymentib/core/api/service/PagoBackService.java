package es.caib.paymentib.core.api.service;

import java.util.Date;
import java.util.List;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.api.TypeModoValidacion;
import es.caib.paymentib.plugins.api.TypeEstadoPago;

/**
 * Acceso a funciones invocadas desde el back de pagos.
 *
 * @author Indra
 *
 */
public interface PagoBackService {


	/**
	 * Obtiene el logo del back.
	 *
	 * @return logo back
	 */
	String obtenerLogoBack();

	/**
	 * Obtiene las entidades de pago de una pasarela concreta.
	 * @param idPasarelaPago
	 * @param idioma
	 * @return Entidades de pago
	 */
	List<EntidadPago> obtenerEntidadesPagoPasarela(String idPasarelaPago, TypeIdioma idioma);

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
			final String filtroPasarela, final String filtroEntidad, final String filtroAplicacion, final String filtroLocATIB, final String filtroMetodosPago,
			final String filtroIdentificador, final TypeEstadoPago filtroEstado, final Double filtroImporte,
			final String filtroNIF, final String filtroNombre);

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
	 * Lista métodos de pago.
	 *
	 * @return lista de métodos de pago
	 */
	List<String> listaMetodosPago();

	/**
	 * Recupera datos sesión pago por Codigo.
	 *
	 * @param codigo
	 *            codigo
	 * @return datos sesión pago
	 */
	DatosSesionPago getPagoByCodigo(Long codigo);

	/**
	 * Recupera datos sesión pago por identificador.
	 *
	 * @param identificador
	 *            identificador
	 * @return datos sesión pago
	 */
	DatosSesionPago getPagoByIdentificador(String identificador);

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


	/**
	 * Obtiene el modo de validación de una pasarela.
	 * @param pasarelaId id de la pasarela
	 * @return Modo de validación de la pasarela
	 */
	TypeModoValidacion obtenerModoValidacionPasarela(String pasarelaId);

	/**
	 * Obtiene el directorio de ayuda externa.
	 * @return Directorio de ayuda externa
	 */
	String obtenerDirectorioAyudaExterna();

	/**
	 * Obtiene el filtro inicial de pagos.
	 * @return Filtro inicial de pagos
	 */
	String obtenerFiltroInicial();
}
