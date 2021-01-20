package es.caib.paymentib.plugins.tpv;

/**
 * Datos organismo.
 *
 * @author Indra
 *
 */
public class MerchantData {

	/** Nombre. */
	private String merchantName;
	/** Código. */
	private String merchantCode;
	/** Terminal. */
	private String merchantTerminal;
	/** Password. */
	private String merchantPassword;
	/** Url para iniciar pago. */
	private String merchantUrlInicio;
	/** Url para notificar pago. */
	private String merchantUrlNotificacion;

	/**
	 * Método de acceso a merchantName.
	 *
	 * @return merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * Método para establecer merchantName.
	 *
	 * @param merchantName
	 *                         merchantName a establecer
	 */
	public void setMerchantName(final String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * Método de acceso a merchantCode.
	 *
	 * @return merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * Método para establecer merchantCode.
	 *
	 * @param merchantCode
	 *                         merchantCode a establecer
	 */
	public void setMerchantCode(final String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * Método de acceso a merchantTerminal.
	 *
	 * @return merchantTerminal
	 */
	public String getMerchantTerminal() {
		return merchantTerminal;
	}

	/**
	 * Método para establecer merchantTerminal.
	 *
	 * @param merchantTerminal
	 *                             merchantTerminal a establecer
	 */
	public void setMerchantTerminal(final String merchantTerminal) {
		this.merchantTerminal = merchantTerminal;
	}

	/**
	 * Método de acceso a merchantPassword.
	 *
	 * @return merchantPassword
	 */
	public String getMerchantPassword() {
		return merchantPassword;
	}

	/**
	 * Método para establecer merchantPassword.
	 *
	 * @param merchantPassword
	 *                             merchantPassword a establecer
	 */
	public void setMerchantPassword(final String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}

	/**
	 * Método de acceso a merchantUrlNotificacion.
	 *
	 * @return merchantUrlNotificacion
	 */
	public String getMerchantUrlNotificacion() {
		return merchantUrlNotificacion;
	}

	/**
	 * Método para establecer merchantUrlNotificacion.
	 *
	 * @param merchantUrlNotificacion
	 *                                    merchantUrlNotificacion a establecer
	 */
	public void setMerchantUrlNotificacion(final String merchantUrlNotificacion) {
		this.merchantUrlNotificacion = merchantUrlNotificacion;
	}

	/**
	 * Método de acceso a merchantUrlInicio.
	 *
	 * @return merchantUrlInicio
	 */
	public String getMerchantUrlInicio() {
		return merchantUrlInicio;
	}

	/**
	 * Método para establecer merchantUrlInicio.
	 *
	 * @param merchantUrlInicio
	 *                              merchantUrlInicio a establecer
	 */
	public void setMerchantUrlInicio(final String merchantUrlInicio) {
		this.merchantUrlInicio = merchantUrlInicio;
	}

}
