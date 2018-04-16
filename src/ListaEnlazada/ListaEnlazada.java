package ListaEnlazada;

import java.util.NoSuchElementException;
//uinicio y fin tiran no such element exception
public class ListaEnlazada<T>	//cambiar E de la lista por T
{
	private Nodo<T> inicio;	//unico absolutamente necesario
	private Nodo<T> fin;
	private int size;

	public ListaEnlazada()
	{
		this.inicio = this.fin = null;
		this.size = 0;
	}

	public ListaEnlazada(T[]dato)
	{
		//this.insertarInicio(dato[0]);
		for(int i=0;i<dato.length;i++)
		{
			this.insertarFin(dato[i]);
		}
		size=dato.length;
	}

	public T fin() throws ListaVaciaException
	{
		try{
			return this.fin.getDato();
		}
		catch(NullPointerException e)
		{
			throw new ListaVaciaException("No se puede examinar el ultimo elemento de una lista vacia");
		}
	}

	public T inicio() throws ListaVaciaException
	{
		try{
			return this.inicio.getDato();
		}
		catch(NullPointerException e)
		{
			throw new ListaVaciaException("No se puede examinar el primer elemento de una lista vacia");
		}
	}

	public int size()
	{
		return this.size;
	}

	public boolean estaVacia()
	{
		return this.size==0;
		//return this.inicio==null; esta mas relacionado para quien es el inicio de mi lista
	}

	public void insertarInicio(T dato)
	{
		Nodo<T> nvo = new Nodo<T>(dato , this.inicio);
		if (this.estaVacia())
		{
			this.fin = nvo;
		}
		this.inicio = nvo;
		this.size++;
	}

	public void insertarFin (T dato)
	{
		Nodo<T> nvo = new Nodo<T>(dato);
		if (this.estaVacia())
		{
			this.insertarInicio(dato);
		}
		else
		{
			this.fin.setNext(nvo);
			this.fin = nvo;
			this.size++;
		}
	}

	public void insertarEn(int pos, T dato)
	{
		/*Nodo <T> current = this.inicio;
		for(int i =0;i<pos-1;i++)
		{
			current=current.getNext();
		}
		Nodo<T> nvo = new Nodo<T> (dato,current.getNext());
		current.setNext(nvo);
		size++;*/
		try
		{
			if (pos <=this.size)
			{
				if (pos==0)
				{
					this.insertarInicio(dato);
				}
				else if (pos==this.size)
				{
					this.insertarFin(dato);
				}
				else
				{
					Nodo<T> current=this.inicio;

					for (int i=0;i<pos-1;i++)
					{
						current=current.getNext();
					}

					Nodo<T> temp=current.getNext();
					Nodo<T> nvo= new Nodo<T>(dato,temp);
					current.setNext(nvo);
					this.size++;
				}
			}
		}
		catch (NullPointerException e)
		{
			new FueraDeRangoException("No existe dicha posici�n");
		}
	}

	public T borrarInicio() throws ListaVaciaException
	{
		//porque hereda de runtime? cuando se considera que es un error que puede ser prevenible a traves de programacion
			//acceder a una posicion invalida en un arreglo
			//algo que no puedes atribuirlo al programador entonces que no sea de tipo RunTime como una conexion de red
			//o que te den una ubicacion de un archivo que no existe

		//si la lsita esta vacia se pueden hacer varias cosas
			//arrojar una excepcion
			//o mandan a borrar un elemento
			//regresas null
		try{
			T dato = this.inicio.getDato();//nullpointer exception
			if(this.size()==1)
			{
				this.fin=null;
			}
			this.inicio = this.inicio.getNext();
			size--;
			return dato;
		}
		catch(NullPointerException e)
		{
			throw new ListaVaciaException("borrar el inicio de una lista vacia");
		}
	}

