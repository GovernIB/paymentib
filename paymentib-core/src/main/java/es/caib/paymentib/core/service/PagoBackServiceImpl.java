package es.caib.paymentib.core.service;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.core.api.service.PagoBackService;
import es.caib.paymentib.core.interceptor.NegocioInterceptor;
import es.caib.paymentib.core.service.component.ConfiguracionComponent;
import es.caib.paymentib.core.service.repository.dao.PagoDao;

/**
 * Implementación PagoBackService.
 *
 * @author Indra
 */
@Service
@Transactional
public final class PagoBackServiceImpl implements PagoBackService {

	/** Log. */
	private final org.slf4j.Logger LOG = LoggerFactory.getLogger(PagoBackServiceImpl.class);

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	/** DAO Entidad. */
	@Autowired
	private PagoDao pagoDao;

	@Override
	@NegocioInterceptor
	public List<DatosSesionPago> listaPagos(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha, final String filtroClaveTramitacion, final String filtroTramite, final Integer filtroVersion) {
		return pagoDao.getAllByFiltro(filtro, fechaDesde, fechaHasta, tipoFecha, filtroClaveTramitacion, filtroTramite, filtroVersion);
	}

	@Override
	@NegocioInterceptor
	public DatosSesionPago getPagoByCodigo(final Long codigo) {
		return pagoDao.getByCodigo(codigo);
	}

	@Override
	@NegocioInterceptor
	public void confirmarPago(final String identificador, final Date fechaPago, final String usuarioConfirmacion) {
		pagoDao.confirmarPago(identificador, fechaPago, usuarioConfirmacion);
	}

}
