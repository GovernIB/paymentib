package es.caib.paymentib.core.api.exception;

/**
 *
 * Excepcion generada en el back.
 *
 */
@SuppressWarnings("serial")
public class ErrorBackException extends RuntimeException {

	/**
	 * Genera excepción ErrorBackException estableciendo un mensaje .
	 *
	 * @param message
	 *            Mensaje
	 */
	public ErrorBackException(final String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            Mensaje
	 * @param excep
	 *            Excepcion origen
	 */
	public ErrorBackException(final String message, final Throwable excep) {
		super(message, excep);
	}

}
