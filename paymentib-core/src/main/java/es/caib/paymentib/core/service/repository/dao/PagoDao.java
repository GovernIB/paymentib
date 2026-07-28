package es.caib.paymentib.core.service.repository.dao;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.pago.FiltroPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;

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
			final TypeFiltroFecha tipoFecha, final String filtroClaveTramitacion, final String filtroTramite, final Integer filtroVersion,
			final String filtroPasarela, final String filtroEntidad, final String filtroAplicacion, final String filtroLocATIB,
			final String filtroMetodosPago, final String filtroIdentificador, final TypeEstadoPago filtroEstado, final Double filtroImporte,
			final String filtroNIF, final String filtroNombre, final Integer filtroUnidades);

	/**
	 * Obtiene la lista de todas las pasarelas
	 *
	 * @return lista de pasarelas
	 */
	List<String> getPasarelas();

	/**
	 * Obtiene la lista de todas las entidades
	 *
	 * @return lista de entidades
	 */
	List<String> getEntidades();

	/**
	 * Obtiene la lista de todas las aplicaciones
	 *
	 * @return lista de aplicaciones
	 */
	List<String> getAplicaciones();

	/**
	 * Obtiene la lista de todos los métodos de pago
	 *
	 * @return lista de métodos de pago
	 */
	List<String> getMetodosPago();

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
	 * @param tokenAcceso                token acceso
	 * @return identificador pago
	 */
	String create(String pasarelaId, DatosPago datosPago, String urlCallbackAppOrigen, String tokenAcceso);

	/**
	 * Inicia pago en pasarela.
	 *
	 * @param identificador identificador pago
	 * @param localizador   localizador pago
	 * @param token         token acceso para retorno pasarela
	 * @param entidadPagoId    entidad de pago seleccionada (método pago)
	 */
	void iniciar(String identificador, String localizador, String token, String entidadPagoId);

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
	 * @param identificador        identificador
	 * @param estado               estado
	 * @param fechaPago            fecha pago
	 * @param codigoErrorPasarela  codigo error pasarela
	 * @param mensajeErrorPasarela mensaje error pasarela
	 */
	void actualizarEstado(String identificador, TypeEstadoPago estado, Date fechaPago, String codigoErrorPasarela, String mensajeErrorPasarela);


	/**
	 * Marca pago como verificado externamente.
	 * @param identificador identificador
	 * @param localizador localizador
	 * @param fechaPago fecha pago
	 */
	void verificadoPagoExterno(String identificador, String localizador, Date fechaPago);

	/**
	 * Actualiza mensaje error (para cuando la pasarela no llega a devolver estado).
	 *
	 * @param identificador identificador
	 *
	 * @param mensajeError  Mensaje error
	 */
	void actualizarMensajeError(String identificador, String mensajeError);

	/**
	 * Recupera datos sesión pago por Codigo.
	 *
	 * @param codigo codigo
	 * @return datos sesión
	 */
	DatosSesionPago getByCodigo(Long codigo);

	/**
	 * Recupera datos sesión pago por localizador.
	 *
	 * @param localizador localizador
	 * @return datos sesión
	 */
	DatosSesionPago getByLocalizador(String localizador);

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

	/**
	 * Marca que se ha iniciado la redirección a la pasarela de pago. Sirve para controlar que solo se haga 1 vez.
	 * @param identificador identificador pago
	 * @return true si se ha podido marcar, false si ya estaba marcado
	 */
    boolean iniciarRedireccionPasarelaPago(String identificador);

	/**
	 * Marca que se ha seleccionado un pago externo para su verificación.
	 * @param identificador identificador pago
	 */
    void seleccionarPagoExterno(String identificador);

}
