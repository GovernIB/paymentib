package es.caib.paymentib.plugins.api;

/**
 * Excepción al invocar la pasarela de pago.
 *
 * @author Indra
 *
 */
public class PasarelaPagoException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasarelaPagoException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PasarelaPagoException(String arg0) {
        super(arg0);
    }

    public PasarelaPagoException(Throwable arg0) {
        super(arg0);
    }

}
