package es.caib.paymentib.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Proceso que arranca proceso purga.
 */
@Service
public final class PurgaProcess {

    /** Log. */
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

    /** Tarea asincrona. */
    @Autowired
    private AsyncTaskIntf purgaAsyncTask;

    /** Process. */
    @Scheduled(cron = "${procesos.purga.cron}")
    public void process() {
        purgaAsyncTask.doTask();
    }

}
