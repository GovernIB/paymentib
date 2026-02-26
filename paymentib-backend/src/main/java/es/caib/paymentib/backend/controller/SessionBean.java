package es.caib.paymentib.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import es.caib.paymentib.core.api.service.PagoBackService;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.paymentib.backend.util.UtilJSF;
import es.caib.paymentib.core.api.exception.ErrorBackException;
import es.caib.paymentib.core.api.model.types.TypeRoleAcceso;
import es.caib.paymentib.core.api.service.SecurityService;

/**
 * Información de sesión.
 *
 * @author Indra
 *
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {

	/**
	 * Usuario.
	 */
	private String userName;

	/**
	 * Roles del usuario.
	 */
	private List<TypeRoleAcceso> rolesList;

	/**
	 * Role activo principal (superadmin).
	 */
	private TypeRoleAcceso activeRole;

	/**
	 * Idioma actual.
	 */
	private String lang;

	/**
	 * Locale actual.
	 */
	private Locale locale;

	/**
	 * Titulo pantalla.
	 */
	private String literalTituloPantalla;

	private Map<String, Object> mochilaDatos;

	private String logo;

	private boolean hayLogo;

	/** Directorio ayuda externa. */
	private String directorioAyudaExterna;

	/** Servicio seguridad. */
	@Inject
	private SecurityService securityService;

	/** Servicio seguridad. */
	@Inject
	private PagoBackService pagoBackService;


	/** Inicio sesión. */
	@PostConstruct
	public void init() {

//		Sesion sesion = null;

		// Recupera info usuario
		userName = securityService.getUsername();

		// recuperamos datos por defecto del usuario
		/*if (StringUtils.isNotEmpty(userName)) {
			sesion = systemService.getSesion(userName);
		}*/

		if (FacesContext.getCurrentInstance().getViewRoot().getLocale() == null) {
			lang = "ca";
			locale = new Locale("ca", "ES");
		} else {
			lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		if (FacesContext.getCurrentInstance().getViewRoot() == null || FacesContext.getCurrentInstance().getViewRoot().getLocale() == null) {
			lang = "ca";
			locale = new Locale("ca", "ES");
		} else {
			lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		rolesList = securityService.getRoles();


		// Logo
		logo = pagoBackService.obtenerLogoBack();
		if (logo == null) {
			this.setHayLogo(false);
		} else {
			this.setHayLogo(true);
		}

		// Directorio ayuda externa
		directorioAyudaExterna = pagoBackService.obtenerDirectorioAyudaExterna();

		// Establece role activo por defecto
		if (activeRole == null) {
			if (rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
				activeRole = TypeRoleAcceso.SUPER_ADMIN;
			} else if (rolesList.contains(TypeRoleAcceso.CONSULTA)) {
				activeRole = TypeRoleAcceso.CONSULTA;
			} else {
				UtilJSF.redirectJsfPage("/error/errorUsuarioSinRol.xhtml", new HashMap<String, List<String>>());
				return;
			}
		}
		// inicializamos mochila
		mochilaDatos = new HashMap<>();
	}

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma) {
		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/** Cambio role activo. */
	public void cambiarRoleActivo(final String role) {

		// Cambia role
		final TypeRoleAcceso roleChange = TypeRoleAcceso.fromString(role);
		if (!rolesList.contains(roleChange)) {
			throw new ErrorBackException("No tiene el role indicado");
		}
		this.setActiveRole(roleChange);

		// Recarga pagina principal segun role
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public void redirectDefaultUrl() {
		UtilJSF.redirectJsfDefaultPageRole(activeRole);
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public String getDefaultUrl() {
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		final String contextPath = servletContext.getContextPath();
		return contextPath + UtilJSF.getDefaultUrlRole(activeRole);
	}

	/**
	 * Obtiene lenguaje opuesto al seleccionado (supone solo castellano/catalan).
	 *
	 * @return lang
	 */
	public String getChangeLang() {
		String res = null;
		if ("es".equals(lang)) {
			res = "ca";
		} else {
			res = "es";
		}
		return res;
	}

	/**
	 * Limpia mochila datos.
	 */
	public void limpiaMochilaDatos() {
		mochilaDatos.clear();
	}

	public void limpiaMochilaDatos(final String pClave) {
		mochilaDatos.remove(pClave);
	}

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();

		final DefaultSubMenu firstSubmenu = new DefaultSubMenu();
		firstSubmenu.setLabel(getUserName());
		firstSubmenu.setIcon("fa-li fa pi pi-user");
		final DefaultMenuItem item = new DefaultMenuItem();
		String literalIdioma = UtilJSF.getLiteral(getChangeLang());
		item.setAriaLabel(literalIdioma);
		item.setTitle(literalIdioma);
		item.setValue(literalIdioma);
		item.setCommand("#{sessionBean.cambiarIdioma(sessionBean.getChangeLang())}");
		item.setIcon("fa-li fa fa-flag");
		item.setStyleClass("colorNegro");
		firstSubmenu.getElements().add(item);

		model.getElements().add(firstSubmenu);

		final DefaultSubMenu secondSubmenu = new DefaultSubMenu();

		secondSubmenu.setLabel(UtilJSF.getLiteral("roles." + activeRole.name().toLowerCase()));
		secondSubmenu.setIcon("fa-li fa pi pi-id-card");
		for (final TypeRoleAcceso role : rolesList) {
			if (!activeRole.equals(role)) {
				final DefaultMenuItem item2 = new DefaultMenuItem();
				item2.setAriaLabel(UtilJSF.getLiteral("roles." + role.name().toLowerCase()));
				item2.setTitle(UtilJSF.getLiteral("roles." + role.name().toLowerCase()));
				item2.setValue(UtilJSF.getLiteral("roles." + role.name().toLowerCase()));
				item2.setCommand("#{sessionBean.cambiarRoleActivo(\"" + role.toString() + "\")}");
				item2.setIcon("fa-li fa pi pi-id-card");
				item2.setStyleClass("colorNegro");
				secondSubmenu.getElements().add(item2);
			}
		}
		model.getElements().add(secondSubmenu);

		model.generateUniqueIds();
		return model;
	}

	// --------- GETTERS / SETTERS ------------------

	public String getLiteralTituloPantalla() {
		return literalTituloPantalla;
	}

	public void setLiteralTituloPantalla(final String titulo) {
		this.literalTituloPantalla = titulo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String user) {
		this.userName = user;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(final String lang) {
		this.lang = lang;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public Map<String, Object> getMochilaDatos() {
		return mochilaDatos;
	}

	public void setMochilaDatos(final Map<String, Object> mapaDatos) {
		this.mochilaDatos = mapaDatos;
	}

	public List<TypeRoleAcceso> getRolesList() {
		return rolesList;
	}

	public void setRolesList(final List<TypeRoleAcceso> rolesList) {
		this.rolesList = rolesList;
	}

	public TypeRoleAcceso getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(final TypeRoleAcceso activeRole) {
		this.activeRole = activeRole;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(final SecurityService securityService) {
		this.securityService = securityService;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the hayLogo
	 */
	public boolean isHayLogo() {
		return hayLogo;
	}

	/**
	 * @param hayLogo the hayLogo to set
	 */
	public void setHayLogo(boolean hayLogo) {
		this.hayLogo = hayLogo;
	}

	/**
	 * Obtiene directorio ayuda externa.
	 * @return directorio ayuda externa
	 */
	public String getDirectorioAyudaExterna() {
		return directorioAyudaExterna;
	}

	/**
	 * Establece directorio ayuda externa.
	 * @param directorioAyudaExterna directorio ayuda externa
	 */
	public void setDirectorioAyudaExterna(String directorioAyudaExterna) {
		this.directorioAyudaExterna = directorioAyudaExterna;
	}
}
