package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Incidencia;
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
	public List<Incidencia> listadoIncidencias(int primerResultado, int maxResultados,String tipo) {
    	
    	Query consulta1 = em.createNamedQuery("Incidencia.findByTipo");
    	List<Incidencia> listaIncidencia = consulta1.setParameter("tipo",tipo).setFirstResult(primerResultado).setMaxResults(maxResultados).getResultList();
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
    public long getTotalFiltro(String tipo) {
    	Query consulta = em.createQuery("SELECT count(inc) FROM Incidencia inc where UPPER(inc.estadoincidencia.idEstado) LIKE UPPER(:tipo)");
    	consulta.setParameter("tipo",tipo);
    	return (Long) consulta.getSingleResult();
    	
    	
	}
}
