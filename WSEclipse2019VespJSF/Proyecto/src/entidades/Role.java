package entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "ROLES" database table.
 * 
 */
@Entity
@Table(name="\"ROLES\"")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long idRoles;

	@Column(name="DETALLE")
	private String detalleRoles;

	public Role() {
	}

	public Long getIdRoles() {
		return this.idRoles;
	}

	public void setIdRoles(Long idRoles) {
		this.idRoles = idRoles;
	}

	public String getDetalleRoles() {
		return this.detalleRoles;
	}

	public void setDetalleRoles(String detalleRoles) {
		this.detalleRoles = detalleRoles;
	}

}