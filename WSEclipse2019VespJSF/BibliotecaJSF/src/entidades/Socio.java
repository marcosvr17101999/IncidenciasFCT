package entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SOCIO database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Socio.findAll", query="SELECT s FROM Socio s order by s.idsocio"),
	@NamedQuery(name="Socio.findByName", query="select s from Socio s where UPPER(s.nombre) LIKE UPPER(:nombre)"),
	@NamedQuery(name="Socio.findAllM", query="select distinct p.socio from Prestamo p where p.fechalimitedevolucion<CURRENT_DATE "),
	
})
public class Socio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idsocio;

	private String direccion;

	private String email;

	private String nombre;

	@Version
	private Long version;

	//bi-directional many-to-one association to Prestamo
	@OneToMany(mappedBy="socio")
	private List<Prestamo> prestamos;

	public Socio() {
	}

	public Long getIdsocio() {
		return this.idsocio;
	}

	public void setIdsocio(Long idsocio) {
		this.idsocio = idsocio;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<Prestamo> getPrestamos() {
		return this.prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public Prestamo addPrestamo(Prestamo prestamo) {
		getPrestamos().add(prestamo);
		prestamo.setSocio(this);

		return prestamo;
	}

	public Prestamo removePrestamo(Prestamo prestamo) {
		getPrestamos().remove(prestamo);
		prestamo.setSocio(null);

		return prestamo;
	}

}