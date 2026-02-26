package es.caib.paymentib.frontend.literales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Componente para recuperar literales de la capa web.
 *
 * @author Indra
 *
 */
@Component("literalesFront")
public final class LiteralesFrontImpl implements LiteralesFront {

	/** Bundle con los literales. */
	@Autowired
	private MessageSource messageSource;

	@Override
	public Properties getLiteralesSeccion(final String pSeccion, final String pIdioma) {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale(pIdioma),
				new UTF8Control());
		final Properties properties = new Properties();
		final Enumeration<String> keysLiterales = resourceBundle.getKeys();
		while (keysLiterales.hasMoreElements()) {
			final String key = keysLiterales.nextElement();
			final String literal = resourceBundle.getString(key);
			if (key.startsWith(pSeccion)) {

				properties.setProperty(key.substring(key.indexOf(".") + 1, key.length()), literal);
			}
		}
		return properties;
	}

	@Override
	public String getLiteral(final String pCodigo, final String pIdioma, Object[] pParametros) {
		String res = null;
		try {
			res = messageSource.getMessage(pCodigo, pParametros, new Locale(pIdioma));
		} catch (final NoSuchMessageException ex) {
			res = "¿¿" + pCodigo + "??";
		}
		return res;
	}

	@Override
	public String getLiteral(final String pCodigo, final String pIdioma) {
		return getLiteral(pCodigo, pIdioma, null);
	}


}
