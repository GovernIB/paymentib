package es.caib.paymentib.backend.procesos;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.paymentib.core.api.service.SystemService;
import es.caib.paymentib.core.api.util.GeneradorId;

/**
 * Proceso que arranca proceso purga ficheros.
 */
@Component
public final class PurgaProcess {

    /** Id Servlet Context (instancia aplicacion). */
    public static final String SERVLET_CONTEXT_ID = "sistramit-SERVLET_CONTEXT_ID";

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private ServletContext servletContext;

    /**
     * Process.
     */
    @Scheduled(cron = "${procesos.purga.cron}")
    public void process() {
        log.debug("Proceso purgarFicheros");
        final String instancia = getIdServletContext();
        if (StringUtils.isNotBlank(instancia)) {
            if (systemService.isMaestro(instancia)) {
                log.debug("Es maestro. Lanza purga");
                systemService.purgar();
            } else {
                log.debug("No es maestro. No lanza purga");
            }
        } else {
            log.warn("No se ha podido obtener id instancia.");
        }
    }

    /**
     * Obtiene id instancia.
     *
     * @return id instancia
     */
    private String getIdServletContext() {
        String id = null;
        if (servletContext != null) {
            id = (String) servletContext.getAttribute(SERVLET_CONTEXT_ID);
            if (id == null) {
                id = GeneradorId.generarId();
                servletContext.setAttribute(SERVLET_CONTEXT_ID, id);
            }
        }
        return id;
    }

}