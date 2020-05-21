package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Incidencia;
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
   	public List<Incidencia> allListadoIncidencias(int primerResultado, int maxResultados,String tipo) {
    	
    	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
    	List<Incidencia> listaIncidencia = consulta1.setParameter("tipo",tipo).setFirstResult(1).setMaxResults(maxResultados).getResultList();
    	return listaIncidencia;
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
    		System.out.println(4);
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByUser");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).getResultList();
    	return listaIncidencia.size();
    	}else if(u.getGrupousuario().getId().getIdrol()==3) {

        	String dep=u.getDepartamentoBean().getDetalleDepartamento();
    		System.out.println(3);
    		listaIncidencia.clear();
    		Query consulta = em.createNamedQuery("Incidencia.findByDepartamento");
    	listaIncidencia = consulta.setParameter("email",User).setParameter("tipo",tipo).setParameter("departamento",dep).getResultList();
    	return listaIncidencia.size();
    	}
    	
		 return listaIncidencia.size();
    	
    
    	
    	
	}
}
