package es.caib.paymentib.core.api.model.types;

/**
 * Enum para indicar el tipo de entorno.
 *
 * @author Indra
 *
 */
public enum TypeEntorno {
	/**
	 * Desarrollo.
	 */
	DESARROLLO("des"),
	/**
	 * Preproduccion
	 */
	PREPRODUCCION("pre"),
	/**
	 * Produccion
	 */
	PRODUCCION("pro");

	/** Valor. **/
	private String valor;

	/** Constructor. **/
	private TypeEntorno(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeEntorno fromString(final String text) {
		TypeEntorno respuesta = null;
		if (text != null) {
			for (final TypeEntorno b : TypeEntorno.values()) {
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
