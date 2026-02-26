package es.caib.paymentib.core.service.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
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

    /** Directorio configuración. */
    private String directorioConf;

    /** Pasarelas con sus entidades de pago (key: idpasarela-idioma) */
    private Map<String, List<EntidadPago>> pasarelasEntidadesPago = new HashMap<>();

    @PostConstruct
    public void init() {
        // Carga propiedades
        final String pathProperties = System.getProperty("es.caib.paymentib.properties.path");
        propiedadesLocales = readPropertiesFileUTF8(pathProperties);
        // Obtiene directorio configuracion
        final File f = new File(System.getProperty("es.caib.paymentib.properties.path"));
        directorioConf = f.getParentFile().getAbsolutePath();
        // Inicializa pasarelas y entidades de pago
        String pasarelasStr = propiedadesLocales.getProperty("pasarelas");
        String [] pasarelas = pasarelasStr != null ? pasarelasStr.split(",") : new String[0];
        for (String pasarelaId : pasarelas) {
            IPasarelaPagoPlugin plugin = obtenerPluginPasarelaPago(pasarelaId.trim());
            pasarelasEntidadesPago.put(pasarelaId + "-" + TypeIdioma.CASTELLANO, plugin.obtenerEntidadesPagoElectronico(TypeIdioma.CASTELLANO));
            pasarelasEntidadesPago.put(pasarelaId + "-" + TypeIdioma.CATALAN, plugin.obtenerEntidadesPagoElectronico(TypeIdioma.CATALAN));
            pasarelasEntidadesPago.put(pasarelaId + "-" + TypeIdioma.INGLES, plugin.obtenerEntidadesPagoElectronico(TypeIdioma.INGLES));
        }
    }

    @Override
    public String obtenerDirectorioConfiguracion() {
        return directorioConf;
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

    @Override
    public List<EntidadPago> obtenerEntidadesPagoPasarela(
            String idPasarelaPago, TypeIdioma idioma) {
        return pasarelasEntidadesPago.get(idPasarelaPago + "-" + idioma);
    }

    // ----------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ----------------------------------------------------------------------

    private IPasarelaPagoPlugin createPlugin(String idPasarelaPago) {

        // No dejamos usar pasarela MOCK en PRODUCCION
        if ("MOCK".equals(idPasarelaPago) && "PRO".equalsIgnoreCase(readPropiedad("entorno"))) {
            throw new PluginErrorException("No se permite el uso de la pasarela MOCK en PRODUCCION.");
        }


        String classname = null;
        try {
            // Cargamos properties de la pasarela de pago
            Properties propsPasarela = readPropertiesFileUTF8(
                    obtenerDirectorioConfiguracion() + File.separator + idPasarelaPago + ".properties");
            // Classname del plugin
            classname = propsPasarela.getProperty("classname");
            // Propiedades específicas del plugin
            final Properties propsPlugin = new Properties();
            for (final Object key : propsPasarela.keySet()) {
                propsPlugin.put(IPasarelaPagoPlugin.PAGO_BASE_PROPERTY + key, propsPasarela.get(key));
            }

            final IPlugin plg = (IPlugin) PluginsManager
                    .instancePluginByClassName(classname,
                            IPasarelaPagoPlugin.PAGO_BASE_PROPERTY, propsPlugin);

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
     * Lee fichero de propiedades en UTF-8.
     * @param pathProperties ruta fichero propiedades
     * @return propiedades leidas
     */
    private Properties readPropertiesFileUTF8(String pathProperties) {
        try (FileInputStream fis = new FileInputStream(pathProperties);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8")) { // Specify UTF-8 encoding
            Properties props = props = new Properties();
            props.load(reader);
            return props;
        } catch (final IOException e) {
            throw new ConfiguracionException(e);
        }
    }

    /**
     * Convierte Properties a Map<String, String>
     * @param properties propiedades
     * @return mapa
     */
    public static Map<String, String> convertPropertiesToMap(Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.getProperty(key));
        }
        return map;
    }

}
