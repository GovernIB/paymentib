package es.caib.paymentib.plugins.api;

/**
 * Tipo validación pago.
 *
 * @author Indra
 *
 */
public enum TypeModoValidacion {

	/** Validación por llamada de verificación. **/
	VERIFICACION("v"),
	/** Confirmación manual. **/
	CONFIRMACION_MANUAL("c");

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
	private TypeModoValidacion(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeModoValidacion fromString(final String text) {
		TypeModoValidacion respuesta = null;
		if (text != null) {
			for (final TypeModoValidacion b : TypeModoValidacion.values()) {
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
