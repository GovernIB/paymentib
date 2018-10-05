package es.caib.paymentib.plugins.api;

/**
 * Tipo idioma.
 *
 * @author Indra
 *
 */
public enum TypeIdioma {

    /** Castellano **/
    CASTELLANO("es"),
    /** Catalán **/
    CATALAN("ca"),
    /** Inglés. **/
    INGLES("en");

    /**
     * Ambito nombre;
     */
    private String valor;

    /**
     * Constructor.
     *
     * @param pValor
     *            Role name
     */
    private TypeIdioma(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeSiNo
     */
    public static TypeIdioma fromString(final String text) {
        TypeIdioma respuesta = null;
        if (text != null) {
            for (final TypeIdioma b : TypeIdioma.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

    @Override
    public String toString() {
        return valor;
    }

}
