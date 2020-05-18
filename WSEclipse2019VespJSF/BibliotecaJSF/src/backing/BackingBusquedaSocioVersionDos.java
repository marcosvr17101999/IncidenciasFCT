package backing;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;

import com.sun.istack.internal.logging.Logger;

import entidades.Socio;
import services.SocioService;

@Named
@ViewScoped
public class BackingBusquedaSocioVersionDos implements Serializable{
	
	private static final long serialVersionUID = 2521212970858677985L;
	@EJB
	private SocioService socioService;
	private String inicialesBuscarSocio;
// recogera el resultado de las consultas y hace de
// backing para el objeto h:datatable
	private List<Socio> listadoSocios;
	private Socio socioseleccionado;
	private String url;
	private long idsocio;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public SocioService getSocioService() {
		return socioService;
	}
	@PostConstruct
	public void init() {
		this.socioseleccionado=this.socioService.getSocioById(idsocio);
		
	}
	public void setSocioService(SocioService socioService) {
		this.socioService = socioService;
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

	public BackingBusquedaSocioVersionDos() {
		// TODO Auto-generated constructor stub
	}
	public Socio getSocioseleccionado() {
		return socioseleccionado;
	}
	public void setSocioseleccionado(Socio socioseleccionado) {
		this.socioseleccionado = socioseleccionado;
	}
	public long getIdsocio() {
		return idsocio;
	}
	public void setIdsocio(long idsocio) {
		this.idsocio = idsocio;
	}
	public String editarSocio() {
		return "/admin/editsocio.xhtml?pagina=/admin/getsocio.xhtml&faces-redirect=true";
		}
	public String cancelar() {
		return this.url+"?face-redirect=true";
		
	}
	public void actualizarSocioSeleccionado() {
		try {
			socioService.actualizarSocio(socioseleccionado);
			FacesContext context = FacesContext.getCurrentInstance(); 
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
			context.getViewRoot().getLocale());
			context.addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO, archivomensajes.getString("generico.registroActualizado"),
			archivomensajes.getString("generico.registroActualizado")));
		} catch (EJBException e) {
			// TODO: handle exception
			FacesContext context=FacesContext.getCurrentInstance();
			if(e.getCause()instanceof OptimisticLockException)
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"El registro cambio",e.getMessage()));
		}
		
		}
}
