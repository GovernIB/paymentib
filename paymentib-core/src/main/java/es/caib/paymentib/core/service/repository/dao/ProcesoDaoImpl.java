package es.caib.paymentib.core.service.repository.dao;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.paymentib.core.api.exception.ProcesoConfiguracionException;
import es.caib.paymentib.core.service.repository.model.JProceso;

/**
 * Proceso DAO.
 */
@Repository("procesoDao")
public class ProcesoDaoImpl implements ProcesoDao {

    /** Tiempo máximo inactivo que puede estar el maestro. */
    private static final int MINUTOS_MAX_INACTIVO = 15;

    /** Id maestro. */
    private static final String ID_MAESTRO = "MAESTRO";

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean verificarMaestro(final String instanciaId) {

        boolean res = false;
        boolean tomarControl = false;
        final Date fechaActual = new Date();

        // Recuperamos info actual (debe existir siempre)
        final JProceso jProceso = entityManager.find(JProceso.class,
                ID_MAESTRO);
        if (jProceso == null) {
            throw new ProcesoConfiguracionException(
                    "No existe fila en tabla de procesos con id " + ID_MAESTRO);
        }

        // Verifica si esta configurado como maestro
        // - Instancia actual
        if (jProceso.getInstancia().equals(instanciaId)) {
            // Intentamos renovar
            tomarControl = true;
        } else {
            // Si se ha sobrepasado el tiempo sin que la instancia configurada
            // como maestro
            // este activa, se intenta tomar control
            final Calendar cal = Calendar.getInstance();
            cal.setTime(jProceso.getFecha());
            cal.add(Calendar.MINUTE, MINUTOS_MAX_INACTIVO);
            final Date fechaLimite = cal.getTime();
            tomarControl = fechaLimite.before(fechaActual);
        }

        // Toma de control
        if (tomarControl) {
            final String sql = "UPDATE JProceso p SET p.instancia = :instanciaActual, p.fecha = :fechaActual WHERE p.fecha = :fechaOld AND p.instancia = :instanciaOld";
            final Query query = entityManager.createQuery(sql);
            query.setParameter("instanciaActual", instanciaId);
            query.setParameter("instanciaOld", jProceso.getInstancia());
            query.setParameter("fechaActual", fechaActual);
            query.setParameter("fechaOld", jProceso.getFecha());
            final int update = query.executeUpdate();
            res = (update == 1);
        }

        return res;
    }

}
