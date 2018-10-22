package es.caib.paymentib.core.service.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import es.caib.paymentib.core.api.exception.JustificantePagoException;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.service.util.pdf.PdfUtil;
import es.caib.paymentib.plugins.api.TypeIdioma;

/**
 * Generador justificante pago.
 *
 * @author Indra
 *
 */
public class GeneradorJustificantePago {

	/**
	 * Genera justificante estandard de pago (PDF).
	 *
	 * @param datosPago
	 *            Datos pago
	 */
	public static byte[] generarJustificantePago(final DatosSesionPago datosPago) {

		try {
			final InputStream ficheroJustificantePago = GeneradorJustificantePago.class.getClassLoader()
					.getResourceAsStream(
							"JUSTIFICANTE_DE_PAGO_" + datosPago.getDatosPago().getIdioma().toString() + ".pdf");
			final byte[] contenido = IOUtils.toByteArray(ficheroJustificantePago);
			final PdfUtil pdf = new PdfUtil(contenido);
			final Map<String, String> datos = new HashMap<>();
			datos.put("SUJETOPASIVONIF", datosPago.getDatosPago().getSujetoPasivoNif());
			datos.put("SUJETOPASIVONOMBRE", datosPago.getDatosPago().getSujetoPasivoNombre());
			datos.put("MODELO", datosPago.getDatosPago().getModelo());
			datos.put("CONCEPTO", datosPago.getDatosPago().getConcepto());
			datos.put("IMPORTE", String.valueOf(datosPago.getDatosPago().getImporte()));
			final String pattern = "dd MMMMM yyyy HH:mm";
			SimpleDateFormat simpleDateFormat;
			if (datosPago.getDatosPago().getIdioma() == TypeIdioma.CATALAN) {
				simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ca", "ES"));
			} else if (datosPago.getDatosPago().getIdioma() == TypeIdioma.CASTELLANO) {
				simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "ES"));
			} else {
				simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "EN"));
			}
			datos.put("FECHACREACION", simpleDateFormat.format(datosPago.getFechaCreacion()));
			datos.put("LOCALIZADOR", datosPago.getLocalizador());
			datos.put("FECHAPAGO", simpleDateFormat.format(datosPago.getFechaPago()));
			pdf.establecerSoloImpresion();
			pdf.ponerValor(datos);
			final byte[] retorno = pdf.guardarEnMemoria(true);
			// final Path path = Paths.get("P:\\prueba.pdf");
			// Files.write(path, retorno);
			return retorno;
		} catch (final Exception exception) {
			throw new JustificantePagoException(exception);
		}
	}

}
