package backing;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entidades.Incidencia;
import services.IncidenciasService;
import util.PaginacionHelper;

@Named
@RequestScoped
public class BackingEdicionIncidencia {
	private static final long serialVersionUID = 1072552360870918593L;
	Incidencia i=new Incidencia();
	List<Incidencia>listadoIncidencias=null;
	private String tipoBusqueda="1";
	@EJB
	IncidenciasService incService;
	
	public Incidencia getI() {
		return i;
	}
	public List<Incidencia> getListadoIncidencias() {
		return listadoIncidencias;
	}
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	public IncidenciasService getIncService() {
		return incService;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public void setListadoIncidencias(List<Incidencia> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
	}
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	public void setIncService(IncidenciasService incService) {
		this.incService = incService;
	}
	public BackingEdicionIncidencia() {
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
		if (paginacion == null) {
			paginacion = new PaginacionHelper(getSlctnrpag(), 0) {
				@Override
				public long getItemsCount() {
					return incService.getTotalFiltro2(tipoBusqueda);
				}
			};
		}
		
		listadoIncidencias = incService.listadoIncidencias2(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag(),tipoBusqueda);
		System.out.println("-"+listadoIncidencias.size());
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
		paginacion=null;
		ini();
		resetPaginacion();
		return (listadoIncidencias != null) ? listadoIncidencias.size() : 0;
	}

	/***************************************************************************/
	public long getTotalInc() {
		paginacion=null;
		ini();
		resetPaginacion();
		return incService.getTotalFiltro2(tipoBusqueda);
	}

	/********************************************************************/
	public void paginaAnterior() {
		paginacion=null;
		ini();
		paginacion.getPaginaAnterior();
		listadoIncidencias = incService.listadoIncidencias2(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda);
	
		
	}

	/*********************************************************************/
	public void paginaSiguiente() {
		paginacion=null;
		ini();
		paginacion.getPaginaSiguiente();
		listadoIncidencias = incService.listadoIncidencias2(paginacion.getPagina() * paginacion.getNrpag(),
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
		listadoIncidencias = incService.listadoIncidencias2(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda);
	}
	/**************************************************************************/
	public void getEstadoIncidenciaFiltrada() {
		paginacion=null;
		ini();
			}
	
}
