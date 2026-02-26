package es.caib.paymentib.frontend.literales;

import java.util.Properties;

/**
 * Componente para recuperar literales.
 * @author Indra
 *
 */
public interface LiteralesFront {


    /**
     * Obtiene literales por sección.
     *
     * @param seccion
     *            Parámetro seccion
     * @param idioma
     *            Idioma
     * @return Literal
     */
    Properties getLiteralesSeccion(String seccion, String idioma);

    /**
     * Obtiene literal.
     * @param codigo Código del literal.
     * @param idioma Idioma.
     * @param parametros Parámetros del literal.
     * @return Literal.
     */
    String getLiteral(String codigo, String idioma, Object[] parametros);

    /**
     * Obtiene literal.
     * @param codigo Código del literal.
     * @param idioma Idioma.
     * @return Literal.
     */
    String getLiteral(String codigo, String idioma);


}
