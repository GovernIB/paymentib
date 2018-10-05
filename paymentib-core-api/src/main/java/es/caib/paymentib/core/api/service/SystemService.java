package es.caib.paymentib.core.api.service;

public interface SystemService {

    /**
     * Realiza purga.
     *
     */
    void purgar();

    /**
     * Verifica si es maestro.
     *
     * @param instancia
     *            instancia
     * @return si es maestro
     */
    boolean isMaestro(String instancia);

}
