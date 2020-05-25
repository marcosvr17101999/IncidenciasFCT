package backing;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Prioridad;
import entidades.Usuario;
import services.ComentarioService;
import services.DepartamentoService;
import services.EstadoService;
import services.IncidenciasService;
import services.PrioriodadService;
import services.UsuarioService;

@Named
@SessionScoped
public class BackingNuevaIncidencia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7887047000890230473L;
	@EJB
	IncidenciasService incService;
	@EJB
	PrioriodadService priService;
	@EJB
	DepartamentoService depService;
	@EJB
	UsuarioService usuService;
	@EJB
	EstadoService estService;
	@EJB
	ComentarioService comService;
	public UsuarioService getUsuService() {
		return usuService;
	}
	
	public void setUsuService(UsuarioService usuService) {
		this.usuService = usuService;
	}
	
	Incidencia inc;
	Comentario com;
	String asunto="";
	String descrip="";
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	String username = ec.getRemoteUser();
	public ExternalContext getEc() {
		return ec;
	}
	public String getUsername() {
		return username;
	}
	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public PrioriodadService getPriService() {
		return priService;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setPriService(PrioriodadService priService) {
		this.priService = priService;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	private List<Departamento> listadoDepartamentos = null;
	private int idDepartamento = 0;
	
	private List<Prioridad> listadoPrioridad = null;
	private int idprioridad= 0;
	public DepartamentoService getDepService() {
		return depService;
	}
	public int getIdprioridad() {
		return idprioridad;
	}
	public void setIdprioridad(int idprioridad) {
		this.idprioridad = idprioridad;
	}
	public List<Prioridad> getListadoPrioridad() {
		return listadoPrioridad;
	}
	public void setDepService(DepartamentoService depService) {
		this.depService = depService;
	}
	public void setListadoPrioridad(List<Prioridad> listadoPrioridad) {
		this.listadoPrioridad = listadoPrioridad;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public IncidenciasService getIncService() {
		return incService;
	}
	public Incidencia getInc() {
		return inc;
	}
	public Comentario getCom() {
		return com;
	}
	public List<Departamento> getListadoDepartamentos() {
		return listadoDepartamentos;
	}
	public int getIdDepartamento() {
		return idDepartamento;
	}
	public void setIncService(IncidenciasService incService) {
		this.incService = incService;
	}
	public void setInc(Incidencia inc) {
		this.inc = inc;
	}
	public void setCom(Comentario com) {
		this.com = com;
	}
	public void setListadoDepartamentos(List<Departamento> listadoDepartamentos) {
		this.listadoDepartamentos = listadoDepartamentos;
	}
	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public BackingNuevaIncidencia() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void ini() {
		
			listadoPrioridad=priService.getPrioridad();
			listadoDepartamentos = depService.getDepartamentos();
		
	}
	public void nuevaIncidencia() {
		String pri=String.valueOf(idprioridad);
		Long idinc=incService.getUlt()+1;
		Prioridad p=priService.getPrioridadbyID(pri).get(0);
		Usuario u=usuService.getUsuarioById(username).get(0);
		Long com=comService.getUlt()+1;
		//System.out.println(com);
    	Estadoincidencia es=estService.getEstado("1").get(0);
		try {
			  incService.nuevaIncidencia(asunto,p,descrip,u,idinc,es,com);
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
		inc = new Incidencia();
		
	}
	
}
