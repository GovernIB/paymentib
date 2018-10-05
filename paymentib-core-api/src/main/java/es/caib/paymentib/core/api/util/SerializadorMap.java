package es.caib.paymentib.core.api.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Serializa un Map<String,Strin>.
 *
 * @author Indra
 *
 */
public final class SerializadorMap {

	/**
	 * Constructor.
	 */
	private SerializadorMap() {
		super();
	}

	/**
	 * Separador de propiedades.
	 */
	public static final String SEPARADOR = "#@#";

	/**
	 * Numero 1.
	 */
	private static final int UNO = 1;

	/**
	 * Numero -1.
	 */
	private static final int UNO_NEGATIVO = -1;

	/**
	 * Serializa map de strings a un string.
	 *
	 * @param map
	 *            Map de strings
	 * @return cadena
	 */
	public static String serializar(final Map<String, String> map) {
		String res = null;
		if (map != null) {
			final StringBuilder detalles = new StringBuilder("");
			for (final Map.Entry<String, String> propiedad : map.entrySet()) {
				detalles.append(propiedad.getKey());
				detalles.append("=");
				detalles.append(propiedad.getValue());
				detalles.append(SEPARADOR);
			}
			res = detalles.toString();
		}
		return res;
	}

	/**
	 * Deserializa cadena en un map de strings.
	 *
	 * @param cadena
	 *            Cadena
	 * @return Map de strings
	 */
	public static Map<String, String> deserializar(final String cadena) {
		Map<String, String> resultado = null;
		if (cadena != null) {
			resultado = new HashMap<>();
			final String[] props = cadena.split(SEPARADOR);
			for (final String prop : props) {
				final int pos = prop.indexOf('=');
				if (pos != UNO_NEGATIVO) {
					final String key = StringUtils.substring(prop, 0, pos);
					final String value = StringUtils.substring(prop, pos + UNO);
					resultado.put(key, value);
				} else {
					resultado.put(prop, "");
				}
			}
		}
		return resultado;
	}

}
