package es.caib.paymentib.backend.controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.paymentib.backend.model.DialogResult;
import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.model.types.TypeNivelGravedad;
import es.caib.paymentib.core.api.service.PagoBackService;

/**
 * @author aagudo
 *
 */
@ManagedBean
@ViewScoped
public class DialogPagosConfirmar extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private PagoBackService pagoBackService;

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
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		UtilJSF.checkSecOpenDialog(modo, getId());

		if (id == null) {
			setData(new DatosSesionPago());
		} else {
			setData(pagoBackService.getPagoByCodigo(Long.valueOf(id)));
		}
	}

	public void aceptar() {
		if (this.data.getFechaPago() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fecha.falta"));
			return;
		}

		if (this.data.getFechaPago().after(new Date())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fecha.posteriorHoy"));
			return;
		}

		pagoBackService.confirmarPago(data.getDatosPago().getIdentificador(), data.getFechaPago(),
				UtilJSF.getSessionBean().getUserName());

		setData(pagoBackService.getPagoByCodigo(Long.valueOf(id)));

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public DatosSesionPago getData() {
		return data;
	}

	public void setData(final DatosSesionPago data) {
		this.data = data;
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

}
