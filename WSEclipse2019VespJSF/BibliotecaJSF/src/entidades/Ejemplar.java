package entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EJEMPLAR database table.
 * 
 */
@Entity
@NamedQuery(name="Ejemplar.findAll", query="SELECT e FROM Ejemplar e")
public class Ejemplar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idejemplar;

	private String baja;

	//bi-directional many-to-one association to Libro
	@ManyToOne
	@JoinColumn(name="ISBN")
	private Libro libro;

	public Ejemplar() {
	}

	public Long getIdejemplar() {
		return this.idejemplar;
	}

	public void setIdejemplar(Long idejemplar) {
		this.idejemplar = idejemplar;
	}

	public String getBaja() {
		return this.baja;
	}

	public void setBaja(String baja) {
		this.baja = baja;
	}

	public Libro getLibro() {
		return this.libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

}