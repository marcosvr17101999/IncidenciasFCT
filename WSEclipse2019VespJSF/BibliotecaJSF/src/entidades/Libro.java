package entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the LIBRO database table.
 * 
 */
@Entity
@NamedQuery(name="Libro.findAll", query="SELECT l FROM Libro l")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String isbn;

	private String titulo;

	//bi-directional many-to-one association to Ejemplar
	@OneToMany(mappedBy="libro")
	private List<Ejemplar> ejemplares;

	//bi-directional many-to-one association to Autor
	@ManyToOne
	@JoinColumn(name="IDAUTOR")
	private Autor autor;

	public Libro() {
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Ejemplar> getEjemplares() {
		return this.ejemplares;
	}

	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}

	public Ejemplar addEjemplare(Ejemplar ejemplare) {
		getEjemplares().add(ejemplare);
		ejemplare.setLibro(this);

		return ejemplare;
	}

	public Ejemplar removeEjemplare(Ejemplar ejemplare) {
		getEjemplares().remove(ejemplare);
		ejemplare.setLibro(null);

		return ejemplare;
	}

	public Autor getAutor() {
		return this.autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}