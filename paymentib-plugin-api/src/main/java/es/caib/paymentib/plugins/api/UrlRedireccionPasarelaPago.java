package es.caib.paymentib.plugins.api;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Url de redirección a pasarela de pagos.
 *
 * @author Indra
 *
 */
public class UrlRedireccionPasarelaPago {

    /** Localizador pago en la pasarela. */
    private String localizador;

    /**
     * Url.
     */
    private String url;

    /**
     * Parametros post.
     */
    private final Map<String, String> parametersPost = new LinkedHashMap<>();

    /**
     * Método de acceso a url.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Método para establecer url.
     *
     * @param url
     *            url a establecer
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Método de acceso a parametersPost.
     *
     * @return parametersPost
     */
    public Map<String, String> getParametersPost() {
        return parametersPost;
    }

    /**
     * Método de acceso a localizador.
     *
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Método para establecer localizador.
     *
     * @param localizador
     *            localizador a establecer
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

}
