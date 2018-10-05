package es.caib.paymentib.core.api.service;

import java.util.List;

import es.caib.paymentib.core.api.model.types.TypeRoleAcceso;

/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario.
 * Para usar de forma interna en la capa de servicio.
 *
 * @author Indra
 *
 */
public interface ContextService {

	/**
	 * Obtiene usuario autenticado.
	 *
	 * @return usuario autenticado.
	 */
	public String getUsername();

	/**
	 * Verifica si tiene el role.
	 *
	 * @param role
	 *            role
	 * @return boolean si tiene el role
	 */
	public boolean hashRole(String role);

	/**
	 * Obtiene la lista de roles
	 *
	 * @return lista de roles
	 */
	public List<TypeRoleAcceso> getRoles();

}
