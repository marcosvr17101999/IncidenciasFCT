package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Ejemplar;
import entidades.Libro;
import services.LibroService;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class BackingListadoLibros implements Serializable {
	private Libro libro=null;
	private String inicialesBusqueda=null;
	private String tipoBusqueda=null;
	private List<Object[]> resultado=null;
	private List<Ejemplar>listadoEjemplares=null;
	private List<Long> seleccionEjemplar=null;
	@EJB
	private LibroService libroservice=null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7282660236242214383L;
	public BackingListadoLibros() {
		// TODO Auto-generated constructor stub
	}
	public Libro getLibro() {
		return libro;
	}
	public String getInicialesBusqueda() {
		return inicialesBusqueda;
	}
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	public List<Object[]> getResultado() {
		return resultado;
	}
	public List<Ejemplar> getListadoEjemplares() {
		return listadoEjemplares;
	}
	
	public LibroService getLibroservice() {
		return libroservice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public void setInicialesBusqueda(String inicialesBusqueda) {
		this.inicialesBusqueda = inicialesBusqueda;
	}
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	public void setResultado(List<Object[]> resultado) {
		this.resultado = resultado;
	}
	public void setListadoEjemplares(List<Ejemplar> listadoEjemplares) {
		this.listadoEjemplares = listadoEjemplares;
	}
	
	
	public List<Long> getSeleccionEjemplar() {
		return seleccionEjemplar;
	}
	public void setSeleccionEjemplar(List<Long> seleccionEjemplar) {
		this.seleccionEjemplar = seleccionEjemplar;
	}
	public void setLibroservice(LibroService libroservice) {
		this.libroservice = libroservice;
	}
	public void listarEjemplares(String libro) {
		this.listadoEjemplares=libroservice.getEjemplares(libro);
	}
	public void getLibros() {
		resultado=libroservice.listarlibro(tipoBusqueda,inicialesBusqueda);
	}
	public void eliminarEjemplar() {
			this.libroservice.eliminarEjemplar(this.seleccionEjemplar);
			FacesContext context = FacesContext.getCurrentInstance(); 
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
			context.getViewRoot().getLocale());
			context.addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO, archivomensajes.getString("generico.registroEliminado"),
			archivomensajes.getString("generico.registroEliminado")));
			getLibros();
			this.seleccionEjemplar=null;
			
		
	}
}
