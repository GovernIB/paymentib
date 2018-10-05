package es.caib.paymentib.backend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.paymentib.backend.model.DialogResult;
import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.model.pago.DatosSesionPago;
import es.caib.paymentib.core.api.model.types.TypeModoAcceso;
import es.caib.paymentib.core.api.service.PagoBackService;

@ManagedBean
@ViewScoped
public class DialogPagos extends DialogControllerBase {

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

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		UtilJSF.checkSecOpenDialog(modo, getId());

		if (modo == TypeModoAcceso.CONSULTA) {
			setData(pagoBackService.getPagoByCodigo(Long.valueOf(id)));
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

}
