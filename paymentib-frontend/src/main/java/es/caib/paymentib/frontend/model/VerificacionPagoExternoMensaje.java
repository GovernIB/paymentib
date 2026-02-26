package es.caib.paymentib.frontend.model;

/**
 * Mensaje de la verificación de un pago externo.
 */
public class VerificacionPagoExternoMensaje {

    /** Título del mensaje */
    private String titulo;
    /** Texto del mensaje */
    private String texto;

    /**
     * Constructor por defecto.
     */
    public VerificacionPagoExternoMensaje() {
    }

    /**
     * Constructor.
     * @param titulo Título
     * @param texto Texto
     */
    public VerificacionPagoExternoMensaje(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    /**
     * Método de acceso a título.
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer título.
     * @param titulo título a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método de acceso a texto.
     * @return texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Método para establecer texto.
     * @param texto texto a establecer
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
