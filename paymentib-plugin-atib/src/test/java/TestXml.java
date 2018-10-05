import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.TypeIdioma;
import es.caib.paymentib.plugins.atib.xml.TaxaXml;

public class TestXml {

    public static void main(String[] args) throws Exception {

        final DatosPago datosPago = new DatosPago();
        datosPago.setAplicacionId("S2");
        datosPago.setEntidadId("E1");
        datosPago.setOrganismoId("O1");
        datosPago.setIdioma(TypeIdioma.CASTELLANO);
        datosPago.setConcepto("Concepto");
        datosPago.setModelo("046");
        datosPago.setTasaId("1.21.1.2.1.4");
        datosPago.setImporte(100);
        datosPago.setSujetoPasivoNif("33456299Q");
        datosPago.setSujetoPasivoNombre("Rafael Sanz Villanueva");
        datosPago.setDetallePago("Pago de trámite 1");

        final String xml = TaxaXml.generarXml("pagar", datosPago);

        System.out.println(xml);
    }

}
