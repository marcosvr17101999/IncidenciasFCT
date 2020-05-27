package backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Usuario;
import services.UsuarioService;

@Named
@SessionScoped
public class BackingInfo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8821869489209775262L;
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	String username = ec.getRemoteUser();
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ExternalContext getEc() {
		return ec;
	}
	public String getUsername() {
		return username;
	}
	public Usuario getU() {
		return u;
	}
	public UsuarioService getUsuService() {
		return usuService;
	}
	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setU(Usuario u) {
		this.u = u;
	}
	public void setUsuService(UsuarioService usuService) {
		this.usuService = usuService;
	}
	Usuario u;
	@EJB
	UsuarioService usuService;
	public BackingInfo() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		u=usuService.getUsuario(username);
		
	}
	
}
