package es.caib.paymentib.plugins.atib;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.EntidadPago;
import es.caib.paymentib.plugins.api.EstadoPago;
import es.caib.paymentib.plugins.api.IPasarelaPagoPlugin;
import es.caib.paymentib.plugins.api.PasarelaPagoException;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.api.UrlRedireccionPasarelaPago;
import es.caib.paymentib.plugins.atib.clientws.ClienteAtib;
import es.caib.paymentib.plugins.atib.clientws.cxf.ArrayOfGuid;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuesta046;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuestaGetUrlPago;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosTasa046;
import es.caib.paymentib.plugins.atib.xml.TaxaXml;

/**
 * Pasarela de pago ATIB.
 *
 * @author Indra
 *
 */
public class AtibPlugin extends AbstractPluginProperties
        implements IPasarelaPagoPlugin {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(AtibPlugin.class);

    /**
     *
     * Constructor.
     *
     * @param prefijoPropiedades
     *            prefijo
     * @param properties
     *            propiedades
     */
    public AtibPlugin(final String prefijoPropiedades,
            final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public String getPasarelaId() {
        return "ATIB";
    }

    @Override
    public List<EntidadPago> obtenerEntidadesPagoElectronico(
            final TypeIdioma idioma) throws PasarelaPagoException {
        final List<EntidadPago> res = new ArrayList<>();
        final String entidadesPago[] = this.getProperty("entidadesPago")
                .split(";");
        EntidadPago ep = null;
        for (final String id : entidadesPago) {
            ep = new EntidadPago();
            ep.setCodigo(id);
            ep.setDescripcion(this.getProperty(
                    "entidadPago." + id + "." + idioma.toString()));
            if (StringUtils.isBlank(ep.getDescripcion())) {
                ep.setDescripcion(id);
            }
            ep.setLogo(this.getProperty("entidadPago." + id + ".logo"));
            res.add(ep);
        }
        return res;
    }

    @Override
    public UrlRedireccionPasarelaPago iniciarPagoElectronico(
            final DatosPago datosPago, final String entidadPagoId,
            final String urlCallback) throws PasarelaPagoException {
        try {
            // Genera XML de pago
            final String xmlPago = TaxaXml.generarXml("pagar", datosPago);
            final String xmlPagoB64 = Base64.getEncoder()
                    .encodeToString(xmlPago.getBytes("UTF-8"));

            // Generamos cliente
            final ClienteAtib cliente = this.crearClienteAtib();

            // Enviamos pago
            final DatosRespuesta046 resInserta046 = cliente
                    .inserta46(xmlPagoB64);
            if (resInserta046.getCodError() != null) {
                throw new PasarelaPagoException("Error enviando datos pago: "
                        + resInserta046.getCodError() + " - "
                        + resInserta046.getTextError());
            }

            // Obtenemos url pago
            final ArrayOfGuid refsModelos = new ArrayOfGuid();
            refsModelos.getGuid().add(resInserta046.getToken());
            final DatosRespuestaGetUrlPago resUrlPago = cliente
                    .getUrlPago(refsModelos, entidadPagoId, urlCallback);
            if (resUrlPago.getUrl() == null) {
                throw new PasarelaPagoException("Error obteniendo url pago ");
            }

            // Retornamos datos inicio pago
            final UrlRedireccionPasarelaPago resultado = new UrlRedireccionPasarelaPago();
            resultado.setLocalizador(resInserta046.getLocalizador());
            resultado.setUrl(resUrlPago.getUrl());
            return resultado;
        } catch (final Exception ex) {
            throw new PasarelaPagoException(
                    "Excepcion invocando pasarela: " + ex.getMessage(), ex);
        }
    }

    @Override
    public EstadoPago verificarRetornoPagoElectronico(final DatosPago datosPago,
            final String localizador,
            final Map<String, String[]> parametrosRetorno)
            throws PasarelaPagoException {
        return verificarPagoImpl(localizador);
    }

    @Override
    public EstadoPago verificarPagoElectronico(final DatosPago datosPago,
            final String localizador) throws PasarelaPagoException {
        return verificarPagoImpl(localizador);
    }

    @Override
    public byte[] obtenerJustificantePagoElectronico(final DatosPago datosPago,
            final String localizador) throws PasarelaPagoException {
        try {
            log.debug("Obtener justificante pago");

            // Generamos cliente
            final ClienteAtib cliente = this.crearClienteAtib();

            // Obtenemos PDF
            final byte[] resPDF = cliente.getPdf046(localizador,
                    centsToEur(datosPago.getImporte() + ""),
                    datosPago.getSujetoPasivoNif(),
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            if (resPDF == null) {
                throw new PasarelaPagoException(
                        "Error obteniendo justificante pago");
            }

            log.debug("Justificante de pago obtenido");

            return resPDF;

        } catch (final Exception ex) {
            throw new PasarelaPagoException(
                    "Excepcion invocando pasarela: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int consultaTasa(final String idTasa) throws PasarelaPagoException {
        log.debug("Consulta tasa: " + idTasa);
        final ClienteAtib clienteAtib = crearClienteAtib();
        final DatosTasa046 res = clienteAtib.consultaDatosTasa046(idTasa);
        if (res.getCodError() != null) {
            throw new PasarelaPagoException("Error consultando tasa: "
                    + res.getCodError() + " - " + res.getDescripcion());
        }
        log.debug("Tasa: " + res.getImporte() + " cents");
        return Integer.parseInt(res.getImporte());
    }

    @Override
    public boolean permitePagoPresencial() {
        return true;
    }

    @Override
    public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago)
            throws PasarelaPagoException {
        try {
            log.debug("Obtener carta pago");

            // Genera XML de pago
            final String xmlPago = TaxaXml.generarXml("imprimir", datosPago);
            final String xmlPagoB64 = Base64.getEncoder()
                    .encodeToString(xmlPago.getBytes("UTF-8"));

            // Generamos cliente
            final ClienteAtib cliente = this.crearClienteAtib();

            // Enviamos pago
            final DatosRespuesta046 resInserta046 = cliente
                    .inserta46(xmlPagoB64);
            if (resInserta046.getCodError() != null) {
                throw new PasarelaPagoException("Error enviando datos pago: "
                        + resInserta046.getCodError() + " - "
                        + resInserta046.getTextError());
            }

            // Obtenemos PDF
            final byte[] resPDF = cliente.getPdf046(
                    resInserta046.getLocalizador(),
                    centsToEur(datosPago.getImporte() + ""),
                    datosPago.getSujetoPasivoNif(),
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            if (resPDF == null) {
                throw new PasarelaPagoException("Error obteniendo carta pago");
            }

            log.debug("Carta de pago obtenida");

            return resPDF;

        } catch (final Exception ex) {
            throw new PasarelaPagoException(
                    "Excepcion invocando pasarela: " + ex.getMessage(), ex);
        }
    }

    /**
     * Crea cliente ws ATIB.
     *
     * @throws PasarelaPagoException
     */
    public ClienteAtib crearClienteAtib() throws PasarelaPagoException {
        try {
            return new ClienteAtib(this.getProperty("url"),
                    this.getProperty("usr"), this.getProperty("pwd"));
        } catch (final Exception e) {
            throw new PasarelaPagoException(
                    "Excepcion obteniendo cliente ATIB: " + e.getMessage(), e);
        }
    }

    /**
     * Convierte de cents a euros.
     *
     * @param importe
     *            importe en cents
     * @return importe en euros
     */
    private String centsToEur(final String importe) {
        final double impDec = Double.parseDouble(importe) / 100;
        final DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance();
        f.setDecimalSeparatorAlwaysShown(true);
        f.setMaximumFractionDigits(2);
        f.setMinimumFractionDigits(2);
        f.setMinimumIntegerDigits(1);
        final String importeDec = f.format(impDec);
        return importeDec;
    }

    /**
     * Verifica pago
     *
     * @param localizador
     * @return
     * @throws PasarelaPagoException
     */
    private EstadoPago verificarPagoImpl(final String localizador)
            throws PasarelaPagoException {
        try {
            log.debug("Verificar estado pago");

            // Generamos cliente
            final ClienteAtib cliente = this.crearClienteAtib();

            // Verifica estado pago
            final DatosRespuesta046 resEstado = cliente.estado046(localizador);

            log.debug("Estado pago: CodError: " + resEstado.getCodError()
                    + " MensajeError: " + resEstado.getTextError() + " Estado: "
                    + resEstado.getEstadoPago());

            final EstadoPago estadoPago = new EstadoPago();
            if (resEstado.getCodError() != null) {
                estadoPago.setEstado(TypeEstadoPago.DESCONOCIDO);
                estadoPago.setCodigoErrorPasarela(
                        resEstado.getCodError().toString());
                estadoPago.setMensajeErrorPasarela(resEstado.getTextError());
            } else {
                if ("OK".equals(resEstado.getEstadoPago())) {
                    estadoPago.setEstado(TypeEstadoPago.PAGADO);
                    estadoPago.setFechaPago(
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                    .parse(resEstado.getFechaPago()));
                } else {
                    // TODO Ver si podemos gestionar mas estados
                    estadoPago.setEstado(TypeEstadoPago.NO_PAGADO);
                }
            }

            return estadoPago;

        } catch (final Exception ex) {
            throw new PasarelaPagoException(
                    "Excepcion invocando pasarela: " + ex.getMessage(), ex);
        }
    }

}
