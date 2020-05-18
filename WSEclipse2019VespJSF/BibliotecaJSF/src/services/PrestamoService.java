package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

import entidades.Ejemplar;
import entidades.Prestamo;
import entidades.Socio;
import exceptions.PrestamoException;

/**
 * Session Bean implementation class PrestamoService
 */
@Stateless
@LocalBean
public class PrestamoService {
	@PersistenceContext(unitName="BibliotecaJSF")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public PrestamoService() {
        // TODO Auto-generated constructor stub
    }
    public EntityManager getEntityManager(){
    	return em;
    	}
    public void instertarPrestamo(Prestamo pr) throws PrestamoException,RollbackException {
		EntityManager em=getEntityManager();
		EntityTransaction tx=em.getTransaction();
		Ejemplar e=em.find(Ejemplar.class, pr.getEjemplar().getIdejemplar());
		Socio s=em.find(Socio.class, pr.getSocio().getIdsocio());
		Prestamo p=new Prestamo();
		Prestamo prestamobuscado=em.find(Prestamo.class,  pr.getEjemplar().getIdejemplar());
		if(s==null) {
			throw new PrestamoException("El socio indicado no existe.");
		}
		if(e==null || (e==null && e.getBaja()=="S")) {
			throw new PrestamoException("El ejemplar indicado no existe.");
		}
		if (prestamobuscado!=null) {
			throw new PrestamoException("El ejemplar ya está en préstamo.");
		}
		p.setEjemplar(e);
		p.setSocio(s);
		try {
			tx.begin();
			em.persist(p);
			tx.commit();
		}catch (RollbackException rbe) {
			throw rbe;
		}finally {
			em.close();
		}
	}
    public Prestamo findPrestamo(Prestamo p) {
    	EntityManager em=getEntityManager();
    	Socio s=em.find(Socio.class,p.getSocio().getIdsocio());
    	p.setSocio(s);
    	return p;
    	
    }
}
