package es.caib.paymentib.core.api.service;

import java.util.List;

import es.caib.paymentib.core.api.model.types.TypeRoleAcceso;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
public interface SecurityService {

	/**
	 * Obtiene usuario autenticado.
	 *
	 * @return usuario autenticado.
	 */
	public String getUsername();

	/**
	 * Obtiene la lista de roles.
	 *
	 * @return lista de roles
	 */
	public List<TypeRoleAcceso> getRoles();

	/**
	 * Verifica si es usuario Superadministrador.
	 *
	 * @return boolean.
	 */
	public boolean isSuperAdministrador();

	/**
	 * Verifica si es usuario Consulta.
	 *
	 * @return boolean.
	 */
	public boolean isConsulta();

}
