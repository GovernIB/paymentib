package es.caib.paymentib.core.api.exception;

import java.util.Date;

import es.caib.paymentib.core.api.model.comun.ListaPropiedades;

/**
 * 
 * Excepcion que no generara rollback para servicios transaccionales. Las
 * excepciones controladas que no deban generar rollback deberan heredar de esta
 * clase. No se puede usar directamente esta clase. Estas excepciones hace falta
 * declararlas en los metodos.
 * 
 */
@SuppressWarnings("serial")
public abstract class ServiceNoRollbackException extends Exception implements ServiceException {

  /**
   * Datos de la excepcion de servicio.
   */
  private final ServiceExceptionData serviceDataSNRE = new ServiceExceptionData();
  
  @Override
  public boolean isNegocioException() {
	  return true;
  }

  /**
   * Genera excepción ServiceNoRollbackException estableciendo un mensaje.
   * 
   * @param messageSNRE
   *          Mensaje
   */
  public ServiceNoRollbackException(final String messageSNRE) {
    super(messageSNRE);
  }

  /**
   * Genera excepción ServiceNoRollbackException estableciendo un mensaje y
   * detalles para la excepción.
   * 
   * @param messageSNRE
   *          Mensaje
   * @param detallesSNRE
   *          Detalles de la excepción
   */
  public ServiceNoRollbackException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
    super(messageSNRE);
    serviceDataSNRE.setDetallesExcepcion(detallesSNRE);
  }

  /**
   * Genera excepción ServiceNoRollbackException estableciendo un mensaje y la
   * causa.
   * 
   * @param messageSNRE
   *          Mensaje
   * @param causeSNRE
   *          Causa
   */
  public ServiceNoRollbackException(final String messageSNRE, final Throwable causeSNRE) {
    super(messageSNRE, causeSNRE);
  }

  /**
   * Genera excepción ServiceNoRollbackException estableciendo un mensaje y la
   * causa.
   * 
   * @param messageSNRE
   *          Mensaje
   * @param causeSNRE
   *          Causa
   * @param detallesSNRE
   *          Detalles de la excepción
   */
  public ServiceNoRollbackException(final String messageSNRE, final Throwable causeSNRE,
      final ListaPropiedades detallesSNRE) {
    super(messageSNRE, causeSNRE);
    serviceDataSNRE.setDetallesExcepcion(detallesSNRE);
  }

  /**
   * Obtiene los detalles de la excepción ServiceNoRollbackException.
   * 
   * @return Devuelbe detalles Excepcion
   */

  public final ListaPropiedades getDetallesExcepcion() {
    return serviceDataSNRE.getDetallesExcepcion();
  }

  /**
   * Metodo de acceso a fechaExcepcion de ServiceNoRollbackException.
   * 
   * @return fechaExcepcion
   */

  public final Date getFechaExcepcion() {
    return serviceDataSNRE.getFechaExcepcion();
  }

  /**
   * Metodo para establecer fechaExcepcion de ServiceNoRollbackException.
   * 
   * @param pFechaExcepcion
   *          fechaExcepcion a establecer
   */
  protected final void setFechaExcepcion(final Date pFechaExcepcion) {
    serviceDataSNRE.setFechaExcepcion(pFechaExcepcion);
  }

  /**
   * Establece detalles excepción ServiceNoRollbackException.
   * 
   * @param detallesSNRE
   *          Detalles excepción ServiceNoRollbackException
   */
  protected final void setDetallesExcepcion(final ListaPropiedades detallesSNRE) {
    serviceDataSNRE.setDetallesExcepcion(detallesSNRE);
  }
}
