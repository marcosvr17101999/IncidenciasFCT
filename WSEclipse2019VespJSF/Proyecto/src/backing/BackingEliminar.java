package backing;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Incidencia;
import services.IncidenciasService;
import util.PaginacionHelper;

@Named
@SessionScoped
public class BackingEliminar implements Serializable {
	private String tipoBusquedad = "5";
	private String tipoBusquedad2 = "6";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2048512396662887702L;
	@EJB
	IncidenciasService incService;
	List<Incidencia> listIncidencia;

	public BackingEliminar() {
		// TODO Auto-generated constructor stub

	}

	private PaginacionHelper paginacion;
	private int slctnrpag = 5;

	public int getSlctnrpag() {
		return slctnrpag;
	}

	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}

	@PostConstruct
	public void ini() {
		paginacion=null;
		if (paginacion == null) {
			paginacion = new PaginacionHelper(getSlctnrpag(), 0) {
				@Override
				public long getItemsCount() {
					return incService.getTotalFiltro3(tipoBusquedad, tipoBusquedad2);
				}
			};
		}

		listIncidencia = incService.listadoIncidencias3(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(), tipoBusquedad, tipoBusquedad2);
		System.out.println("-" + listIncidencia.size());
	}

	public PaginacionHelper getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(PaginacionHelper paginacion) {
		this.paginacion = paginacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*************************************************************************/
	public int getTotalIncFil() {
		paginacion = null;
		ini();
		resetPaginacion();
		return (listIncidencia != null) ? listIncidencia.size() : 0;
	}

	/***************************************************************************/
	public long getTotalInc() {
		paginacion = null;
		ini();
		resetPaginacion();
		return incService.getTotalFiltro3(tipoBusquedad, tipoBusquedad2);
	}

	/********************************************************************/
	public void paginaAnterior() {
		paginacion = null;
		ini();
		paginacion.getPaginaAnterior();
		listIncidencia = incService.listadoIncidencias3(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(), tipoBusquedad, tipoBusquedad2);

	}

	/*********************************************************************/
	public void paginaSiguiente() {
		paginacion = null;
		ini();
		paginacion.getPaginaSiguiente();
		listIncidencia = incService.listadoIncidencias3(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(), tipoBusquedad, tipoBusquedad2);

	}

	/****************************************************************************/

	/************************************************************************/
	public void resetPaginacion() {
		/*
		 * Procedimiento que recalcula el número de página en función de como se sube y
		 * baja el numero de registros por página. A este procedimiento se le llama
		 * mediante peticion ajax asociada al cuadro combinado que permite seleccionar
		 * los registros por pagina. EL valor seleccionado esta asociado a la propiedad
		 * slctnrpag de nuestro backing bean.
		 */
		int nuevapagina = (paginacion.getPrimerElementoPagina() / slctnrpag);
		paginacion.setNrpag(slctnrpag);
		paginacion.setPagina(nuevapagina);
		listIncidencia = incService.listadoIncidencias3(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(), tipoBusquedad, tipoBusquedad2);
	}

	/**************************************************************************/
	public void getEstadoIncidenciaFiltrada() {
		paginacion = null;
		ini();
	}

	public String getTipoBusquedad() {
		return tipoBusquedad;
	}

	public String getTipoBusquedad2() {
		return tipoBusquedad2;
	}

	public IncidenciasService getIncService() {
		return incService;
	}

	public List<Incidencia> getListIncidencia() {
		return listIncidencia;
	}

	public void setTipoBusquedad(String tipoBusquedad) {
		this.tipoBusquedad = tipoBusquedad;
	}

	public void setTipoBusquedad2(String tipoBusquedad2) {
		this.tipoBusquedad2 = tipoBusquedad2;
	}

	public void setIncService(IncidenciasService incService) {
		this.incService = incService;
	}

	public void setListIncidencia(List<Incidencia> listIncidencia) {
		this.listIncidencia = listIncidencia;
	}

	public void eliminar(Long id) {
	//	Incidencia i=incService.findByID(id).get(0);
		
		try {
		System.out.println("id que se pasa---------"+id);
			incService.eliminarIncidencia(id);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado con éxito",
					"Registro eliminado con éxito"));
		paginacion=null;
		ini();
		}catch (RollbackException e) {
			e.printStackTrace();
		} catch (Exception e) {
			String mensaje = e.getCause().getCause().getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		}
		
	//	System.out.println("eliminar ------->"+id.getIdIncidencia()+"----"+i.getUsuarioBean().getEmail());
	}
}
