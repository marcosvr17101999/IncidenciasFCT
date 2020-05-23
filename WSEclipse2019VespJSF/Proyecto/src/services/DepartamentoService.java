package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Departamento;

/**
 * Session Bean implementation class DepartamentoService
 */
@Stateless
@LocalBean
public class DepartamentoService {

	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;

	public DepartamentoService() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Departamento> getDepartamentoById(int id) {
		String id1=String.valueOf(id);
		return em.createNamedQuery("Departamento.findId").setParameter("id", id1)
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Departamento> getDepartamentoById(Long id) {
		String id1=String.valueOf(id);
		return em.createNamedQuery("Departamento.findId").setParameter("id", id1)
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Departamento> getDepartamentos() {
		return em.createNamedQuery("Departamento.findAll").getResultList();
	}
}
