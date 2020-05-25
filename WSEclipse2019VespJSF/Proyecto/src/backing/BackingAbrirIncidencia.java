package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Usuario;
import services.ComentarioService;
import services.DepartamentoService;
import services.EstadoService;
import services.IncidenciasService;
import services.UsuarioService;

@Named
@RequestScoped
public class BackingAbrirIncidencia implements Serializable{
	/**
	 * 
	 */
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	String username = ec.getRemoteUser();
	private static final long serialVersionUID = -7061901208917395556L;
	Long idIncidencia;
	Long idDepartamento;
	Departamento dep;
	public ExternalContext getEc() {
		return ec;
	}
	public String getUsername() {
		return username;
	}
	public Departamento getDep() {
		return dep;
	}
	public UsuarioService getUsuService() {
		return usuService;
	}
	public ComentarioService getComSerivce() {
		return comSerivce;
	}
	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setDep(Departamento dep) {
		this.dep = dep;
	}
	public void setUsuService(UsuarioService usuService) {
		this.usuService = usuService;
	}
	public void setComSerivce(ComentarioService comSerivce) {
		this.comSerivce = comSerivce;
	}
	String Comentario;
	public Long getIdDepartamento() {
		return idDepartamento;
	}
	public DepartamentoService getDepService() {
		return depService;
	}
	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	public void setDepService(DepartamentoService depService) {
		this.depService = depService;
	}
	@EJB
	UsuarioService usuService;
	List<Departamento>departamentos;
	@EJB
	ComentarioService comSerivce;
	@EJB
	DepartamentoService depService;
	@EJB
	EstadoService estService;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}
	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	Incidencia i=new Incidencia();
	@EJB
	IncidenciasService incService;
	public Long getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(Long idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public Incidencia getI() {
		return i;
	}
	public IncidenciasService getIncService() {
		return incService;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public void setIncService(IncidenciasService incService) {
		this.incService = incService;
	}
	public BackingAbrirIncidencia() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void ini() {
		departamentos=depService.getDepartamentos();
				//System.out.println("size"+departamentos.size());
		
		}
	public void mostrar() {
		System.out.println("inc--->"+idIncidencia);
		System.out.println("dep--->"+idDepartamento);
		System.out.println("com--->"+Comentario);
		Long co=comSerivce.getUlt()+1;
		Date date = new Date();
		Incidencia i = incService.findByID(String.valueOf(idIncidencia)).get(0);
		Departamento dep=depService.getDepartamentoById(idDepartamento).get(0);
		i.setDepartamento(dep);
		Usuario u=usuService.getUsuarioById(username).get(0);
		Comentario c=new Comentario();
    	c.setFechaComentario(date);
    	c.setDetallesComentario(Comentario);
    	c.setIncidencia(i);
    	c.setUsuario(u);
    	c.setIdcomentario(co);
    	String l=String.valueOf(2);
    	Estadoincidencia es=estService.getEstado(l).get(0);
    	try {
    		System.out.println(i.getDepartamento().getIdDepartamento());
    		comSerivce.newComentario(c);
    		i.setEstadoincidencia(es);
    		
    		
    		incService.actualizarIncidencia(i);
    		
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
    	
	}
	public EstadoService getEstService() {
		return estService;
	}
	public void setEstService(EstadoService estService) {
		this.estService = estService;
	}
}
