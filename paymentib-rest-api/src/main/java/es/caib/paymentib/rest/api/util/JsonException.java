package es.caib.paymentib.rest.api.util;

@SuppressWarnings("serial")
public class JsonException extends Exception {

    public JsonException() {
        super();
    }

    public JsonException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public JsonException(String arg0) {
        super(arg0);
    }

    public JsonException(Throwable arg0) {
        super(arg0);
    }

}
