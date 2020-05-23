package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Incidencia;

/**
 * Session Bean implementation class ComentarioService
 */
@Stateless
@LocalBean
public class ComentarioService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public ComentarioService() {
        // TODO Auto-generated constructor stub
    }
    public long getUlt() {
    	Query consulta1 = em.createNamedQuery("Comentario.findAll");
    	Comentario i=(Comentario) consulta1.getResultList().get(0);
    	return i.getIdcomentario();
    }
    public void newComentario(Comentario c) {
    	try {
    		em.persist(c);
		} catch (RollbackException e) {
			// TODO: handle exception
		}
    	
    }
}
