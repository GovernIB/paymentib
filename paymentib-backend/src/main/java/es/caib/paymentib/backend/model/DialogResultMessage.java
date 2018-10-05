package es.caib.paymentib.backend.model;

import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;

/**
 * Mensaje a mostrar al cerrar ventana de dialogo.
 *
 * @author Indra
 *
 */
public class DialogResultMessage {

	/**
	 * Nivel gravedad
	 */
	private TypeNivelGravedad nivel;

	/**
	 * Mensaje.
	 */
	private String mensaje;

	public TypeNivelGravedad getNivel() {
		return nivel;
	}

	public void setNivel(final TypeNivelGravedad nivel) {
		this.nivel = nivel;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}
}
