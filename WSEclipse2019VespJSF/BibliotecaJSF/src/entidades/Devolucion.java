package entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DEVOLUCION database table.
 * 
 */
@Entity
@NamedQuery(name="Devolucion.findAll", query="SELECT d FROM Devolucion d")
public class Devolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DevolucionPK id;

	@Temporal(TemporalType.DATE)
	private Date fechadevolucion;

	//uni-directional many-to-one association to Ejemplar
	@ManyToOne
	@JoinColumn(name="IDEJEMPLAR")
	private Ejemplar ejemplar;

	//uni-directional many-to-one association to Socio
	@ManyToOne
	@JoinColumn(name="IDSOCIO")
	private Socio socio;

	public Devolucion() {
	}

	public DevolucionPK getId() {
		return this.id;
	}

	public void setId(DevolucionPK id) {
		this.id = id;
	}

	public Date getFechadevolucion() {
		return this.fechadevolucion;
	}

	public void setFechadevolucion(Date fechadevolucion) {
		this.fechadevolucion = fechadevolucion;
	}

	public Ejemplar getEjemplar() {
		return this.ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public Socio getSocio() {
		return this.socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}