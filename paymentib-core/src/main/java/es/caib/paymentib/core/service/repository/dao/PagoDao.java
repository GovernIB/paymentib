package es.caib.paymentib.core.service.repository.dao;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.pago.FiltroPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EstadoPago;

/**
 * La interface PagoDao.
 */
public interface PagoDao {

	/**
	 * Obtiene la lista de todos los pagos usando un filtro
	 *
	 * @param filtro filtro
	 * @return lista de pagos
	 */
	List<DatosSesionPago> getAllByFiltro(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha);

	/**
	 * Obtiene la lista de todos los pagos
	 *
	 * @return lista de pagos
	 */
	List<DatosSesionPago> getAll();

	/**
	 * Crea pago y devuelve token de acceso.
	 *
	 * @param pasarelaId           pasarela id
	 * @param datosPago            Datos pago
	 * @param urlCallbackAppOrigen Url callback aplicación origen
	 * @param Token                token acceso
	 * @return identificador pago
	 */
	String create(String pasarelaId, DatosPago datosPago, String urlCallbackAppOrigen, String tokenAcceso);

	/**
	 * Inicia pago en pasarela.
	 *
	 * @param identificador identificador pago
	 * @param localizador   localizador pago
	 * @param token         token acceso para retorno pasarela
	 */
	void iniciar(String identificador, String localizador, String token);

	/**
	 * Recupera datos sesión pago por token.
	 *
	 * @param tokenSesion Token
	 * @return datos sesión
	 */
	DatosSesionPago getByToken(String tokenSesion);

	/**
	 * Recupera datos sesión pago por identificador.
	 *
	 * @param identificador identificador
	 * @return datos sesión
	 */
	DatosSesionPago getByIdentificador(String identificador);

	/**
	 * Actualiza estado pago.
	 *
	 * @param identificador identificador
	 *
	 * @param ep            Estado pago
	 */
	void actualizarEstado(String identificador, EstadoPago ep);

	/**
	 * Recupera datos sesión pago por Codigo.
	 *
	 * @param codigo codigo
	 * @return datos sesión
	 */
	DatosSesionPago getByCodigo(Long codigo);

	/**
	 * Realiza purga de pagos que hayan sobrepasado los días.
	 *
	 * @param dias dias
	 */
	void purgar(int dias);

	/**
	 * Confirmar pago.
	 *
	 * @param identificador identificador
	 * @param fechaPago     fecha pago
	 * @param usuario       usuario
	 */
	void confirmarPago(String identificador, Date fechaPago, String usuario);

	List<DatosSesionPago> getAllByFiltro(FiltroPago filtro, Date fechaDesde, Date fechaHasta, Long numPag,
			Long maxNumElem);

}
