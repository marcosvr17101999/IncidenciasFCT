package backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Usuario;
import services.UsuarioService;

@Named
@SessionScoped
public class BackingPassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 25940941818992102L;

	@EJB
	UsuarioService usuarioService;
	String password="";
	String password1="";
	String password2="";
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	String username = ec.getRemoteUser();

	public BackingPassword() {
		// TODO Auto-generated constructor stub
	}

	public void cambiarPassword() {
		Usuario u=usuarioService.getUsuario(username);
		if (password1.equals(password2)&& u.getPassword().equals(password)) {
			usuarioService.updatePassword(password1, username);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña cambiada con éxito",
					"Contraseña cambiada con éxito"));
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Las contraseñas no coinciden",
					"Las contraseñas no coinciden"));
	}
	}

	public String getPassword1() {
		return password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPassword() {
		return password;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}