	public T borrarFin() throws NoSuchElementException
	{
		//las excepciones que NO heredan de runtime esoty obligado a reportarlas. las que heredan de Runtime no
		// //estoy obligado a reportarlas
		try{
			if (this.size==1)
			{
				return this.borrarInicio();
			}
			else
			{
				Nodo<T> antes = this.inicio;
				T dato;
				for(int i =0;i<this.size-2;i++)
				{
					antes=antes.getNext();
				}
				dato=this.fin.getDato();
				antes.setNext(null);
				this.size--;
				return dato;
			}
		}catch (NullPointerException e){
			throw new NoSuchElementException("No se puede borrar el fin de una lista vacia");
			//throw new ListaEnlazada.ListaVaciaException("");
		}
	}

	public T borrarEn(int pos)
	{
		/*Nodo <T> current = this.inicio;
		for(int i =0;i<pos-1;i++)
		{
			current=current.getNext();
		}
		T dato = current.getDato();
		current.setNext(current.getNext().getNext());
		size--;
		return dato;*/
		if(pos<0)
		{
			throw new FueraDeRangoException("No se puede borrar el ultimo elemento de una lista vacia");
		}
		else
		{
			try{
				if(pos==0)
				{
					return borrarInicio();
				}
				else if(pos==size-1)
				{
					return this.borrarFin();
				}
				else{
					Nodo<T> current = this.inicio;
					for(int i =0;i<pos-1;i++) {
						current = current.getNext();
					}
					T dato = current.getNext().getDato();
					current.setNext(current.getNext().getNext());
					size--;
					return dato;
				}
			}
			catch(NullPointerException e)
			{
				throw new FueraDeRangoException("No se puede  borrar el elemento "+pos+" de una lista de tama�o: "+this.size);
			}
		}
	}

	public void setAt(int pos, T dato)
	{
		if(pos >= 0 && pos < this.size){ //es mas rapido entrar al atributo que al metodos
			//si pos fuera un -1 no nos daria ninguna excepcion
			Nodo<T> current = this.inicio;
			for(int i = 0; i < pos; i++){
				current = current.getNext();
			}
			current.setDato(dato);
		}
		else
		{
			throw new IllegalArgumentException("La posicion " + pos +" de una lista de tamano "
					+ this.size + " es invalida");
		}
	}

	public String toString()
	{
		String tmp="";//acumulador
		Nodo<T> current = this.inicio;
		for(int i = 0 ; i < this.size; i++)
		{
			//tmp = tmp + current.getDato()+"\n"; yo antes
			//profe ahora
			tmp += current + ",";
			current = current.getNext();
		}//utilizando size

		/*while(current!=null)
		{
			tmp = tmp + current.getDato()+"\n";
			current = current.getNext();
		}//utilizando apuntadores*/

		return tmp;
	}

	public static void main(String[] args) 
	{
		ListaEnlazada <Integer> lista = new ListaEnlazada <Integer> ();
//		lista.borrarFin();
		try{
			int b = lista.borrarInicio();
		}
		catch (ListaVaciaException e)
		{
			System.out.println(e);
		}
		lista.insertarInicio(5);
		lista.insertarInicio(20);
		lista.insertarFin(70);
		lista.insertarFin(30);
		int a =lista.borrarInicio();
		System.out.println(lista);
		System.out.println(",");
		Integer[] a1 ={7,4,5,6,7,8};
		ListaEnlazada<Integer> prueba =new ListaEnlazada <Integer>(a1);
		System.out.println(prueba);
		prueba.insertarEn(3, 10);
		System.out.println(prueba);
		System.out.println(prueba.size());
		System.out.println("\n");
		prueba.setAt(3, 100);
		System.out.println(prueba);
		prueba.borrarEn(3);
		System.out.println(prueba);
	}

	public class Nodo<E>//tipo de dato parametrizado
	{
		protected E dato;
		protected Nodo<E> next;

		public Nodo(E dato, Nodo<E> next)
		{
			this.dato = dato;
			this.next = next;
		}

		public Nodo(E dato)
		{
			this(dato,null);
		}

		public E getDato()
		{
			return dato;
		}

		public void setDato(E dato)
		{
			this.dato = dato;
		}

		public Nodo<E> getNext()
		{
			return next;
		}

		public void setNext(Nodo<E> next)
		{
			this.next = next;
		}

		//New on 29 Jan 2018
		public String toString(){
			return this.dato.toString();
		}
	}
}