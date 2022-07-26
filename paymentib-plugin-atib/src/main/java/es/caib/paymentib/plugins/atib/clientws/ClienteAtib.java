package es.caib.paymentib.plugins.atib.clientws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import es.caib.paymentib.plugins.atib.clientws.cxf.ArrayOfGuid;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuesta046;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuestaGetUrlPago;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosTasa046;
import es.caib.paymentib.plugins.atib.clientws.cxf.ServiceTasa;
import es.caib.paymentib.plugins.atib.clientws.cxf.ServiceTasaSoap;
import es.caib.paymentib.plugins.atib.clientws.cxf.UsuariosWebServices;

public class ClienteAtib {

    /**
     * Cliente del servicio
     */
    private final ServiceTasaSoap cliente;
    private final UsuariosWebServices cabecera;

    /**
     * Constructor de la clase retorna una excepcion si no ha sido posible
     * inicializar el cliente
     *
     * @param url
     *            url del webservice
     * @throws MalformedURLException
     */
    public ClienteAtib(String url, String user, String password)
            throws Exception {
        this.cabecera = getUsuario(user, password);
        this.cliente = getCliente(url);
        if (this.cliente == null) {
            throw new Exception(
                    "No ha sido posible inicializar el ClienteAtib");
        }

    }

    /**
     * Obtiene el cliente soap.
     *
     * @param url
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    private ServiceTasaSoap getCliente(String url)
            throws NumberFormatException, Exception {
        final ServiceTasa servicio = new ServiceTasa();
        final ServiceTasaSoap serviceTasaSoap = servicio.getServiceTasaSoap();
        final BindingProvider provider = (BindingProvider) serviceTasaSoap;
        provider.getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        final Client client = ClientProxy.getClient(serviceTasaSoap);
        final HTTPConduit conduit = (HTTPConduit) client.getConduit();

        // Vemos si hay que pasar por proxy
        final String proxyHost = System.getProperty("http.proxyHost");
        if (proxyHost != null && !"".equals(proxyHost)) {
            if (!validateNonProxyHosts(url)) {
                final HTTPClientPolicy policy = conduit.getClient();
                policy.setProxyServer(proxyHost);
                policy.setProxyServerPort(
                        Integer.parseInt(System.getProperty("http.proxyPort")));

                conduit.getProxyAuthorization()
                        .setUserName(System.getProperty("http.proxyUser"));
                conduit.getProxyAuthorization()
                        .setPassword(System.getProperty("http.proxyPassword"));
            }
        }

        return serviceTasaSoap;

    }

    /**
     * Inicio del trámite con un String del XML en base64. Devuelve un registro
     * de tipo DATOSRESPUESTA046, con el que se puede realizar el pago de la
     * tasa descrita en el parámetro XML
     *
     * @param base64
     * @return
     */
    public DatosRespuesta046 inserta46(String base64) {
        return cliente.inserta046(base64, this.cabecera);
    }

    /**
     * Solicita una url para realizar el pago de una lista de modelos, en la
     * entidad bancaria seleccionada.
     *
     * @param refsModelos
     * @param codigoEntidad
     * @param urlDeVuelta
     * @param idioma
     * @return
     */
    public DatosRespuestaGetUrlPago getUrlPago(ArrayOfGuid refsModelos,
            String codigoEntidad, String urlDeVuelta, String idioma) {
        return cliente.getUrlPago(refsModelos, codigoEntidad, urlDeVuelta, idioma,
                this.cabecera);
    }

    /**
     * Dado un localizador, devuelve un array de bytes de la carta de pago o
     * justificante generado, en PDF.
     *
     * @param localizador
     * @param importeaingresar
     * @param nifsujetopasivo
     * @param fechacreacion
     * @return
     */
    public byte[] getPdf046(String localizador, String importeaingresar,
            String nifsujetopasivo, String fechacreacion) {
        return cliente.getPdf046(localizador, importeaingresar, nifsujetopasivo,
                fechacreacion, this.cabecera);
    }

    /**
     * Devuelve los datos de una tasa dado su identificador.
     *
     * @param identificador
     * @return
     */
    public DatosTasa046 consultaDatosTasa046(String identificador) {
        return cliente.consultaDatosTasa046(identificador, this.cabecera);
    }

    /**
     * Comprobación del estado del servicio.
     *
     * @param incoming
     * @return
     */
    public DatosRespuesta046 ping046(String incoming) {
        return cliente.ping046(incoming, this.cabecera);
    }

    /**
     * Comprobación del estado del servicio.
     *
     * @param localizador
     *            localizador
     * @return
     */
    public DatosRespuesta046 estado046(String localizador) {
        return cliente.estado046(localizador, this.cabecera);
    }

    /**
     * Crea un objeto con los datos de usuario password para la cabecera de la
     * petición SOAP
     *
     * @return
     */
    private static UsuariosWebServices getUsuario(String user,
            String password) {
        final UsuariosWebServices usuario = new UsuariosWebServices();
        usuario.setIdentificador(user);
        usuario.setPassword(password);
        return usuario;
    }

    /**
     * Busca els host de la url indicada dentro de la propiedad
     * http.nonProxyHosts de la JVM
     *
     * @param url
     *            Endpoint del ws
     * @return true si el host esta dentro de la propiedad, fals en caso
     *         contrario
     */
    private static boolean validateNonProxyHosts(String url) throws Exception {
        final String nonProxyHosts = System.getProperty("http.nonProxyHosts");
        boolean existe = false;
        URL urlURL;
        try {
            if (nonProxyHosts != null && !"".equals(nonProxyHosts)) {
                urlURL = new URL(url);
                final String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
                for (int i = 0; i < nonProxyHostsArray.length; i++) {
                    final String a = nonProxyHostsArray[i]
                            .replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
                    ;
                    if (urlURL.getHost().matches(a)) {
                        existe = true;
                        break;
                    }
                }
            }
        } catch (final MalformedURLException e) {
            throw new Exception("Error validando nonProxyHosts", e);
        }
        return existe;
    }

}
