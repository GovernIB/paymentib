package es.caib.paymentib.core.api.exception;

/**
 * 
 * Excepcion generada en el front.
 * 
 */
@SuppressWarnings("serial")
public class ErrorFrontException extends RuntimeException {
	
  /**
   * Genera excepción FrontException estableciendo un mensaje .
   * 
   * @param message
   *          Mensaje
   */
  public ErrorFrontException(final String message) {
    super(message);
  }

  /**
   * Constructor.
   * 
   * @param message
   *          Mensaje
   * @param excep
   *          Excepcion origen
   */
  public ErrorFrontException(final String message, final Throwable excep) {
    super(message, excep);
  }

}
