package es.caib.paymentib.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.paymentib.core.api.exception.ConfiguracionException;
import es.caib.paymentib.core.api.exception.FaltanDatosException;
import es.caib.paymentib.core.api.exception.MaxNumFilasException;
import es.caib.paymentib.core.api.exception.NoExisteSesionPagoException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.core.service.component.ConfiguracionComponent;
import es.caib.paymentib.core.service.repository.model.JPagoE;
import es.caib.paymentib.core.service.util.Constantes;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;

@Repository("pagoDao")
public class PagoDaoImpl implements PagoDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	public PagoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.paymentib.core.service.repository.dao.PagoDao#getAllByFiltro(
	 * java. lang.String)
	 */
	@Override
	public List<DatosSesionPago> getAllByFiltro(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha) {
		return listarPagos(filtro, fechaDesde, fechaHasta, tipoFecha);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.paymentib.core.service.repository.dao.PagoDao#getAll()
	 */
	@Override
	public List<DatosSesionPago> getAll() {
		return listarPagos(null, null, null, null);
	}

	@Override
	public String create(final String pasarelaId, final DatosPago datosPago, final String urlCallbackAppOrigen,
			final String tokenAcceso) {

		final JPagoE jo = new JPagoE();
		jo.setIdentificador(generarId());
		jo.setPasarelaId(pasarelaId);
		jo.setEstado(TypeEstadoPago.NO_INICIADO.toString());
		jo.setFechaCreacion(new Date());
		jo.setIdioma(datosPago.getIdioma().toString());
		jo.setAplicacionId(datosPago.getAplicacionId());
		jo.setEntidadId(datosPago.getEntidadId());
		jo.setOrganoId(datosPago.getOrganismoId());
		jo.setSujetoPasivoNif(datosPago.getSujetoPasivoNif());
		jo.setSujetoPasivoNombre(datosPago.getSujetoPasivoNombre());
		jo.setDetallePago(datosPago.getDetallePago());
		jo.setModelo(datosPago.getModelo());
		jo.setConcepto(datosPago.getConcepto());
		jo.setTasaId(datosPago.getTasaId());
		jo.setImporte(datosPago.getImporte());
		jo.setUrlCallbackOrigen(urlCallbackAppOrigen);
		jo.setToken(tokenAcceso);

		entityManager.persist(jo);

		return jo.getIdentificador();
	}

	@Override
	public void iniciar(final String identificador, final String localizador, final String token) {
		final JPagoE jp = getJPagoByIdentificador(identificador);
		jp.setEstado(TypeEstadoPago.DESCONOCIDO.toString());
		jp.setLocalizador(localizador);
		jp.setToken(token);
		entityManager.persist(jp);
	}

	@Override
	public void actualizarEstado(final String identificador, final EstadoPago ep) {
		final JPagoE jp = getJPagoByIdentificador(identificador);
		jp.setEstado(ep.getEstado().toString());
		jp.setCodigoErrorPasarela(ep.getCodigoErrorPasarela());
		jp.setMensajeErrorPasarela(StringUtils.substring(ep.getMensajeErrorPasarela(), 0, 500));
		jp.setFechaPago(ep.getFechaPago());
		entityManager.persist(jp);
	}

	@Override
	public DatosSesionPago getByToken(final String tokenSesion) {
		DatosSesionPago dp = null;

		// Recuperamos pago por token
		final Query query = entityManager.createQuery("SELECT p FROM JPagoE p WHERE p.token = :token");
		query.setParameter("token", tokenSesion);
		final List<JPagoE> results = query.getResultList();
		if (!results.isEmpty()) {
			final JPagoE jp = results.get(0);
			dp = jp.toModel();

			// Reseteamos token para evitar reusarlo
			jp.setToken(null);
			entityManager.persist(jp);

		}
		return dp;
	}

	@Override
	public DatosSesionPago getByIdentificador(final String identificador) {
		DatosSesionPago dp = null;
		final JPagoE jp = getJPagoByIdentificador(identificador);
		if (jp != null) {
			dp = jp.toModel();
		}
		return dp;
	}

	@SuppressWarnings("unchecked")
	private List<DatosSesionPago> listarPagos(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha) {
		final List<DatosSesionPago> listaPagos = new ArrayList<>();

		final String sqlSelect = "SELECT p FROM JPagoE p ";
		final String sqlSelectCount = "SELECT COUNT(p) FROM JPagoE p ";

		final StringBuilder sqlWhere = new StringBuilder();

		if (StringUtils.isNotBlank(filtro)) {
			sqlWhere.append(" (LOWER(p.aplicacionId) LIKE :filtro OR LOWER(p.concepto) LIKE :filtro")
					.append(" OR LOWER(p.detallePago) LIKE :filtro OR LOWER(p.entidadId) LIKE :filtro")
					.append(" OR LOWER(p.estado) LIKE :filtro OR LOWER(p.identificador) LIKE :filtro")
					.append(" OR LOWER(p.localizador) LIKE :filtro OR LOWER(p.sujetoPasivoNif) LIKE :filtro")
					.append(" OR LOWER(p.sujetoPasivoNombre) LIKE :filtro OR LOWER(p.organoId) LIKE :filtro")
					.append(" OR LOWER(p.pasarelaId) LIKE :filtro OR LOWER(p.tasaId) LIKE :filtro ")
					.append(" OR LOWER(p.modelo) LIKE :filtro OR LOWER(p.urlCallbackOrigen) LIKE :filtro")
					.append(" OR LOWER(p.usuarioConfirmacion) LIKE :filtro) ");
		}

		if ((fechaDesde != null || fechaHasta != null) && tipoFecha != null) {

			String sqlFecha = null;
			if (TypeFiltroFecha.CREACION.equals(tipoFecha)) {
				sqlFecha = "fechaCreacion";
			} else if (TypeFiltroFecha.PAGO.equals(tipoFecha)) {
				sqlFecha = "fechaPago";
			}

			if (fechaDesde != null) {
				if (sqlWhere.length() > 0) {
					sqlWhere.append(" AND ");
				}

				sqlWhere.append(" p.").append(sqlFecha).append(" >= :fechaDesde ");

			}
			if (fechaHasta != null) {
				if (sqlWhere.length() > 0) {
					sqlWhere.append(" AND ");
				}

				sqlWhere.append(" p.").append(sqlFecha).append(" <= :fechaHasta ");
			}

		}

		if (sqlWhere.length() > 0) {
			sqlWhere.insert(0, " WHERE");
		}

		final String sqlOrder = " ORDER BY p.codigo";

		final Query queryCount = entityManager.createQuery(sqlSelectCount + sqlWhere);

		if (StringUtils.isNotBlank(filtro)) {
			queryCount.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if ((fechaDesde != null || fechaHasta != null) && tipoFecha != null) {
			if (fechaDesde != null) {
				queryCount.setParameter("fechaDesde", fechaDesde);
			}
			if (fechaHasta != null) {
				queryCount.setParameter("fechaHasta", fechaHasta);
			}
		}

		final Long nfilas = (Long) queryCount.getSingleResult();

		if (nfilas > Constantes.MAX_NUM_PAGOS) {
			throw new MaxNumFilasException(String.valueOf(nfilas) + " recuperadas");
		}

		final Query query = entityManager.createQuery(sqlSelect + sqlWhere + sqlOrder);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if ((fechaDesde != null || fechaHasta != null) && tipoFecha != null) {
			if (fechaDesde != null) {
				query.setParameter("fechaDesde", fechaDesde);
			}
			if (fechaHasta != null) {
				query.setParameter("fechaHasta", fechaHasta);
			}
		}

		final List<JPagoE> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JPagoE jPago : results) {
				final DatosSesionPago pago = jPago.toModel();
				listaPagos.add(pago);
			}
		}

		return listaPagos;
	}

	private JPagoE getJPagoByIdentificador(final String identificador) {
		JPagoE jp = null;
		// Recuperamos pago por token
		final Query query = entityManager.createQuery("SELECT p FROM JPagoE p WHERE p.identificador = :identificador");
		query.setParameter("identificador", identificador);
		final List<JPagoE> results = query.getResultList();
		if (!results.isEmpty()) {
			jp = results.get(0);
		}
		return jp;
	}

	@Override
	public DatosSesionPago getByCodigo(final Long codigo) {
		DatosSesionPago pago = null;

		if (codigo == null) {
			throw new FaltanDatosException("Falta el codigo");
		}

		final JPagoE jPagoE = entityManager.find(JPagoE.class, codigo);

		if (jPagoE == null) {
			throw new NoExisteSesionPagoException(String.valueOf(codigo));
		} else {
			pago = jPagoE.toModel();
		}

		return pago;
	}

	@Override
	public void purgar(final int dias) {

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, dias * -1);
		final Date fcLimite = cal.getTime();

		final String sql = "DELETE FROM JPagoE t WHERE t.fechaCreacion < :fecha";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("fecha", fcLimite);
		query.executeUpdate();

	}

	@Override
	public void confirmarPago(final String identificador, final Date fechaPago, final String usuario) {
		final JPagoE jp = getJPagoByIdentificador(identificador);
		if (jp != null) {
			jp.setEstado(TypeEstadoPago.PAGADO.toString());
			jp.setFechaPago(fechaPago);
			jp.setUsuarioConfirmacion(usuario);
			entityManager.merge(jp);
		}
	}

	/**
	 * Genera id pago: identiticador instalacion (3 letras) + 9 numeros a partir
	 * sql.
	 *
	 * @return id pago
	 */
	private String generarId() {

		final String prefix = config.obtenerPropiedadConfiguracion("idPago.prefijo");
		if (StringUtils.isBlank(prefix) || prefix.length() != 3) {
			throw new ConfiguracionException("La propiedad 'idPago.prefijo' debe tener 3 letras");
		}

		final String sqlSequence = config.obtenerPropiedadConfiguracion("idPago.sql");
		final Query query = entityManager.createNativeQuery(sqlSequence);
		final String numero = query.getSingleResult().toString();

		final String id = prefix + StringUtils.leftPad(numero, 9, "0");

		return id;
	}

}
