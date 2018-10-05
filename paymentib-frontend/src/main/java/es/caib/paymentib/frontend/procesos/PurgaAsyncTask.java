package es.caib.paymentib.frontend.procesos;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * Producer Siraj Task.
 */
@Component
public class PurgaAsyncTask implements AsyncTaskIntf {

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(PurgaAsyncTask.class);


    @Override
    @Async
    public final void doTask() {
        log.info("PurgaAsyncTask: inicio");
        // claveService.purgar();
        log.info("PurgaAsyncTask: fin");
    }

}
