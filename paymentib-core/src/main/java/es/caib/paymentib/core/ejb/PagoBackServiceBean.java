package es.caib.paymentib.core.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.paymentib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeFiltroFecha;
import es.caib.paymentib.core.api.service.PagoBackService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PagoBackServiceBean implements PagoBackService {

	@Autowired
	private PagoBackService service;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.CONSULTA })
	public List<DatosSesionPago> listaPagos(final String filtro, final Date fechaDesde, final Date fechaHasta,
			final TypeFiltroFecha tipoFecha, final String filtroClaveTramitacion, final String filtroTramite, final Integer filtroVersion,
			final String filtroPasarela, final String filtroEntidad, final String filtroAplicacion, final String filtroLocATIB) {
		return service.listaPagos(filtro, fechaDesde, fechaHasta, tipoFecha, filtroClaveTramitacion, filtroTramite,
									filtroVersion, filtroPasarela, filtroEntidad, filtroAplicacion, filtroLocATIB);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.CONSULTA })
	public List<String> listaPasarelas() {
		return service.listaPasarelas();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.CONSULTA })
	public List<String> listaEntidades() {
		return service.listaEntidades();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.CONSULTA })
	public List<String> listaAplicaciones() {
		return service.listaAplicaciones();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.CONSULTA })
	public DatosSesionPago getPagoByCodigo(final Long codigo) {
		return service.getPagoByCodigo(codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void confirmarPago(final String identificador, final Date fechaPago, final String usuarioConfirmacion) {
		service.confirmarPago(identificador, fechaPago, usuarioConfirmacion);
	}

}
