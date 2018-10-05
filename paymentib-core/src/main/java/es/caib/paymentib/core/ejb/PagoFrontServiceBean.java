package es.caib.paymentib.core.ejb;

import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
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
    @PermitAll
    public DatosSesionPago crearPagoElectronico(String pasarelaId,
            DatosPago datosPago, String urlCallbackAppOrigen) {
        return service.crearPagoElectronico(pasarelaId, datosPago,
                urlCallbackAppOrigen);
    }

    @Override
    @PermitAll
    public DatosSesionPago recuperarPagoElectronico(String tokenSesion) {
        return service.recuperarPagoElectronico(tokenSesion);
    }

    @Override
    @PermitAll
    public UrlRedireccionPasarelaPago iniciarPagoElectronico(
            String identificador, String entidadPagoId,
            String urlCallbackCompPagos) {
        return service.iniciarPagoElectronico(identificador, entidadPagoId,
                urlCallbackCompPagos);
    }

    @Override
    @PermitAll
    public List<EntidadPago> obtenerEntidadesPagoElectronico(
            String identificador) {
        return service.obtenerEntidadesPagoElectronico(identificador);
    }

    @Override
    @PermitAll
    public TypeEstadoPago verificarRetornoPagoElectronico(String identificador,
            Map<String, String[]> parametrosRetorno) {
        return service.verificarRetornoPagoElectronico(identificador,
                parametrosRetorno);
    }

    @Override
    @PermitAll
    public TypeEstadoPago verificarPagoElectronico(String identificador) {
        return service.verificarPagoElectronico(identificador);
    }

    @Override
    @PermitAll
    public byte[] obtenerJustificantePagoElectronico(String identificador) {
        return service.obtenerJustificantePagoElectronico(identificador);
    }

    @Override
    @PermitAll
    public int consultaTasa(String pasarelaId, String idTasa) {
        return service.consultaTasa(pasarelaId, idTasa);
    }

    @Override
    @PermitAll
    public byte[] obtenerCartaPagoPresencial(String pasarelaId,
            DatosPago datosPago) {
        return service.obtenerCartaPagoPresencial(pasarelaId, datosPago);
    }

}
