package es.caib.paymentib.core.service.repository.dao;

public interface ProcesoDao {

    /**
     * Verifica si la instancia actual es maestro. En caso de que no haya
     * maestro o se haya cumplido el plazo, se intenta establecer como maestro.
     *
     * @param instanciaId
     *            id instancia
     * @return true si es maestro
     */
    boolean verificarMaestro(String instanciaId);

}