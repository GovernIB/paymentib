package es.caib.paymentib.core.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import es.caib.paymentib.core.api.model.types.TypeRoleAcceso;
import es.caib.paymentib.core.api.service.ContextService;

/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario.
 *
 * @author Indra
 *
 */
@Stateless
public class ContextServiceBean implements ContextService {

	/** Inyeccion contexto EJB */
	@Resource
	private SessionContext ctx;

	@Override
	@PermitAll
	public String getUsername() {
		return ctx.getCallerPrincipal().getName();
	}

	@Override
	@PermitAll
	public boolean hashRole(final String role) {
		return ctx.isCallerInRole(role);
	}

	@Override
	@PermitAll
	public List<TypeRoleAcceso> getRoles() {
		final List<TypeRoleAcceso> lista = new ArrayList<>();

		for (final TypeRoleAcceso role : TypeRoleAcceso.values()) {
			if (ctx.isCallerInRole(role.toString())) {
				lista.add(role);
			}
		}

		return lista;
	}

}
