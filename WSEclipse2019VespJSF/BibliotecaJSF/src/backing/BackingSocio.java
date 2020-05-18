package backing;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.PaginacionHelper;
import entidades.Socio;
import services.SocioService;

@Named
@ViewScoped
public class BackingSocio implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
// Inyección del enterprise java bean
	SocioService socioService;
	private List<Socio> listadoSocios;
	private int slctnrpag = 5;
	private PaginacionHelper paginacion;

	/*****************************************************************************/
	@PostConstruct
	public void ini() {
		if (paginacion == null) {
			paginacion = new PaginacionHelper(5, 0) {
				@Override
				public long getItemsCount() {
					return socioService.getTotal();
				}
			};
		}
		listadoSocios = socioService.sociosEnRango(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag());
	}

	/******************************************************************************/
	public int getSlctnrpag() {
		return slctnrpag;
	}

	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}

	public PaginacionHelper getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(PaginacionHelper paginacion) {
		this.paginacion = paginacion;
	}

	public List<Socio> getListadoSocios() {
		return listadoSocios;
	}

	public void setListadoSocios(List<Socio> listadoSocios) {
		this.listadoSocios = listadoSocios;
	}

	public BackingSocio() {
	}

	/*************************************************************************/
	public int getTotalSociosCandidatos() {
		return (listadoSocios != null) ? listadoSocios.size() : 0;
	}

	/***************************************************************************/
	public long getTotalSocios() {
		return socioService.getTotal();
	}

	/********************************************************************/
	public void paginaAnterior() {
		paginacion.getPaginaAnterior();
		listadoSocios = socioService.sociosEnRango(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag());
	}

	/*********************************************************************/
	public void paginaSiguiente() {
		paginacion.getPaginaSiguiente();
		listadoSocios = socioService.sociosEnRango(paginacion.getPagina() * paginacion.getNrpag(),
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
		listadoSocios = socioService.sociosEnRango(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag());
	}
	/**************************************************************************/
}