package backing;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entidades.Comentario;
import entidades.Incidencia;
import services.IncidenciasService;

@Named
@SessionScoped
public class BackingModificacionIncidencia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 259662761053450997L;
	String idIncidencia;
	

	Incidencia i=new Incidencia();
	
	List<Comentario>lCom;
	IncidenciasService incService;
	public Incidencia getI() {
		return i;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public String getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public BackingModificacionIncidencia() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		
	System.out.println(i.getIdIncidencia());
	
	}
	
	
	public void guardarVariable() {
		lCom=i.getComentarios();
		for (Comentario comentario : lCom) {
			System.out.println(comentario.getIdcomentario()+"--"+
					comentario.getFechaComentario().getTime());
		}
			}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Comentario> getlCom() {
		return lCom;
	}
	public IncidenciasService getIncService() {
		return incService;
	}
	
	public void setlCom(List<Comentario> lCom) {
		this.lCom = lCom;
	}
	public void setIncService(IncidenciasService incService) {
		this.incService = incService;
	}

}	
