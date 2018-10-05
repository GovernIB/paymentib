package es.caib.paymentib.core.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.paymentib.core.api.service.SystemService;
import es.caib.paymentib.core.interceptor.NegocioInterceptor;
import es.caib.paymentib.core.service.component.ConfiguracionComponent;
import es.caib.paymentib.core.service.repository.dao.PagoDao;
import es.caib.paymentib.core.service.repository.dao.ProcesoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(SystemServiceImpl.class);

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracion;

    /** Procesos DAO. */
    @Autowired
    private ProcesoDao procesosDAO;

    /** Pago DAO. */
    @Autowired
    private PagoDao pagoDao;

    @Override
    @NegocioInterceptor
    public void purgar() {
        final String diasStr = configuracion
                .obtenerPropiedadConfiguracion("procesos.purga.dias");
        int dias = 365;
        if (!StringUtils.isBlank(diasStr)) {
            try {
                dias = Integer.parseInt(diasStr);
            } catch (final NumberFormatException nfe) {
                dias = 365;
            }
        }
        pagoDao.purgar(dias);
    }

    @Override
    @NegocioInterceptor
    public boolean isMaestro(String instancia) {
        return procesosDAO.verificarMaestro(instancia);
    }

}
