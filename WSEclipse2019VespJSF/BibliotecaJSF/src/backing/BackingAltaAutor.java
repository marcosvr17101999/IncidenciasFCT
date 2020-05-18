package backing;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Autor;
import exceptions.TresEquisNombreException;
import services.AutorService;

@Named
@RequestScoped
public class BackingAltaAutor implements Serializable {

	private static final long serialVersionUID = -7436398052842852631L;
	@EJB
	private AutorService ejbAutor;
	Autor autor = new Autor();

	public BackingAltaAutor() {
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void nuevoAutor() {
		try {
			ejbAutor.crearAutor(autor);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							archivomensajes.getString("generico.registroCreadoConExito"),
							archivomensajes.getString("generico.registroCreadoConExito")));
			System.out.println("Antes de llamar a prepararCrearAutor por tanto debe haber nueva instancia");
		} catch (TresEquisNombreException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO,ex.getMessage(),ex.getMessage()));
		}catch (Exception e) {
			// TODO: handle exception
			String mensaje =e.getCause().getCause().getMessage();
			FacesContext context=FacesContext.getCurrentInstance();
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,mensaje,mensaje));
		}
		autor = new Autor(); // reiniciar el contenedor del nuevo autor si trabajo con SessionScoped o bien
								// para eliminar
		// datos del registro recien creado.
	}

}
