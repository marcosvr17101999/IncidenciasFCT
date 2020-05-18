package backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Prestamo;
import entidades.Socio;
import exceptions.PrestamoException;
import services.PrestamoService;

@Named
@SessionScoped
public class BackingAltaPrestamo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7744098037328050139L;
	@EJB
	private PrestamoService prestamoservice;
	private Prestamo prestamo;
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PrestamoService getPrestamoservice() {
		return prestamoservice;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamoservice(PrestamoService prestamoservice) {
		this.prestamoservice = prestamoservice;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public BackingAltaPrestamo() {
		// TODO Auto-generated constructor stub
	}
	
	public void nuevoPrestamo() {
		try {
			prestamoservice.instertarPrestamo(prestamo);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PrestamoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prestamo=new Prestamo();
	}
}
