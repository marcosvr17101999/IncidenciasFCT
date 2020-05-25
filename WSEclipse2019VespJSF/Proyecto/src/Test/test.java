package Test;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import entidades.Comentario;
import entidades.Incidencia;
import services.IncidenciasService;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("Proyecto");
		EntityManager em=emf.createEntityManager();
		
		
		
		System.out.println(
				2);
		em.close();
		emf.close();
		
	}

}
