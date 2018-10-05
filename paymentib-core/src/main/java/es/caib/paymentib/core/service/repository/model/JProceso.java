package es.caib.paymentib.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapeo tabla PIB_PROCES.
 */
@Entity
@Table(name = "PIB_PROCES")
@SuppressWarnings("serial")
public class JProceso implements IModelApi {

    /** Identificador. */
    @Id
    @Column(name = "PROC_IDENT", unique = true, nullable = false)
    private String identificador;

    /** Instancia. */
    @Column(name = "PROC_INSTAN", length = 50)
    private String instancia;

    /** Fecha. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROC_FECHA")
    private Date fecha;

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a instancia.
     *
     * @return instancia
     */
    public String getInstancia() {
        return instancia;
    }

    /**
     * Método para establecer instancia.
     *
     * @param instancia
     *            instancia a establecer
     */
    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    /**
     * Método de acceso a fecha.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha.
     *
     * @param fecha
     *            fecha a establecer
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
