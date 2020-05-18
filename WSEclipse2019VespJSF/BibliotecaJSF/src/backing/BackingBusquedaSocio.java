package backing;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Socio;
import services.SocioService;

@Named
@SessionScoped
public class BackingBusquedaSocio implements Serializable {
	/**
	*
	*/
	private static final long serialVersionUID = 2521212970858677985L;
	@EJB
	private SocioService socioService;
	private String inicialesBuscarSocio;
// recogera el resultado de las consultas y hace de
// backing para el objeto h:datatable
	private List<Socio> listadoSocios;

// Guarda el socio asociado al vínculo editar.
	public BackingBusquedaSocio() {
	}

	public String getInicialesBuscarSocio() {
		return inicialesBuscarSocio;
	}

	public void setInicialesBuscarSocio(String inicialesBuscarSocio) {
		this.inicialesBuscarSocio = inicialesBuscarSocio;
	}

	public List<Socio> getListadoSocios() {
		return listadoSocios;
	}

	public void setListadoSocios(List<Socio> listadoSocios) {
		this.listadoSocios = listadoSocios;
	}

	public void sociosPorNombreAjax() {
		listadoSocios = socioService.sociosPorNombre(inicialesBuscarSocio);
	}

	public int getTotalSociosCandidatos() {
		return (listadoSocios != null) ? listadoSocios.size() : 0;
	}

	public String editarSocio() {
		return "/admin/editsocio.xhtml?pagina=/admin/getsocio.xhtml&faces-redirect=true";
		}
}