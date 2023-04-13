/*
 *
 */
package es.caib.paymentib.backend.controller;

import javax.faces.bean.ManagedProperty;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;

/**
 * Clase de las que heredan los View.
 *
 * @author Indra
 *
 */
public abstract class ViewControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewControllerBase.class);

	/**
	 * Modo de acceso a la ventana.
	 */
	protected String modoAcceso;

	/**
	 * Referencia al bean de sesion
	 */
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sesion;

	/**
	 * Obtiene el valor del bean de sesion.
	 *
	 * @return el valor del bean de sesion
	 */
	public SessionBean getSesion() {
		return sesion;
	}

	/**
	 * Establece el valor del bean de sesion.
	 *
	 * @param sesion
	 *            el nuevo valor del bean de sesion
	 */
	public void setSesion(final SessionBean sesion) {
		this.sesion = sesion;
	}

	/**
	 * Establece el valor del titulo de la pantalla.
	 *
	 * @param titulo
	 *            el nuevo valor del titulo de la pantalla.
	 */
	public void setLiteralTituloPantalla(final String titulo) {
		sesion.setLiteralTituloPantalla(titulo);
	}

	/**
	 * Devuelve logger.
	 *
	 * @return logger
	 */
	protected final Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Ventana principal de ayudar.
	 *
	 * @param event
	 *            resultado
	 */
	public void ayudar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

	/**
	 * Indica si la ventana se abre en modo alta.
	 *
	 * @return boolean
	 */
	public boolean isAlta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si la ventana se abre en modo edicion.
	 *
	 * @return boolean
	 */
	public boolean isEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si la ventana se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isConsulta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Obtiene modo acceso a la ventana.
	 *
	 * @return modo acceso
	 */
	public String getModoAcceso() {
		return modoAcceso;
	}

	/**
	 * Establece modo acceso a la ventana.
	 *
	 * @param modoAcceso
	 *            modo acceso
	 */
	public void setModoAcceso(final String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	/**
	 * Normaliza filtro
	 *
	 * @param filtro
	 *            Filtro
	 * @return filtro
	 */
	protected String normalizarFiltro(String filtro) {
		if (filtro != null && StringUtils.isBlank(filtro)) {
			filtro = null;
		}
		if (filtro != null) {
			filtro = filtro.trim();
		}
		return filtro;
	}

}
