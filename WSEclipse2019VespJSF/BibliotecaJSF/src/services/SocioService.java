package services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import entidades.Prestamo;
import entidades.Socio;

/**
 * Session Bean implementation class SocioService
 */
@Stateless
public class SocioService {
	@PersistenceContext(unitName = "BibliotecaJSF")
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public SocioService() {
// TODO Auto-generated constructor stub
	}

	public Socio crearSocio(Socio s) {
		em.persist(s);
		return s;
	}
	public long diasDemora() {
		long diasDemora = 0;
		String textConsulta="";
		Query consulta=em.createQuery(textConsulta);
		
		return diasDemora;
				
	}
	
//	  public Socio actualizarSocio(Socio s) {
//		  return em.merge(s); 
//		  }
	@SuppressWarnings("unchecked")
	public List<Prestamo> prestamosFueraPlazo(Long socio) throws Exception {
		List<Prestamo> listaprestamos;
		String textoConsulta = "select p " + 
				"		from Prestamo p " + 
				"		where p.socio.idsocio=:idsocio " + 
				"		and p.fechalimitedevolucion < CURRENT_DATE ";
		Query consulta = em.createQuery(textoConsulta);
		consulta.setParameter("idsocio", socio);
		listaprestamos= consulta.getResultList();
	
		
		return listaprestamos;
	}
	public void actualizarSocio(Socio s)throws RollbackException {
		try {
			em.merge(s);
		} catch (EJBException e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Socio> sociosPorNombre(String socio) {
		Query consulta = em.createNamedQuery("Socio.findByName");
		List<Socio> listaSocios = consulta.setParameter("nombre", "%" + socio + "%").getResultList();
		/*
		 * for(Socio s:listaSocios){ em.refresh(s); }
		 */
		return listaSocios;
	}

	/**********************************************************************/
	public Socio getSocioById(long idsocio) {
		return em.find(Socio.class, idsocio);
	}
	
	/************************************************************************/
	@SuppressWarnings("unchecked")
	public List<Socio> sociosEnRango(int primerResultado, int maxResultados) {
		Query consulta = em.createNamedQuery("Socio.findAll");
		consulta.setFirstResult(primerResultado);
		consulta.setMaxResults(maxResultados);
		List<Socio> listaSocios = consulta.getResultList();
		/*
		 * for(Socio s:listaSocios){ em.refresh(s); }
		 */
		return listaSocios;
	}
	@SuppressWarnings("unchecked")
	public List<Socio> sociosEnRangoM(int primerResultado, int maxResultados) {
		Query consulta = em.createNamedQuery("Socio.findAllM");
		consulta.setFirstResult(primerResultado);
		consulta.setMaxResults(maxResultados);
		List<Socio> listaSocios = consulta.getResultList();
		/*
		 * for(Socio s:listaSocios){ em.refresh(s); }
		 */
		return listaSocios;
	}
/******************************************************************************/
	@SuppressWarnings("unchecked")
	public List<Socio> listadoSociosMorosos() {
		List<Socio> listadoSociosMorosos;
		String textoConsulta = "select distinct p.socio from Prestamo p where p.fechalimitedevolucion<CURRENT_DATE ";
		Query consulta = em.createQuery(textoConsulta);
		listadoSociosMorosos= consulta.getResultList();
		em.close();
		 return listadoSociosMorosos;
	}
	public long getTotal() {
		Query consulta = em.createQuery("select count(s) from Socio s");
		return (Long) consulta.getSingleResult();
	}
	public long getTotalMorosos() {
		Query consulta = em.createQuery("select count(distinct p.socio) from Prestamo p where p.fechalimitedevolucion<CURRENT_DATE");
		return (Long) consulta.getSingleResult();
	}

}