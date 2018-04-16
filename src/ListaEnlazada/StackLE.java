package ListaEnlazada;

import java.util.NoSuchElementException;

public class StackLE <E> {

    private ListaEnlazada<E> lista; // compuesta

    public StackLE()
    {
        this.lista = new ListaEnlazada<E>();
    }

    public int size()
    {
        return this.lista.size();
    }

    public boolean isEmpty()
    {
        return this.lista.estaVacia();
    }

    public void push (E dato)   //no debe ser insertar fin porque sino seria obrrar fin y es lo que no quiero porque tarda mucho
    {
//            this.lista.insertarInicio(dato);//hay dos implementaciones,
        //constante

//        this.lista.insertarFin(dato);//la otra opcion
        //constante

        this.lista.insertarInicio(dato);
    }

    public E pop () throws NoSuchElementException
    {
        try {
//            return this.lista.borrarFin();
            //compejidad: n

            return this.lista.borrarInicio();
            //constante
        }
        catch (NoSuchElementException e)
        {
            throw new NoSuchElementException("No se puede hacer un pop de un stack vacio");
        }
    }

    //Entonces no es lo mismo implmentar insertar fin porque es mas tardado, la segunda opcion es mejor porque
    //las operaciones son constantes.
    //esas son las sutilezas en las que hay que poner atencion

    public E top () throws NoSuchElementException {
        try { //no la necesito cachar porque ya la cacha la lista enlazada
            //siempre no, si se necesita
            return this.lista.inicio();
        } catch (NoSuchElementException e)//inicio nos puede arrojar un No such element entonces no necesitos
        {
            throw new NoSuchElementException("No se puede hacer un top de una stack vacio");
        }
    }

    //borra la liste
    public void flush ()
    {
        this.lista = new ListaEnlazada<E>();
        System.gc();    //llamada al garbage collector.
    }

    public String toString(){
        return lista.toString();
    }

    public static void main(String[] args) {
        StackLE <String> pila1 = new StackLE<String>();

        String [] nombres = {"Hector", "Elias", "Kevin", "Eutimio", "Quirino", "Donovan", "Lafarga"};

        for (String nombre:nombres){
            pila1.push(nombre);
        }

        while (!pila1.isEmpty())
        {
            //la i se incrementa y cada que hago un deque el size se decrementa
            //el size es dinamico
            //guardarlo en una variable, no se acostumbra poqure estas usando una variable que no es necesara
            System.out.println(pila1.pop());
            //soutp
            System.out.println("args = [" + args + "]");
            //sout
            System.out.println();
        }
    }
}
