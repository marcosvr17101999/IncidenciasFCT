package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Estadoincidencia;
import entidades.Role;

/**
 * Session Bean implementation class EstadoService
 */
@Stateless
@LocalBean
public class EstadoService {

	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public EstadoService() {
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
   	public List<Estadoincidencia> getEstado(String id) {
   		return em.createNamedQuery("Estadoincidencia.findById").setParameter("id", id).getResultList();
   	}
    
    @SuppressWarnings("unchecked")
	public List<Estadoincidencia> getAll(){
    	return em.createNamedQuery("Estadoincidencia.findAll").getResultList();
    }
}
