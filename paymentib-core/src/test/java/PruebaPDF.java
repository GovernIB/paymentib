import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.service.util.GeneradorJustificantePago;
import es.caib.paymentib.plugins.api.DatosPago;
import es.caib.paymentib.plugins.api.TypeIdioma;

public class PruebaPDF {

	public static void main(final String args[]) throws FileNotFoundException, IOException {
		final DatosSesionPago datosSesionPago = new DatosSesionPago();
		final DatosPago datosPago = new DatosPago();
		datosPago.setAplicacionId("SISTRA2");
		datosPago.setConcepto("CONCEPTO");
		datosPago.setDetallePago("DEtalle pago");
		datosPago.setEntidadId("ID ENTIDAD");
		datosPago.setIdentificador("1023981");
		datosPago.setIdioma(TypeIdioma.CATALAN);
		datosPago.setImporte(12);
		datosPago.setModelo("MODELO");
		datosPago.setOrganismoId("ORGANISMO ID");
		datosPago.setSujetoPasivoNif("123456789F");
		datosPago.setSujetoPasivoNombre("NOMBRE APE1 APE2");
		datosPago.setTasaId("TASA ID");
		datosSesionPago.setDatosPago(datosPago);
		datosSesionPago.setFechaCreacion(new Date());
		datosSesionPago.setFechaPago(new Date());
		datosSesionPago.setLocalizador("LOCALIZADOR");
		final byte[] contenido = GeneradorJustificantePago.generarJustificantePago("D:/", datosSesionPago);
		IOUtils.copy(new ByteArrayInputStream(contenido), new FileOutputStream("/prueba.pdf"));
	}
}
