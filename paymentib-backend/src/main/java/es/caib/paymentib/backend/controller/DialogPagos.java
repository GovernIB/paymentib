package es.caib.paymentib.backend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.caib.paymentib.backend.model.DialogResult;
import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;
import es.caib.paymentib.core.api.service.PagoBackService;
import es.caib.paymentib.core.api.exception.CargaConfiguracionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@ManagedBean
@ViewScoped
public class DialogPagos extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private PagoBackService pagoBackService;

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private DatosSesionPago data;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Identificador cuando se envia desde ibenred
	 */
	private String identificador;

	/**
	 * La botonera inferior que incluye la ayuda y el botón de cerrar. Cuando se carga desde STH, el pago no debe mostrar esos botones, porque ya está el botón de cerrar en STH
	 */
	private boolean mostrarBotoneraInferior;


	/**
	 * Inicialización.
	 */
	public void init() {
		mostrarBotoneraInferior = true;
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (id != null && !id.isEmpty()) {
			UtilJSF.checkSecOpenDialog(modo, getId(), null);
		}

		if (modo == TypeModoAcceso.CONSULTA) {
			if (id != null && !id.isEmpty()) {
				setData(pagoBackService.getPagoByCodigo(Long.valueOf(id)));
			} else  	if (identificador != null) {
				mostrarBotoneraInferior = false;
				setData(pagoBackService.getPagoByIdentificador(identificador));
			}

		} else {
			setData(new DatosSesionPago());
		}
	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.copiar"));
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("pagosDialog");
	}

	private Properties recuperarConfiguracionProperties() {
		final String pathProperties = System.getProperty("es.caib.paymentib.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}

	/**
	 * Método que se encarga de mostrar el metodo de pago
	 * @param metodoPago Código del método de pago
	 * @return Método de pago
	 */
	public String mostrarMetodoPago(String metodoPago) {
		if (metodoPago == null || metodoPago.isEmpty()) {
			return UtilJSF.getLiteral("typeMetodoPagoSeleccionado.noInformado");
		}

		String entidadesPago = propiedadesLocales.getProperty("pasarela.ATIB.entidadesPago");

		return entidadesPago.contains(metodoPago) ? propiedadesLocales.getProperty("pasarela.ATIB.entidadPago." + metodoPago + "." + UtilJSF.getIdioma()) : metodoPago;
	}

	private String getTokenFromRequest() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("TOKEN");
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public DatosSesionPago getData() {
		return data;
	}

	public void setData(DatosSesionPago data) {
		this.data = data;
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public final String getErrorCopiar() {
		return errorCopiar;
	}

	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}


	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the mostrarBotoneraInferior
	 */
	public boolean isMostrarBotoneraInferior() {
		return mostrarBotoneraInferior;
	}

	/**
	 * @param mostrarBotoneraInferior the mostrarBotoneraInferior to set
	 */
	public void setMostrarBotoneraInferior(boolean mostrarBotoneraInferior) {
		this.mostrarBotoneraInferior = mostrarBotoneraInferior;
	}


}
