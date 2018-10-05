package es.caib.paymentib.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.service.PagoFrontService;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.TypeEstadoPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.rest.api.v1.RDatosInicioPago;
import es.caib.paymentib.rest.api.v1.RDatosPago;
import es.caib.paymentib.rest.api.v1.RRedireccionPago;
import es.caib.paymentib.rest.api.v1.RTypeEstadoPago;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones API Rest.
 *
 * @author Indra
 */
@RestController
@RequestMapping("/v1")
@Api(value = "v1", produces = "application/json")
public class ApiRestController {

    /** Service. */
    @Autowired
    private PagoFrontService service;

    /**
     * Inicia pago electrónico.
     *
     * @param rPago
     *            Datos inicio pago
     * @return Redirección al pago (identificador pago + url)
     * @throws RPagoPluginException
     */
    @ApiOperation(value = "iniciarPagoElectronico", notes = "Inicia pago electrónico.", response = RRedireccionPago.class)
    @RequestMapping(value = "/iniciarPagoElectronico", method = RequestMethod.POST)
    public RRedireccionPago iniciarPagoElectronico(
            @RequestBody RDatosInicioPago rPago) {

        // Crea pago electrónico
        final DatosPago datosPago = convertDatosPago(rPago.getDatosPago());

        final DatosSesionPago datosSesionPago = service.crearPagoElectronico(
                rPago.getDatosPago().getPasarelaId(), datosPago,
                rPago.getUrlCallback());

        // Devolvemos url redirección pago
        final RRedireccionPago res = new RRedireccionPago();
        res.setIdentificador(datosSesionPago.getDatosPago().getIdentificador());
        res.setUrlPago(service.obtenerUrlFrontal() + "/inicioPago/"
                + datosSesionPago.getTokenAcceso() + ".html");
        return res;
    }

    /**
     * Verifica estado pago contra pasarela de pago.
     *
     * @param identificador
     *            identificador pago
     * @return estado pago
     */
    @ApiOperation(value = "verificarPagoElectronico", notes = "Verifica estado pago contra pasarela de pago.", response = RTypeEstadoPago.class)
    @RequestMapping(value = "/verificarPagoElectronico/{identificador}", method = RequestMethod.POST)
    public RTypeEstadoPago verificarPagoElectronico(
            @PathVariable("identificador") final String identificador) {

        final TypeEstadoPago estado = service
                .verificarPagoElectronico(identificador);

        RTypeEstadoPago res = null;
        switch (estado) {
        case PAGADO:
            res = RTypeEstadoPago.PAGADO;
            break;
        case NO_PAGADO:
            res = RTypeEstadoPago.NO_PAGADO;
            break;
        default:
            res = RTypeEstadoPago.DESCONOCIDO;
        }

        return res;
    }

    /**
     * Obtiene justificante de pago
     *
     * @param identificador
     *            identificador pago
     * @return Justificante de pago (nulo si la pasarela no genera
     *         justificante).
     */
    @ApiOperation(value = "obtenerJustificantePagoElectronico", notes = "btiene justificante de pago")
    @RequestMapping(value = "/obtenerJustificantePagoElectronico/{identificador}", method = RequestMethod.POST)
    @ResponseBody
    public byte[] obtenerJustificantePagoElectronico(
            @PathVariable("identificador") final String identificador) {
        return service.obtenerJustificantePagoElectronico(identificador);
    }

    /**
     * Obtiene importe tasa.
     *
     * * @param idPasarela id pasarela
     *
     * @param idTasa
     *            id tasa
     * @return importe (en cents)
     * @throws RPagoPluginException
     */
    @ApiOperation(value = "consultaTasa", notes = "Obtiene importe tasa.")
    @RequestMapping(value = "/consultaTasa/{idPasarela}/{idTasa}", method = RequestMethod.POST)
    public int consultaTasa(@PathVariable("idPasarela") final String idPasarela,
            @PathVariable("idTasa") final String idTasa) {
        return service.consultaTasa(idPasarela, idTasa);
    }

    /**
     * Obtiene carta de pago presencial (PDF).
     *
     * @param datosPago
     *            Datos pago
     * @return carta de pago presencial
     */
    @ApiOperation(value = "obtenerCartaPagoPresencial", notes = "Logout")
    @RequestMapping(value = "/obtenerCartaPagoPresencial", method = RequestMethod.POST)
    @ResponseBody
    public byte[] obtenerCartaPagoPresencial(
            @RequestBody RDatosPago datosPago) {
        final DatosPago dp = convertDatosPago(datosPago);
        return service.obtenerCartaPagoPresencial(datosPago.getPasarelaId(),
                dp);
    }

    /**
     * Obtiene datos pago a partif datos inicio pago
     *
     * @param rDatosPago
     * @return
     */
    private DatosPago convertDatosPago(RDatosPago rDatosPago) {
        final DatosPago datosPago = new DatosPago();
        datosPago.setAplicacionId(rDatosPago.getAplicacionId());
        datosPago.setEntidadId(rDatosPago.getEntidadId());
        datosPago.setOrganismoId(rDatosPago.getOrganismoId());
        datosPago.setDetallePago(rDatosPago.getDetallePago());
        datosPago.setIdioma(TypeIdioma.fromString(rDatosPago.getIdioma()));
        datosPago.setSujetoPasivoNif(rDatosPago.getSujetoPasivoNif());
        datosPago.setSujetoPasivoNombre(rDatosPago.getSujetoPasivoNombre());
        datosPago.setModelo(rDatosPago.getModelo());
        datosPago.setConcepto(rDatosPago.getConcepto());
        datosPago.setTasaId(rDatosPago.getTasaId());
        datosPago.setImporte(rDatosPago.getImporte());
        return datosPago;
    }
}
