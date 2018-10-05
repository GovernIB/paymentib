package es.caib.paymentib.core.api.exception;

import java.util.Date;

import es.caib.paymentib.core.api.model.comun.ListaPropiedades;

/**
 * 
 * Excepcion que generara rollback para servicios transaccionales. Las
 * excepciones que generen rollback deberan heredar de esta clase. No se puede
 * usar directamente esta clase. Estas excepciones no hace falta declararlas en
 * los metodos.
 * 
 * 
 */
@SuppressWarnings("serial")
public abstract class ServiceRollbackException extends RuntimeException implements ServiceException {

  /**
   * Datos de la excepcion de servicio.
   */
  private final ServiceExceptionData serviceDataSRE = new ServiceExceptionData();
  
  @Override
  public boolean isNegocioException() {
	  return true;
  }

  /**
   * Genera excepción ServiceRollbackException estableciendo un mensaje .
   * 
   * @param messageSRE
   *          Mensaje
   */
  public ServiceRollbackException(final String messageSRE) {
    super(messageSRE);
  }

  /**
   * Genera excepción ServiceRollbackException estableciendo un mensaje y
   * detalles para la excepción.
   * 
   * @param messageSRE
   *          Mensaje
   * @param detallesSRE
   *          Detalles de la excepción
   */
  public ServiceRollbackException(final String messageSRE, final ListaPropiedades detallesSRE) {
    super(messageSRE);
    serviceDataSRE.setDetallesExcepcion(detallesSRE);
  }

  /**
   * Genera excepción ServiceRollbackException estableciendo un mensaje y la
   * causa.
   * 
   * @param pmessageSRE
   *          Mensaje
   * @param pcauseSRE
   *          Causa
   */
  public ServiceRollbackException(final String pmessageSRE, final Throwable pcauseSRE) {
    super(pmessageSRE, pcauseSRE);
  }

  /**
   * Genera excepción ServiceRollbackException estableciendo un mensaje, la
   * causa y los detalles.
   * 
   * @param messageSRE
   *          Mensaje
   * @param causeSRE
   *          Causa
   * @param detallesSRE
   *          Detalles de la excepción
   */
  public ServiceRollbackException(final String messageSRE, final Throwable causeSRE,
      final ListaPropiedades detallesSRE) {
    super(messageSRE, causeSRE);
    serviceDataSRE.setDetallesExcepcion(detallesSRE);
  }

  /**
   * Obtiene los detalles de la excepción ServiceRollbackException.
   * 
   * @return the detallesExcepcion
   */

  public final ListaPropiedades getDetallesExcepcion() {
    return serviceDataSRE.getDetallesExcepcion();
  }

  /**
   * Mótodo de acceso a fechaExcepcion de ServiceRollbackException.
   * 
   * @return fechaExcepcion
   */

  public final Date getFechaExcepcion() {
    return serviceDataSRE.getFechaExcepcion();
  }

  /**
   * Metodo para establecer fechaExcepcion de ServiceRollbackException.
   * 
   * @param pFechaExcepcionSRE
   *          fechaExcepcion a establecer
   */
  protected final void setFechaExcepcion(final Date pFechaExcepcionSRE) {
    serviceDataSRE.setFechaExcepcion(pFechaExcepcionSRE);
  }

  /**
   * Establece detalles excepción ServiceRollbackException.
   * 
   * @param detallesSRE
   *          Detalles excepción ServiceRollbackException
   */
  protected final void setDetallesExcepcion(final ListaPropiedades detallesSRE) {
    serviceDataSRE.setDetallesExcepcion(detallesSRE);
  }
}
