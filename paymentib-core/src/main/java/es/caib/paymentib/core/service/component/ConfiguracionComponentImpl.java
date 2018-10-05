package es.caib.paymentib.core.service.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.stereotype.Component;

import es.caib.paymentib.core.api.exception.ConfiguracionException;
import es.caib.paymentib.core.api.exception.PluginErrorException;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

    /** Propiedades configuración especificadas en properties. */
    private Properties propiedadesLocales;

    @PostConstruct
    public void init() {
        final String pathProperties = System
                .getProperty("es.caib.paymentib.properties.path");
        try (FileInputStream fis = new FileInputStream(pathProperties);) {
            propiedadesLocales = new Properties();
            propiedadesLocales.load(fis);
        } catch (final IOException e) {
            throw new ConfiguracionException(e);
        }
    }

    @Override
    public String obtenerPropiedadConfiguracion(final String propiedad) {
        return readPropiedad(propiedad);
    }

    @Override
    public IPasarelaPagoPlugin obtenerPluginPasarelaPago(
            String idPasarelaPago) {
        return createPlugin(idPasarelaPago);
    }

    // ----------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ----------------------------------------------------------------------

    private IPasarelaPagoPlugin createPlugin(String idPasarelaPago) {

        String classname = null;
        try {

            classname = readPropiedad(
                    "pasarela." + idPasarelaPago + ".classname");

            final Map<String, String> propsPlugin = readPropiedades(
                    "pasarela." + idPasarelaPago + ".");
            final Properties prop = new Properties();
            for (final String key : propsPlugin.keySet()) {
                prop.put(IPasarelaPagoPlugin.PAGO_BASE_PROPERTY + key,
                        propsPlugin.get(key));
            }

            final IPlugin plg = (IPlugin) PluginsManager
                    .instancePluginByClassName(classname,
                            IPasarelaPagoPlugin.PAGO_BASE_PROPERTY, prop);

            if (plg == null) {
                throw new PluginErrorException(
                        "No se ha podido instanciar plugin de tipo "
                                + idPasarelaPago
                                + " , PluginManager devuelve nulo.");
            }

            return (IPasarelaPagoPlugin) plg;

        } catch (final Exception e) {
            throw new PluginErrorException("Error al instanciar plugin "
                    + idPasarelaPago + " con classname " + classname, e);
        }
    }

    /**
     * Lee propiedad.
     *
     * @param propiedad
     *            propiedad
     * @return valor propiedad (nulo si no existe)
     */
    private String readPropiedad(final String propiedad) {
        // Busca primero en propiedades locales
        final String prop = propiedadesLocales
                .getProperty(propiedad.toString());
        return prop;
    }

    /**
     * Lee propiedad.
     *
     * @param propiedad
     *            propiedad
     * @return valor propiedad (nulo si no existe)
     */
    private Map<String, String> readPropiedades(final String prefix) {
        // Busca primero en propiedades locales
        final Map<String, String> props = new HashMap<>();
        for (final Object key : propiedadesLocales.keySet()) {
            if (key.toString().startsWith(prefix)) {
                props.put(key.toString().substring(prefix.length()),
                        propiedadesLocales.getProperty(key.toString()));
            }
        }
        return props;
    }

}
