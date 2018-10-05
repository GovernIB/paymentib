package es.caib.paymentib.core.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.paymentib.core.api.service.SystemService;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SystemServiceBean implements SystemService {

    /** Security service. */
    @Autowired
    SystemService systemService;

    @Override
    @PermitAll
    public void purgar() {
        systemService.purgar();
    }

    @Override
    @PermitAll
    public boolean isMaestro(String instancia) {
        return systemService.isMaestro(instancia);
    }

}
