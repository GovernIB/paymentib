
import es.caib.paymentib.plugins.atib.clientws.ClienteAtib;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosRespuesta046;
import es.caib.paymentib.plugins.atib.clientws.cxf.DatosTasa046;

public class TestCliente {

    public static void main(String[] args) {

        try {

            final ClienteAtib c = new ClienteAtib(
                    "http://www.atib.es/servicios/Service_Tasa.asmx?wsdl",
                    // "http://www.atib.es/servicios/Service_Tasa.asmx",
                    "indrauser", "INDRA");

            final DatosRespuesta046 res = c.ping046("");
            final DatosRespuesta046 a = res;
            final DatosTasa046 c1 = c.consultaDatosTasa046("4.1.3.4");
            final DatosTasa046 c2 = c1;

            System.out.println(c1.getIdentificador() + " " + c1.getDescripcion()
                    + " " + c1.getImporte());

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
