package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Autor;
import entidades.Ejemplar;


/**
 * Session Bean implementation class LibroService
 */
@Stateless
@LocalBean
public class LibroService {
	@PersistenceContext(unitName="BibliotecaJSF")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public LibroService() {
        // TODO Auto-generated constructor stub
    }
    public EntityManager getEntityManager(){
    	return em;
    	}
    @SuppressWarnings("unchecked")
	public List<Object[]> listarlibro(String campo,String valor){
    	String titulo = null,isbn = null,nombre=null;
    	if(campo.equals("autor")) {
    		titulo="%";
    		isbn="%";
    		nombre=valor;
    	}
    	if(campo.equals("titulo")) {
    		titulo=valor;
    		isbn="%";
    		nombre="%";
    	}
    	if(campo.equals("isbn")) {
    		titulo="%";
    		isbn=valor;
    		nombre="%";
    	}
		EntityManager em=getEntityManager();
		Query ordenSQL=em.createNativeQuery("SELECT L.ISBN ISBN,L.TITULO TITULO,A.NOMBRE AUTOR,TOTALES,PRESTADOS,DISPONIBLES " + 
				"FROM LIBRO L,AUTOR A,( " + 
				"SELECT A.ISBN,A.TOTALES TOTALES,NVL(B.PRESTADOS,0)PRESTADOS,NVL((TOTALES-PRESTADOS),TOTALES) DISPONIBLES " + 
				"FROM " + 
				"    (SELECT L.ISBN,COUNT(*)TOTALES " + 
				"        FROM LIBRO L,EJEMPLAR E " + 
				"        WHERE L.ISBN=E.ISBN " + 
				"        AND E.BAJA='N' " + 
				"        GROUP BY L.ISBN)A LEFT JOIN( " + 
				"SELECT L.ISBN,COUNT(*) PRESTADOS " + 
				"FROM PRESTAMO P,LIBRO L,EJEMPLAR E " + 
				"WHERE P.IDEJEMPLAR=E.IDEJEMPLAR " + 
				"AND E.ISBN=L.ISBN " + 
				"GROUP BY L.ISBN)B " + 
				"ON A.ISBN=B.ISBN)X " + 
				"WHERE L.ISBN=X.ISBN " + 
				"AND A.IDAUTOR=L.IDAUTOR " + 
				"AND TRANSLATE(UPPER(A.NOMBRE),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') " + 
				"AND TRANSLATE(UPPER(L.TITULO),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') " + 
				"AND L.ISBN LIKE ? " + 
				"ORDER BY AUTOR,TITULO");
		ordenSQL.setParameter(1, "%"+nombre+"%");
		ordenSQL.setParameter(2, "%"+titulo+"%");
		ordenSQL.setParameter(3, "%"+isbn+"%");
		return ordenSQL.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Ejemplar> getEjemplares(String isbn){
		List<Ejemplar> ejemplares=null;
		EntityManager em=getEntityManager();
		Query consulta=em.createQuery("select distinct(e)  from Ejemplar e where e.baja =\"N\" " + 
				"and e.libro.isbn like :isbn " + 
				"and e.idejemplar not in(select p.ejemplar.idejemplar from Prestamo p) ");
		consulta.setParameter("isbn", isbn);
		ejemplares=consulta.getResultList();
		return ejemplares;	
	}
	public void eliminarEjemplar(List<Long> idejemplar) {
		for (Long long1 : idejemplar) {
			Ejemplar e=em.find(Ejemplar.class,long1);
			e.setBaja("S");
		}
		
		
		
		
	}
}
