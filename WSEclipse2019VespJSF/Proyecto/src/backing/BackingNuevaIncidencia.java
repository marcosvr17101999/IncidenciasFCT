package backing;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Incidencia;
import services.IncidenciasService;

@Named
@ViewScoped
public class BackingNuevaIncidencia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7887047000890230473L;
	IncidenciasService incService;
	Incidencia inc;
	Comentario com;
	public BackingNuevaIncidencia() {
		// TODO Auto-generated constructor stub
	}
	
	public void nuevaIncidencia() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado con éxito",
					"Registro creado con éxito"));
		} catch (RollbackException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			String mensaje = e.getCause().getCause().getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		}
		
	}
	
}
