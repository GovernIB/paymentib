package es.caib.paymentib.core.service.component;

import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;

/**
 * Componente para acceder a configuracion.
 *
 * @author Indra
 *
 */
public interface ConfiguracionComponent {

    /**
     * Obtiene configuración.
     *
     * @param propiedad
     *            Propiedad configuración
     *
     * @return configuración
     */
    String obtenerPropiedadConfiguracion(String propiedad);

    /**
     * Obtiene tipo plugin pasarela pago.
     *
     * @param idPasarelaPago
     *            idPasarelaPago
     * @return Plugin
     */
    IPasarelaPagoPlugin obtenerPluginPasarelaPago(String idPasarelaPago);

    /**
     * Obtiene directorio de configuración.
     * 
     * @return directorio de configuración
     */
    String obtenerDirectorioConfiguracion();

}