package es.caib.paymentib.plugins.api;

/**
 * Tipo estado pago.
 *
 * @author Indra
 *
 */
public enum TypeEstadoPago {

	NO_INICIADO("v"),
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
	private TypeEstadoPago(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeEstadoPago fromString(final String text) {
		TypeEstadoPago respuesta = null;
		if (text != null) {
			for (final TypeEstadoPago b : TypeEstadoPago.values()) {
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
