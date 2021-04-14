/**
 *
 */
package es.caib.paymentib.frontend.model;

/**
 * @author Indra
 *
 */
public final class ModuleConfig {

	/** Entorno. **/
	private String entorno;

	/** Version. **/
	private String version;

	/** Commit svn. **/
	private String commitSvn;

	/** Commit git. **/
	private String commitGit;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
	}

	/**
	 * @param entorno the entorno to set
	 */
	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getCommitSvn() {
		return commitSvn;
	}

	public void setCommitSvn(final String commitSvn) {
		this.commitSvn = commitSvn;
	}

	public String getCommitGit() {
		return commitGit;
	}

	public void setCommitGit(final String commitGit) {
		this.commitGit = commitGit;
	}

}
