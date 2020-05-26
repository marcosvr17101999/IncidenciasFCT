package backing;


import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Role;
import entidades.Usuario;
import services.ComentarioService;
import services.DepartamentoService;
import services.EstadoService;
import services.IncidenciasService;
import services.RolesService;
import services.UsuarioService;

@Named
@SessionScoped
public class BackingModificacionIncidencia implements Serializable {
	/**
	 * 
	 */
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	@EJB
	private RolesService rolesService;
	private static final long serialVersionUID = 259662761053450997L;
	String idIncidencia;
	String idDepartamento;
	Usuario u;
	String username = ec.getRemoteUser();
	@EJB
	UsuarioService usuService;
	@EJB
	EstadoService estService;
	@EJB
	ComentarioService comService;
	@EJB
	DepartamentoService depService;
	String comentario;
	Long estado;
	Incidencia i;
	
	List<Estadoincidencia>estDisp;
	
	private List<Departamento> listadoDepartamentos = null;
	private List<Comentario>lisComen;
	List<Comentario>lCom;
	@EJB
	IncidenciasService incService;
	@EJB
	private DepartamentoService departamentoService;
	
	private List<Role> listadoRoles = null;
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public ExternalContext getEc() {
		return ec;
	}
	public String getUsername() {
		return username;
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
	public void setUsuService(UsuarioService usuService) {
		this.usuService = usuService;
	}
	

	public Incidencia getI() {
		return i;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	
	public BackingModificacionIncidencia() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		System.out.println(idIncidencia);
		u=usuService.getUsuarioById(username).get(0);
		System.out.println(u.getEmail());
		lisComen=comService.getComen(idIncidencia);
		listadoDepartamentos = departamentoService.getDepartamentos();
		
		}
	
	
	public RolesService getRolesService() {
		return rolesService;
	}
	public List<Role> getListadoRoles() {
		return listadoRoles;
	}
	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}
	public void setListadoRoles(List<Role> listadoRoles) {
		this.listadoRoles = listadoRoles;
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
	public Usuario getU() {
		return u;
	}
	public void setU(Usuario u) {
		this.u = u;
	}
	
	public List<Departamento> getListadoDepartamentos() {
		return listadoDepartamentos;
	}
	public void setListadoDepartamentos(List<Departamento> listadoDepartamentos) {
		this.listadoDepartamentos = listadoDepartamentos;
	}
	public String getIdIncidencia() {
		return idIncidencia;
	}
	public String getIdDepartamento() {
		return idDepartamento;
	}
	public DepartamentoService getDepartamentoService() {
		return departamentoService;
	}
	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}
	
	
	public void guardarVariable() {
		
		System.out.println(estado);
		System.out.println(idDepartamento);
		System.out.println(comentario);
		if(idDepartamento!=null) {
			int idDepartament=Integer.parseInt(idDepartamento);
			Departamento dep=depService.getDepartamentoById(idDepartament).get(0);
			i.setDepartamento(dep);
		}
		
		Long co=comService.getUlt()+1;
		System.out.println(co+"--------a-----------a");
		
		Date date = new Date();
		
		Usuario u=usuService.getUsuarioById(username).get(0);
		Comentario c=new Comentario();
		c.setFechaComentario(date);
    	c.setDetallesComentario(comentario);
    	c.setIncidencia(i);
    	c.setUsuario(u);
    	c.setIdcomentario(co);
    	String l;
    	if(estado!=null) {
    	l=String.valueOf(estado);
    	System.out.println("estado no nulo");
    	}else {
    		System.out.println("estado  nulo");
        		
        		Long l2=i.getEstadoincidencia().getIdEstado();
        				l=String.valueOf(l2);
    	}
    	System.out.println(l+"es qaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	Estadoincidencia es=estService.getEstado(l).get(0);
		
    	List<Comentario> list=i.getComentarios();
		list.add(c);
		i.setComentarios(list);
		
		i.setEstadoincidencia(es);
		System.out.println(i.getIdIncidencia()+"---------IdIncidencia");
		try {
    		
    		comService.newComentario(c);
    			
    		incService.actualizarIncidencia(i);
    		for (Comentario comentario : i.getComentarios()) {
    			System.out.println(comentario.getIdcomentario()+"------"+comentario.getDetallesComentario());
    		}
    	FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado con éxito",
				"Registro creado con éxito"));
}catch (RollbackException e) {
	e.printStackTrace();
} catch (Exception e) {
	String mensaje = e.getCause().getCause().getMessage();
	FacesContext context = FacesContext.getCurrentInstance();
	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
	
	
	
	
}
		System.out.println("eo");
		
	}
	public List<Comentario> getLisComen() {
		return lisComen;
	}
	public void setLisComen(List<Comentario> lisComen) {
		this.lisComen = lisComen;
	}
	
}	
