package es.caib.paymentib.frontend;

/**
 * Bean de sesión http que almacena info pago.
 *
 * @author Indra
 *
 */
public interface SesionHttp {

    /**
     * Método para guardar idioma.
     *
     * @param idioma
     *            Idioma
     */
    void setIdioma(final String idioma);

    /**
     * Método para obtener idioma.
     *
     * @return Idioma
     */
    String getIdioma();

    /**
     * Establece identificador pago.
     *
     * @param identificador
     *            identificador
     */
    void setIdentificador(final String identificador);

    /**
     * Obtiene identificador pago.
     * 
     * @return identificador
     */
    String getIdentificador();

}
