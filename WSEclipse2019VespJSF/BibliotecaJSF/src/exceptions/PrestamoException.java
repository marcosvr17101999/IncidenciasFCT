package exceptions;



public class PrestamoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6504893836344096464L;

	public PrestamoException() {
		
	}
	
	public PrestamoException(String mensaje) {
		super(mensaje);
	}

}
