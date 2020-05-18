package services;

import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Autor;
import entidades.Socio;
import exceptions.TresEquisNombreException;

/**
 * Session Bean implementation class AutorBean
 */
@Stateless

public class AutorService {
	@PersistenceContext(unitName="BibliotecaJSF")
	private EntityManager em;
	public AutorService(){
	} 
	public Autor crearAutor(Autor a)throws TresEquisNombreException{
	em.persist(a); 
	if(a.getNombre().contains("XXX")) {
		FacesContext context=FacesContext.getCurrentInstance();
		ResourceBundle archivomensajes=ResourceBundle.getBundle("resources.application",context.getViewRoot().getLocale());
		String mensaje=archivomensajes.getString("excepciontresequis");
		TresEquisNombreException ex=new TresEquisNombreException(mensaje);
		throw ex;
	}
	
	return a;
	}
	public EntityManager getEntityManager(){
	return em;
	}
	public void eliminarAutor(long a) {
			Autor an = em.find(Autor.class, a);
			System.out.println(a);
			if(an!=null) {
			em.remove(an);
			
			}
	}
	
	@SuppressWarnings("unchecked")
	public List<Autor> autorPorNombre(String autor) {
		Query consulta = em.createNamedQuery("Autor.findByName");
		List<Autor> listaAutor = consulta.setParameter("nombre", "%" + autor + "%").getResultList();
		/*
		 * for(Socio s:listaSocios){ em.refresh(s); }
		 */
		return listaAutor;
	}
	
	
}
