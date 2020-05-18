package entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AUTOR database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Autor.findAll", query="SELECT a FROM Autor a"),
@NamedQuery(name="Autor.findByName", query="select a from Autor a where UPPER(a.nombre) LIKE UPPER(:nombre)"),
})
public class Autor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTOR_IDAUTOR_GENERATOR", sequenceName="S_AUTOR",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOR_IDAUTOR_GENERATOR")
	private Long idautor;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	private String nombre;

	//bi-directional many-to-one association to Libro
	@OneToMany(mappedBy="autor")
	private List<Libro> libros;

	public Autor() {
	}

	public Long getIdautor() {
		return this.idautor;
	}

	public void setIdautor(Long idautor) {
		this.idautor = idautor;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Libro> getLibros() {
		return this.libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public Libro addLibro(Libro libro) {
		getLibros().add(libro);
		libro.setAutor(this);

		return libro;
	}

	public Libro removeLibro(Libro libro) {
		getLibros().remove(libro);
		libro.setAutor(null);

		return libro;
	}

}