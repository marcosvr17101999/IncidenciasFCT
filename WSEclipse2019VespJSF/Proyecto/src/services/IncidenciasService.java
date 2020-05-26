package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Prioridad;
import entidades.Usuario;
/**
 * Session Bean implementation class IncidenciasService
 */
@Stateless
@LocalBean
public class IncidenciasService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
	@SuppressWarnings("unchecked")
	public List<Incidencia> findByID(String id) {
		Query consulta=em.createNamedQuery("Incidencia.findById");
		return consulta.setParameter("id",id).getResultList();
	
	}
	
    public IncidenciasService() {
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
	public List<Incidencia> listadoIncidencias(int primerResultado, int maxResultados,String tipo,String User) {
    	Usuario u=em.find(Usuario.class,User);
    
     	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
     	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
		 listaIncidencia = consulta1.setParameter("tipo",tipo).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();
    	if(u.getGrupousuario().getId().getIdrol()==4){
    		System.out.println(4);
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByUser");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();
    	return listaIncidencia;
    	}else if(u.getGrupousuario().getId().getIdrol()==3) {

        	String dep=u.getDepartamentoBean().getDetalleDepartamento();
    		System.out.println(3);
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByDepartamento");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).setParameter("departamento",dep).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();
    	return listaIncidencia;
    	}
    	
		 return listaIncidencia;
    	
    	
	}
    @SuppressWarnings("unchecked")
	public List<Incidencia> listadoIncidencias2(int primerResultado, int maxResultados,String tipo) {
    	
    
     	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
     	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
		 listaIncidencia = consulta1.setParameter("tipo",tipo).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();
    	
    	
		 return listaIncidencia;
    	
    	
	}
    @SuppressWarnings("unchecked")
   	public List<Incidencia> allListadoIncidencias(int primerResultado, int maxResultados,String tipo) {
    	
    	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
    	List<Incidencia> listaIncidencia = consulta1.setParameter("tipo",tipo).setFirstResult(1).setMaxResults(maxResultados).getResultList();
    	return listaIncidencia;
   	}
    public long getUlt() {
    	Query consulta1 = em.createNamedQuery("Incidencia.findAll");
    	Incidencia i=(Incidencia) consulta1.getResultList().get(0);
    	return i.getIdIncidencia();
    }
    public long getTotal() {
		Query consulta = em.createQuery("select count(i) from Incidencia i");
		return (Long) consulta.getSingleResult();
	}
    @SuppressWarnings("unchecked")
    public long getTotalFiltro(String tipo,String User) {
    	Usuario u=em.find(Usuario.class,User);
        
     	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
     	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
		 listaIncidencia = consulta1.setParameter("tipo",tipo).getResultList();
    	if(u.getGrupousuario().getId().getIdrol()==4){
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByUser");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).getResultList();
    	return listaIncidencia.size();
    	}else if(u.getGrupousuario().getId().getIdrol()==3) {

        	String dep=u.getDepartamentoBean().getDetalleDepartamento();
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByDepartamento");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).setParameter("departamento",dep).getResultList();
    	return listaIncidencia.size();
    	}
    	
		 return listaIncidencia.size();
    	
	}
    @SuppressWarnings("unchecked")
	public long getTotalFiltro2(String tipo) {
        
     	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
     	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
		 listaIncidencia = consulta1.setParameter("tipo",tipo).getResultList();
    	
    	
		 return listaIncidencia.size();
    	
	}
    
    public void nuevaIncidencia(String asunto,Prioridad p,String descrip,Usuario u,Long id,Estadoincidencia es,Long com) {
    	
    	
    	Incidencia i=new Incidencia();
    	i.setDepartamento(null);
    	i.setPrioridadBean(p);
    	i.setUsuarioBean(u);
    	Date date = new Date();
    	
    	i.setDetalleIncidencia(asunto);
    	i.setFechaIncidencia(date);
    	i.setIdIncidencia(id);
    	i.setEstadoincidencia(es);
    	
    	
    	Comentario c=new Comentario();
    	c.setFechaComentario(date);
    	c.setDetallesComentario(descrip);
    	c.setIncidencia(i);
    	c.setUsuario(u);
    	c.setIdcomentario(com);
    	
    	try {
    		
    		em.persist(i);
    		em.persist(c);
    		List<Comentario>comentarios=i.getComentarios();
        	comentarios.add(c);
        	i.setComentarios(comentarios);
    		em.persist(i);
		}catch (RollbackException rbe) {
			throw rbe;
		}
    }
  
    public void actualizarIncidencia(Incidencia i)throws RollbackException {
		try {
			em.merge(i);
		} catch (EJBException e) {
			throw e;
		}
	}
    @SuppressWarnings("unchecked")
   	public List<Incidencia> listadoIncidencias3(int primerResultado, int maxResultados,String tipo,String tipo2) { 
        	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
        	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo2");
   		 listaIncidencia = consulta1.setParameter("tipo",tipo).setParameter("tipo2",tipo2).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();	
   		 return listaIncidencia;
   	}
    @SuppressWarnings("unchecked")
   	public long getTotalFiltro3(String tipo,String tipo2) {
           
        	List<Incidencia> listaIncidencia = new ArrayList<Incidencia>();
        	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo2");
   		 listaIncidencia = consulta1.setParameter("tipo",tipo).setParameter("tipo2",tipo2).getResultList();
       	
       	
   		 return listaIncidencia.size();
       	
   	}
    public void eliminarIncidencia(Long a) {
    	System.out.println(a+" si estoy entrando mirame ---------------");
    
		Incidencia i=em.find(Incidencia.class,a);
		System.out.println(i.getIdIncidencia()+"----------------id que se recibe en paquete");
    	if(i!=null) {
		em.remove(i);
			
			}
    	
				
		
		
}
}
