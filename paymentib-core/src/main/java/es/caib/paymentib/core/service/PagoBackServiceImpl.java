package es.caib.paymentib.core.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeModoValidacion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
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
public class PagoBackServiceImpl implements PagoBackService {

	/** Log. */
	private final Logger LOG = LoggerFactory.getLogger(PagoBackServiceImpl.class);

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent config;

	/** DAO Entidad. */
	@Autowired
	private PagoDao pagoDao;

	@Override
	@NegocioInterceptor
	public String obtenerLogoBack() {
		return config.obtenerPropiedadConfiguracion("back.logo");
	}

	@Override
	@NegocioInterceptor
	public List<EntidadPago> obtenerEntidadesPagoPasarela(String idPasarelaPago, TypeIdioma idioma) {
		return config.obtenerEntidadesPagoPasarela(idPasarelaPago, idioma);
	}

	@Override
	@NegocioInterceptor
	public List<DatosSesionPago> listaPagos(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha, final String filtroClaveTramitacion, final String filtroTramite, final Integer filtroVersion,
			final String filtroPasarela, final String filtroEntidad, final String filtroAplicacion, final String filtroLocATIB, final String filtroMetodosPago,
			final String filtroIdentificador, final TypeEstadoPago filtroEstado, final Double filtroImporte,
			final String filtroNIF, final String filtroNombre, final Integer filtroUnidades) {
		return pagoDao.getAllByFiltro(filtro, fechaDesde, fechaHasta, tipoFecha, filtroClaveTramitacion, filtroTramite, filtroVersion, filtroPasarela, filtroEntidad, filtroAplicacion, filtroLocATIB, filtroMetodosPago, filtroIdentificador, filtroEstado, filtroImporte, filtroNIF, filtroNombre, filtroUnidades);
	}

	@Override
	@NegocioInterceptor
	public List<String> listaPasarelas() {
		return pagoDao.getPasarelas();
	}

	@Override
	@NegocioInterceptor
	public List<String> listaEntidades() {
		return pagoDao.getEntidades();
	}

	@Override
	@NegocioInterceptor
	public List<String> listaAplicaciones() {
		return pagoDao.getAplicaciones();
	}

	@Override
	@NegocioInterceptor
	public List<String> listaMetodosPago() {
		return pagoDao.getMetodosPago();
	}

	@Override
	@NegocioInterceptor
	public DatosSesionPago getPagoByCodigo(final Long codigo) {
		return pagoDao.getByCodigo(codigo);
	}

	@Override
	@NegocioInterceptor
	public DatosSesionPago getPagoByIdentificador(final String identificador) {
		return pagoDao.getByIdentificador(identificador);
	}

	@Override
	@NegocioInterceptor
	public void confirmarPago(final String identificador, final Date fechaPago, final String usuarioConfirmacion) {
		pagoDao.confirmarPago(identificador, fechaPago, usuarioConfirmacion);
	}

	@Override
	@NegocioInterceptor
	public TypeModoValidacion obtenerModoValidacionPasarela(final String pasarelaId) {
		// Crea plugin pago
		final IPasarelaPagoPlugin plgPago = config.obtenerPluginPasarelaPago(pasarelaId);
		// Consulta modo validación
		return plgPago.obtenerModoValidacion();
	}

	@Override
	@NegocioInterceptor
	public String obtenerDirectorioAyudaExterna() {
		return StringUtils.defaultString(config.obtenerPropiedadConfiguracion("ayuda.paymentib.path"), null);
	}

	@Override
	@NegocioInterceptor
	public String obtenerFiltroInicial(){
		return config.obtenerPropiedadConfiguracion("filtroInicial");
	}


}
