package es.caib.paymentib.core.ejb;

import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.paymentib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PagoFrontServiceBean implements PagoFrontService {

	@Autowired
	private PagoFrontService service;

	@Override
	@PermitAll
	public String obtenerUrlFrontal() {
		return service.obtenerUrlFrontal();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public DatosSesionPago crearPagoElectronico(final String pasarelaId, final DatosPago datosPago,
			final String urlCallbackAppOrigen) {
		return service.crearPagoElectronico(pasarelaId, datosPago, urlCallbackAppOrigen);
	}

	@Override
	@PermitAll
	public DatosSesionPago recuperarPagoElectronico(final String tokenSesion) {
		return service.recuperarPagoElectronico(tokenSesion);
	}

	@Override
	@PermitAll
	public UrlRedireccionPasarelaPago iniciarPagoElectronico(final String identificador, final String entidadPagoId,
			final String urlCallbackCompPagos) {
		return service.iniciarPagoElectronico(identificador, entidadPagoId, urlCallbackCompPagos);
	}

	@Override
	@PermitAll
	public List<EntidadPago> obtenerEntidadesPagoElectronico(final String identificador) {
		return service.obtenerEntidadesPagoElectronico(identificador);
	}

	@Override
	@PermitAll
	public EstadoPago verificarRetornoPagoElectronico(final String identificador,
			final Map<String, String[]> parametrosRetorno) {
		return service.verificarRetornoPagoElectronico(identificador, parametrosRetorno);
	}

	@Override
	@PermitAll
	public EstadoPago verificarPagoElectronico(final String identificador) {
		return service.verificarPagoElectronico(identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public byte[] obtenerJustificantePagoElectronico(final String identificador) {
		return service.obtenerJustificantePagoElectronico(identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public int consultaTasa(final String pasarelaId, final String idTasa) {
		return service.consultaTasa(pasarelaId, idTasa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public byte[] obtenerCartaPagoPresencial(final String pasarelaId, final DatosPago datosPago) {
		return service.obtenerCartaPagoPresencial(pasarelaId, datosPago);
	}

	@Override
	public boolean permitePagoPresencial(final String pasarelaId) {
		return service.permitePagoPresencial(pasarelaId);
	}

}
