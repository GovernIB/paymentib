package es.caib.paymentib.plugins.atib.xml;

import java.io.ByteArrayOutputStream;

import org.apache.xmlbeans.XmlOptions;

import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.atib.xml.DECLARANTDocument.DECLARANT;
import es.caib.paymentib.plugins.atib.xml.TAXADocument.TAXA;

public class TaxaXml {

    public static final String CODI_IDTAXA = "c04";
    public static final String CODI_SUBCONCEPTE = "c24";
    public static final String CODI_IMPORT = "c75";
    public static final String CODI_CONCEPTE = "c22";
    public static final String CODI_NIF = "c05";
    public static final String CODI_NOM = "c06";
    public static final String CODI_SIGLES = "c07";
    public static final String CODI_NOM_VIA = "c08";
    public static final String CODI_NUMERO = "c09";
    public static final String CODI_LLETRA = "c10";
    public static final String CODI_ESCALA = "c11";
    public static final String CODI_PIS = "c12";
    public static final String CODI_PORTA = "c13";
    public static final String CODI_TELEFON = "c14";
    public static final String CODI_FAX = "c15";
    public static final String CODI_LOCALITAT = "c16";
    public static final String CODI_PROVINCIA = "c17";
    public static final String CODI_CODI_POSTAL = "c18";

    /**
     *
     * @param accion
     *            "pagar" / "imprimir"
     * @param datosPago
     * @throws Exception
     */
    public static String generarXml(String accion, DatosPago datosPago)
            throws Exception {

        final TAXADocument xmlDoc = TAXADocument.Factory.newInstance();

        final TAXA taxa = xmlDoc.addNewTAXA();

        taxa.setAccio(
                es.caib.paymentib.plugins.atib.xml.TAXADocument.TAXA.Accio.Enum
                        .forString(accion));

        taxa.setMODELO(datosPago.getModelo());
        setValorCampo(taxa.addNewIDTAXA(), datosPago.getTasaId(), CODI_IDTAXA);
        setValorCampo(taxa.addNewIMPORT(), datosPago.getImporte() + "",
                CODI_IMPORT);
        setValorCampo(taxa.addNewCONCEPTE(), datosPago.getConcepto(),
                CODI_CONCEPTE);
        final DECLARANT declarante = taxa.addNewDECLARANT();
        setValorCampo(declarante.addNewNIF(), datosPago.getSujetoPasivoNif(),
                CODI_NIF);
        setValorCampo(declarante.addNewNOM(), datosPago.getSujetoPasivoNombre(),
                CODI_IDTAXA);

        final XmlOptions xmlOpt = new XmlOptions();
        xmlOpt.setCharacterEncoding("UTF-8");
        xmlOpt.setSavePrettyPrint();
        xmlDoc.xmlText(xmlOpt);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        xmlDoc.save(baos, xmlOpt);
        final String xml = new String(baos.toByteArray(), "UTF-8");
        return xml;
    }

    /**
     * Establece valor campo.
     *
     * @param campo
     *            campo
     * @param valor
     *            valor
     * @param codigo
     *            codigo
     */
    private static void setValorCampo(final CAMPOCONCODIGO campo,
            final String valor, final String codigo) {
        campo.setCodi(codigo);
        campo.setStringValue(valor);
    }

}
