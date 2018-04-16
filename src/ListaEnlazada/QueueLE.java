package ListaEnlazada;

import java.util.NoSuchElementException;

public class QueueLE <E> {
    private ListaEnlazada<E> lista;

    public QueueLE()
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

    public void enqueue (E dato)
    {
//            this.lista.insertarInicio(dato);//hay dos implementaciones,
        //constante

        this.lista.insertarFin(dato);//la otra opcion
        //constante
    }

    public E dequeue () throws NoSuchElementException
    {
        try {
//            return this.lista.borrarFin();
            //compejidad: n

            return this.lista.borrarInicio();
            //constante
        }
        catch (NoSuchElementException e)
        {
            throw new NoSuchElementException("No se puede hacer un dequeue de una dila vacia");
        }
    }

    //Entonces no es lo mismo implmentar insertar fin porque es mas tardado, la segunda opcion es mejor porque
    //las operaciones son constantes.
    //esas son las sutilezas en las que hay que poner atencion

    public E next () throws NoSuchElementException {
        try { //no la necesito cachar porque ya la cacha la lista enlazada
            //siempre no, si se necesita
            return this.lista.inicio();
        } catch (NoSuchElementException e)//inicio nos puede arrojar un No such element entonces no necesitos
        {
            throw new NoSuchElementException("No se puede hacer un next de una fila vacia");
        }
    }

    //borra la liste
    public void flush ()
    {
        this.lista = new ListaEnlazada<E>();
        System.gc();    //llamada al garbage collector.
    }

    @Override
    public String toString() {
        return lista.toString();
    }

    public static void main(String[] args) {
        QueueLE <String> fila1 = new QueueLE<String>();

        String [] nombres = {"Hector", "Elias", "Kevin", "Eutimio", "Quirino", "Donovan", "Lafarga"};

        for (String nombre:nombres){
            fila1.enqueue(nombre);
        }

//        fila1.enqueue("Hector");
//        fila1.enqueue("Elias");
//        fila1.enqueue("Kevin");
//        fila1.enqueue("Eutimio");
//        fila1.enqueue("Quirino");
//        fila1.enqueue("Donovan");
//        fila1.enqueue("Lafarga");

//        for(int i = 0; i < fila1.size(); i++)
//        {
//            //la i se incrementa y cada que hago un deque el size se decrementa
//            //el size es dinamico
//            //guardarlo en una variable, no se acostumbra poqure estas usando una variable que no es necesara
//            System.out.println(fila1.dequeue());
//        }
        while (!fila1.isEmpty())
        {
            //la i se incrementa y cada que hago un deque el size se decrementa
            //el size es dinamico
            //guardarlo en una variable, no se acostumbra poqure estas usando una variable que no es necesara
            System.out.println(fila1.dequeue());
        }
    }
}