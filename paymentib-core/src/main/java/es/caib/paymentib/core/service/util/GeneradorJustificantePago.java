package es.caib.paymentib.core.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import es.caib.paymentib.core.api.exception.JustificantePagoException;
import es.caib.paymentib.core.api.exception.NoExistePlantillaJustificanteEntidadException;
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
    public static byte[] generarJustificantePago(String dirConf,
            final DatosSesionPago datosPago) {

        // Verificamos si existe plantilla para entidad
        final String pathFichero = datosPago.getEntidadId() + "_"
                + "JUSTIFICANTE_GENERICO_"
                + datosPago.getDatosPago().getIdioma().toString() + ".pdf";
        final File f = new File(dirConf, pathFichero);
        if (!f.exists()) {
            throw new NoExistePlantillaJustificanteEntidadException(
                    datosPago.getEntidadId(), pathFichero);
        }

        // Cargamos plantilla
        byte[] contenido = null;
        try {
            final InputStream ficheroJustificantePago = new FileInputStream(f);
            contenido = IOUtils.toByteArray(ficheroJustificantePago);
        } catch (final IOException ioe) {
            throw new NoExistePlantillaJustificanteEntidadException(
                    datosPago.getEntidadId(), pathFichero, ioe);
        }

        // Generamos pdf a partir plantilla
        try {
            final PdfUtil pdf = new PdfUtil(contenido);
            final Map<String, String> datos = new HashMap<>();
            datos.put("SUJETOPASIVONIF",
                    datosPago.getDatosPago().getSujetoPasivoNif());
            datos.put("SUJETOPASIVONOMBRE",
                    datosPago.getDatosPago().getSujetoPasivoNombre());
            datos.put("MODELO", datosPago.getDatosPago().getModelo());
            datos.put("CONCEPTO", datosPago.getDatosPago().getConcepto());

            // Importe (convertimos de cents)
            final double impDec = Double.parseDouble(
                    String.valueOf(datosPago.getDatosPago().getImporte()))
                    / 100;
            final DecimalFormat df = (DecimalFormat) DecimalFormat
                    .getInstance(new Locale("es"));
            df.setDecimalSeparatorAlwaysShown(true);
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setMinimumIntegerDigits(1);
            final String importe = df.format(impDec);

            datos.put("IMPORTE", importe + " euros");
            final String pattern = "dd MMMMM yyyy HH:mm";
            SimpleDateFormat simpleDateFormat;
            if (datosPago.getDatosPago().getIdioma() == TypeIdioma.CATALAN) {
                simpleDateFormat = new SimpleDateFormat(pattern,
                        new Locale("ca", "ES"));
            } else if (datosPago.getDatosPago()
                    .getIdioma() == TypeIdioma.CASTELLANO) {
                simpleDateFormat = new SimpleDateFormat(pattern,
                        new Locale("es", "ES"));
            } else {
                simpleDateFormat = new SimpleDateFormat(pattern,
                        new Locale("en", "EN"));
            }
            datos.put("FECHACREACION",
                    simpleDateFormat.format(datosPago.getFechaCreacion()));
            datos.put("LOCALIZADOR", datosPago.getLocalizador());
            datos.put("FECHAPAGO",
                    simpleDateFormat.format(datosPago.getFechaPago()));
            pdf.establecerSoloImpresion();
            pdf.ponerValor(datos);
            final byte[] retorno = pdf.guardarEnMemoria(true);
            return retorno;
        } catch (final Exception exception) {
            throw new JustificantePagoException(exception);
        }
    }

}
