package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Incidencia;
import services.IncidenciasService;
import util.PaginacionHelper;

@Named
@ViewScoped
public class BackingListadoIncidencias implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1072552360870918593L;
	Incidencia i=new Incidencia();
	List<Incidencia>listadoIncidencias=null;
	List<Incidencia>listadoIncidenciasPag=null;
	private String tipoBusqueda="%";
	
	
	public List<Incidencia> getListadoIncidencias() {
		return listadoIncidencias;
	}
	public List<Incidencia> getListadoIncidenciasPag() {
		return listadoIncidenciasPag;
	}
	public IncidenciasService getInService() {
		return inService;
	}
	
	public void setListadoIncidencias(List<Incidencia> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
	}
	public void setListadoIncidenciasPag(List<Incidencia> listadoIncidenciasPag) {
		this.listadoIncidenciasPag = listadoIncidenciasPag;
	}
	public void setInService(IncidenciasService inService) {
		this.inService = inService;
	}
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	@EJB
	 IncidenciasService inService;
	
	
	private PaginacionHelper paginacion;
	private int slctnrpag = 5;
	public int getSlctnrpag() {
		return slctnrpag;
	}
	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}
	public BackingListadoIncidencias() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void ini() {
		if (paginacion == null) {
			paginacion = new PaginacionHelper(5, 0) {
				@Override
				public long getItemsCount() {
					return inService.getTotalFiltro(tipoBusqueda);
				}
			};
		}
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag(),tipoBusqueda);
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
		resetPaginacion();
		return (listadoIncidencias != null) ? listadoIncidencias.size() : 0;
	}

	/***************************************************************************/
	public long getTotalInc() {
		resetPaginacion();
		return inService.getTotalFiltro(tipoBusqueda);
	}

	/********************************************************************/
	public void paginaAnterior() {
	
		paginacion.getPaginaAnterior();
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda);
	
		
	}

	/*********************************************************************/
	public void paginaSiguiente() {
		
		paginacion.getPaginaSiguiente();
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda);
		
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
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda);
	}
	/**************************************************************************/
	public void getEstadoIncidenciaFiltrada() {
		paginacion=null;
		ini();
	}
}
