package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Prioridad;

/**
 * Session Bean implementation class PrioriodadService
 */
@Stateless
@LocalBean
public class PrioriodadService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public PrioriodadService() {
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
	public List<Prioridad> getPrioridad() {
		return em.createNamedQuery("Prioridad.findAll").getResultList();
	}
    @SuppressWarnings("unchecked")
	public List<Prioridad> getPrioridadbyID(String id) {
		return em.createNamedQuery("Prioridad.findAllbyId").setParameter("id", id).getResultList();
	}
}
