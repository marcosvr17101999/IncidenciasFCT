package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Prestamo;
import entidades.Socio;
import services.SocioService;
import util.PaginacionHelper;

@Named
@ViewScoped
public class BackingSociosMorosos implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Socio> listadoSociosMorosos;
	private List<Prestamo> listadoLibros;
	private Socio socio;
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public List<Prestamo> getListadoLibros() {
		return listadoLibros;
	}
	public void setListadoLibros(List<Prestamo> listadoLibros) {
		this.listadoLibros = listadoLibros;
	}
	private PaginacionHelper paginacion;
	private int slctnrpag = 5;
	public List<Socio> getListadoSociosMorosos() {
		return listadoSociosMorosos;
	}
	public void setListadoSociosMorosos(List<Socio> listadoSociosMorosos) {
		this.listadoSociosMorosos = listadoSociosMorosos;
	}
	public int getSlctnrpag() {
		return slctnrpag;
	}
	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}
	@EJB
	SocioService socioService;
	public BackingSociosMorosos() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void ini() {
		if (paginacion == null) {
			paginacion = new PaginacionHelper(5, 0) {
				@Override
				public long getItemsCount() {
					return socioService.getTotalMorosos();
				}
			};
		}
		listadoSociosMorosos = socioService.sociosEnRangoM(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag());
	}
	public PaginacionHelper getPaginacion() {
		return paginacion;
	}
	public void setPaginacion(PaginacionHelper paginacion) {
		this.paginacion = paginacion;
	}
	public SocioService getSocioService() {
		return socioService;
	}
	public void setSocioService(SocioService socioService) {
		this.socioService = socioService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/*************************************************************************/
	public int getTotalSociosCandidatos() {
		return (listadoSociosMorosos != null) ? listadoSociosMorosos.size() : 0;
	}

	/***************************************************************************/
	public long getTotalSocios() {
		return socioService.getTotal();
	}

	/********************************************************************/
	public void paginaAnterior() {
		paginacion.getPaginaAnterior();
		listadoSociosMorosos = socioService.sociosEnRangoM(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag());
	}

	/*********************************************************************/
	public void paginaSiguiente() {
		paginacion.getPaginaSiguiente();
		listadoSociosMorosos = socioService.sociosEnRangoM(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag());
	}

	/****************************************************************************/
	public String editarSocio() {
		return "/admin/editsocio.xhtml?pagina=/admin/listadosociospaginado.xhtml&faces-redirect=true";
	}

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
		listadoSociosMorosos = socioService.sociosEnRangoM(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag());
	}
	/**************************************************************************/
	public void librosFueraPlazo(Long s) {
		
		try {
			listadoLibros=socioService.prestamosFueraPlazo(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void diasDemora() {
		
	}
}
