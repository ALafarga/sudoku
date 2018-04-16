package ListaEnlazada;

public class ListaVaciaException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListaVaciaException()
	{
		super("La lista esta vacia");
	}
	
	public ListaVaciaException (String msj)
	{
		super("La lista esta vacia: "+msj);
	}
}
