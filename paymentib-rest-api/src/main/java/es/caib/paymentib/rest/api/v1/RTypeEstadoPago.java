package es.caib.paymentib.rest.api.v1;

/**
 * Tipo estado pago.
 *
 * @author Indra
 *
 */
public enum RTypeEstadoPago {

    /** No pagado. **/
    NO_PAGADO("n"),
    /** Pagado. **/
    PAGADO("p"),
    /** Desconocido. **/
    DESCONOCIDO("x");

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
    private RTypeEstadoPago(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeSiNo
     */
    public static RTypeEstadoPago fromString(final String text) {
        RTypeEstadoPago respuesta = null;
        if (text != null) {
            for (final RTypeEstadoPago b : RTypeEstadoPago.values()) {
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
