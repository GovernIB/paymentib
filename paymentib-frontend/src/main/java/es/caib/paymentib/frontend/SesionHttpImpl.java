package es.caib.paymentib.frontend;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean de sesión http que almacena info pago.
 *
 * @author Indra
 *
 */
@Component("sesionHttp")
@Scope(value = "session")
@SuppressWarnings("serial")
public final class SesionHttpImpl implements SesionHttp, Serializable {

    /** Idioma. */
    private String idioma = "es";

    /** Identificador pago. */
    private String identificador;

    /**
     * Método de acceso a idioma.
     *
     * @return idioma
     */
    @Override
    public String getIdioma() {
        return idioma;
    }

    /**
     * Método para establecer idioma.
     *
     * @param idioma
     *            idioma a establecer
     */
    @Override
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    @Override
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    @Override
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
