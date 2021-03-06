package backing;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Usuario;
import services.EstadoService;
import services.IncidenciasService;
import services.UsuarioService;
import util.PaginacionHelper;

@Named
@SessionScoped
public class BackingListadoIncidencias implements Serializable{
	
	
	private static final long serialVersionUID = 1072552360870918593L;
	Incidencia i=new Incidencia();
	Usuario u;
	List<Incidencia>listadoIncidencias=null;
	private String tipoBusqueda="%";
	
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	String username = ec.getRemoteUser();
		
	
	public List<Incidencia> getListadoIncidencias() {
		return listadoIncidencias;
	}
	
	public IncidenciasService getInService() {
		return inService;
	}
	
	public void setListadoIncidencias(List<Incidencia> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
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
	
	public void logout(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    	ec.invalidateSession();
    
   
			try {
				ec.redirect("/Proyecto/user/home.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    
		
	}



	
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
	List<Estadoincidencia>listadoEstados;
	@EJB
UsuarioService uS;
	@EJB
	EstadoService estService;
	@PostConstruct
	public void ini() {
		listadoEstados=estService.getAll();
		u=uS.getUsuario(username);
		if (paginacion == null) {
			paginacion = new PaginacionHelper(getSlctnrpag(), 0) {
				@Override
				public long getItemsCount() {
					return inService.getTotalFiltro(tipoBusqueda,username);
				}
			};
		}
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag(),tipoBusqueda,username);
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
		return inService.getTotalFiltro(tipoBusqueda,username);
	}

	/********************************************************************/
	public void paginaAnterior() {
	
		paginacion.getPaginaAnterior();
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda,username);
	
		
	}

	/*********************************************************************/
	public void paginaSiguiente() {
		
		paginacion.getPaginaSiguiente();
		listadoIncidencias = inService.listadoIncidencias(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda,username);
		
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
				paginacion.getNrpag(),tipoBusqueda,username);
	}
	/**************************************************************************/
	public void getEstadoIncidenciaFiltrada() {
		paginacion=null;
		ini();
		//System.out.println(listadoIncidencias.size()+"-");
			}
	public String editarIncidencia() {
		return "/user/EditarIncidencia.xhtml&faces-redirect=true";
	}
	public void salir() {
		System.out.println("salir");
	ec.invalidateSession();
	}

	public Incidencia getI() {
		return i;
	}

	public Usuario getU() {
		return u;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public String getUsername() {
		return username;
	}

	public UsuarioService getuS() {
		return uS;
	}

	public void setI(Incidencia i) {
		this.i = i;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setuS(UsuarioService uS) {
		this.uS = uS;
	}

	public List<Estadoincidencia> getListadoEstados() {
		return listadoEstados;
	}

	public EstadoService getEstService() {
		return estService;
	}

	public void setListadoEstados(List<Estadoincidencia> listadoEstados) {
		this.listadoEstados = listadoEstados;
	}

	public void setEstService(EstadoService estService) {
		this.estService = estService;
	}
	
	
}
