package ListaEnlazada;

public class FueraDeRangoException extends RuntimeException {
	public FueraDeRangoException()
	{
		super("Fuera de rango ");
	}
	public FueraDeRangoException(String msj)
	{
		super("Fuera de rango "+msj);
	}
}
