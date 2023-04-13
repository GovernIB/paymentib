/**
 *
 */
package es.caib.paymentib.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

    private ConstantesRolesAcceso() {
        super();
    }

    public static final String SUPER_ADMIN = "PIB_ADM";

    public static final String CONSULTA = "PIB_CONS";

    public static final String API = "PIB_API";

    public static final String[] listaRoles() {
        final String[] rolesPrincipales = {SUPER_ADMIN, CONSULTA};
        return rolesPrincipales;
    }
}
