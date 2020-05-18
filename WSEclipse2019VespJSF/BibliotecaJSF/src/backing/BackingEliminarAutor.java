package backing;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Autor;
import services.AutorService;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class BackingEliminarAutor implements Serializable {
	private Autor autor;
	private long idautor;
	private String inicialesBuscarAutor;
	private List<Autor> listadoAutores;
	private List<Autor> autoresBorrar;
	private long autorBorrar;
	

	public long getAutorBorrar() {
		return autorBorrar;
	}
	public void setAutorBorrar(long autorBorrar) {
		this.autorBorrar = autorBorrar;
	}

	public void setAutoresBorrar(List<Autor> autoresBorrar) {
		this.autoresBorrar = autoresBorrar;
	}

	

	public List<Autor> getAutoresBorrar() {
		return autoresBorrar;
	}

	public void setAutorBorrar(List<Autor> autoresBorrar) {
		this.autoresBorrar = autoresBorrar;
	}

	public List<Autor> getListadoAutores() {
		return listadoAutores;
	}

	public void setListadoAutores(List<Autor> listadoAutores) {
		this.listadoAutores = listadoAutores;
	}

	public String getInicialesBuscarAutor() {
		return inicialesBuscarAutor;
	}
	public void setInicialesBuscarAutor(String inicialesBuscarAutor) {
		this.inicialesBuscarAutor = inicialesBuscarAutor;
	}

	public AutorService getAutorservice() {
		return autorservice;
	}

	
	public void setAutorservice(AutorService autorservice) {
		this.autorservice = autorservice;
	}
	public void sociosPorNombreAjax() {
		listadoAutores = autorservice.autorPorNombre(inicialesBuscarAutor);
	}
	

	public long getIdautor() {
		return idautor;
	}

	public void setIdautor(long idautor) {
		this.idautor = idautor;
	}

	@EJB
	private AutorService autorservice;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BackingEliminarAutor() {
		// TODO Auto-generated constructor stub
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTotalAutoresCandidatos() {
		return (listadoAutores != null) ? listadoAutores.size() : 0;
	}
	public void borrarAutor() {
		System.out.println(this.autorBorrar);
		this.autorservice.eliminarAutor(this.autorBorrar);
		FacesContext context = FacesContext.getCurrentInstance(); 
		ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
		context.getViewRoot().getLocale());
		context.addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, archivomensajes.getString("generico.registroEliminado"),
		archivomensajes.getString("generico.registroEliminado")));
		this.sociosPorNombreAjax();
		
	}
}
