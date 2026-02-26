package es.caib.paymentib.plugins.api;

/**
 * Enum para indicar resultado validación pago externo.
 *
 * @author Indra
 *
 */
public enum TypeValidacionPagoExterno {

	// -- Estados posibles retornados por pasarela
	/** No válido o no está pagado ("NV").*/
	NO_VALIDO("NV"),
	/** No coincide fecha de pago. */
	FECHA_NO_COINCIDE("FC"),
	/** No coincide importe. */
	IMPORTE_NO_COINCIDE("IM"),
	/** No coincide tasa. */
	TASA_NO_COINCIDE("TS"),
	/** Correcto (OK).*/
	VERIFICADO("OK"),

	// -- Estados controlados por proceso verificación
	/** Existe localizador coincidente (LD).*/
	LOCALIZADOR_DUPLICADO("LD"),
	/** No se ha podido verificar (KO).*/
	NO_VERIFICADO("KO");


	/** Valor. **/
	private String valor;

	/** Constructor. **/
	private TypeValidacionPagoExterno(final String iValor) {
		this.valor = iValor;
	}


	/**
	 * Convierte un string en enumerado.
	 * @param text tipo
	 * @return TypeValidacionPagoExterno
	 */
	public static TypeValidacionPagoExterno fromString(final String text) {
		TypeValidacionPagoExterno respuesta = null;
		if (text != null) {
			for (final TypeValidacionPagoExterno b : TypeValidacionPagoExterno.values()) {
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
