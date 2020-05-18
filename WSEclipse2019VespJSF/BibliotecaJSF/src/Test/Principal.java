/**
 * 
 */
package Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Prestamo;
import entidades.Socio;

/**
 * @author vespertino
 *
 */
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("BibliotecaJSF");
		EntityManager em=emf.createEntityManager();
		
		
		Prestamo p=new Prestamo();
		p=em.find(Prestamo.class, 4);
		System.out.println(p.getSocio().getNombre());
		em.close();
		emf.close();
	}

}
