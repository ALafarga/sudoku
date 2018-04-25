import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import ListaEnlazada.ListaVaciaException;

import ListaEnlazada.StackLE;

public class Tablero {

    private Casilla[][] matriz;
    private Ventana frame;
    private String juego;
    private StackLE<Integer[]> stack;
    //private Integer [] a;

    public Tablero(Ventana x){
        this.matriz = new Casilla [9][9];
        this.frame = x;

        this.stack= new StackLE <Integer[]>();


        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                this.matriz[j][i]= new Casilla();
            }
        }
        this.Open("Juego 1");
    }

    /**
     *
     * @param tablero
     * @return
     */
    public boolean resolver(Casilla[][] tablero){
    	for(int row=0; row<9; row++){
    		for(int col=0; col<9; col++){
    			System.out.print("Comprobando ["+row+"] ["+col+"]: ");
    			if(!matriz[row][col].isUtilizado() && !matriz[row][col].isInicial()){
    				for(int k=0; k<=9; k++){
    					System.out.print("pon a "+k+" ... ");
    					boolean valid = this.frame.setValor(row, col, k);
    					if(valid && resolver(tablero)){
    						System.out.println("Correcto!");
    						return true;
    					}
    					System.out.println("Incorrecto");
    					this.frame.clearValor(row, col);
    				}
    				return false;
    			} else {
    				System.out.println("Valor Inicial o previamente resuelto");
    			}
    		}
    	}
    	return true;
    }

    /**
     *
     * @param fila
     * @param columna
     * @param valor
     * @return
     */
    public boolean setValor(int fila, int columna, int valor){
        Integer [] a= new Integer [2];
        a[0]=fila;
        a[1]=columna;
        if(matriz[fila][columna].isInicial()){
            this.frame.showMessage("Tramposo", "No puedes cambiar éste numero...");
            return true;
        }

        else if (matriz[fila][columna].setValor(valor)){

            if(this.inconsistencia(fila, columna)){
                System.out.println("Inconsistencia");
                return false;
            }

            else{

                this.stack.push(a);
                System.out.println(stack.top()[0]);
                System.out.println("Fila: " + fila + " Columna: "+ columna + " Valor: "+valor);
                return true;
            }

        }

        else {
            return false;
        }
    }

    /**
     *
     * @throws ListaVaciaException
     */
    public void deshacer () throws ListaVaciaException
    {
        try{

        int aux1 = this.stack.top()[0];
        //System.out.println(this.stack.top()[0]+"");
        int aux2 = this.stack.top()[1];

        //System.out.println(this.stack.top()[1]+"");
        this.frame.clearValor(aux1,aux2);
        //System.out.println(stack+"");
        this.stack.pop();
        //System.out.println(stack+"");
        //System.out.println(this.stack.top()[0]+"");

        }catch(ListaVaciaException e){
            this.frame.showMessage("Invalido", "No hay registro de movimientos previos");
            throw new ListaVaciaException("No hay registro de movimientos previos");
        }
    }

    /**
     * Limpia la casilla [fila][columna] llamando a casilla.reiniciar
     *
     * <p>Si la casilla fue inicializada por el mismo juego no se puede borrar</p>
     *
     * @param fila de la casilla a limpiar
     * @param columna de la casilla a limpiar
     */
    public void clearValor(int fila, int columna){
        if(this.matriz[fila][columna].isInicial()){
            this.frame.showMessage("Tramposo...", "No puedes borrar ese número!");
        }
        else{
            this.matriz[fila][columna].reiniciar();
        }
    }

    /**
     * Revisa si el numero/valor que se esta introduciendo en le juego
     * es consistente con las reglas del juego
     *
     * @param fila a revisar
     * @param columna a revisar
     * @return true si existe alguna inconsistencia
     */
    public boolean inconsistencia(int fila,int columna) {
        for(int i=0;i<9;i++){
            //revisa por columna si el valor ya existe
            if(matriz[fila][columna].getValor() == matriz[fila][i].getValor() && columna!=i){
                return true;
            }
            //revisa por fila si el valor ya existe
            else if (matriz[fila][columna].getValor() == matriz[i][columna].getValor() && fila!=i)	{
                return true;
            }
            //revisa si el valor existe en el sector que se quiere introducir
            //inicio y fin son las banderas para saber que sector se revisara
            else {
                int inicio = 0,
                        fin = 0;

                if (fila >= 0 && fila <= 2)	{

                    inicio = 0;

                    if(columna >= 0 && columna <=2)	{
                        fin = 0;
                    }

                    if(columna >= 3 && columna <=5)	{
                        fin = 3;
                    }

                    if(columna >= 6 && columna <=8)	{
                        fin = 6;
                    }
                }

                if (fila >= 3 && fila <= 5)	{

                    inicio = 3;

                    if(columna >= 0 && columna <=2)	{
                        fin = 0;
                    }

                    if(columna >= 3 && columna <=5)	{
                        fin = 3;
                    }

                    if(columna >= 6 && columna <=8)	{
                        fin = 6;
                    }
                }
                if (fila >= 6 && fila <= 8)	{

                    inicio = 6;

                    if(columna >= 0 && columna <=2)	{
                        fin = 0;
                    }

                    if(columna >= 3 && columna <=5)	{
                        fin = 3;
                    }

                    if(columna >= 6 && columna <=8)	{
                        fin = 6;
                    }
                }
                //revisar inconsistencia por sector
                if(this.buscar(fila,columna,inicio,fin)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo utilizado por la funcion inconsistencia
     *
     * Recorrera el sector en el que se quiere comprobar la incosistencia
     * a fin de saber si el valor existe en el sector
     *
     * @param fila
     * @param columna
     * @param inicio
     * @param fin
     * @return true si en el sector existe el valor a revisar
     */
    public boolean buscar(int fila, int columna, int inicio, int fin){
        int valor = this.matriz[fila][columna].getValor();
        for (int j=inicio;j<=inicio+2;j++) {
            for (int h=fin; h<=fin+2;h++) {
                if (this.matriz[j][h].getValor() == valor && j!=fila && columna!=h)	{
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Esta funcion se llama cada que se cambia el valor d euna casilla para
     * saber si con ese movimiento se gano el juego
     *
     * Funciona recorriendo toda la matriz de 81 casillas
     *
     * Si no existe casilla en 0 o no utilizada y sin inconsistencia se gana
     * el juego
     *
     * @return true cuando gana el juego
     */
    public boolean gano(){
        for(int j=0;j<9;j++){
            for(int i=0;i<9;i++){
                if(this.inconsistencia(j, i)){
                    return false;
                }
                else if(this.matriz[j][i].getValor() == 0){
                    return false;
                }
                else if(this.matriz[j][i].isUtilizado() == false){
                    return false;
                }
            }
        }
        return true;

    }

    /**
     *
     */
    public void imprime(){
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                System.out.print(" " + this.matriz[i][j].getValor()+this.matriz[i][j].isInicial()+"");
            }
            System.out.println();
        }
    }

    /**
     * @param fila de la casilla a consultar
     * @param columna de la casilla a consultar
     * @return el valor de la casilla consultada
     */
    public int getValor(int fila, int columna){
        return this.matriz[fila][columna].getValor();
    }

    /**
     *
     * @param fila
     * @param columna
     * @return true si el valor es un valor inicializado por el
     * juego y no por el jugador
     */
    public boolean getInicial(int fila, int columna){
        return this.matriz[fila][columna].isInicial();
    }

    /**
     *
     * @param fila a consultar
     * @param columna a consultar
     * @return true si la casilla que se quiere editar esta utilizada
     */
    public boolean getUtilizado(int fila, int columna){
        return this.matriz[fila][columna].isUtilizado();
    }

    /**
     * Vuelve a jugar el juego actual
     *
     * Se llama cuando se elije reiniciar
     */
    public void rePlay(){
        this.Open(this.juego);
    }

    /**
     * Abrir juego.
     *
     * Si no se escoje nombre regresa El cielo se caeeee
     *
     * Try catch para el buffer reader que abre el archivo
     *
     * Si ocurre un error pregunta por un juego
     *
     * Si no se introduce ningun juego se abre el juego default Juego 1
     *
     * @param nombre del juego que se quiere abrir
     */
    public void Open(String nombre)
    {
        if(nombre.equalsIgnoreCase("") || nombre.equalsIgnoreCase("null")){
            System.out.println("El cielo se caeeee!!");
        }
        else{
            BufferedReader lector;

            try{
                this.juego=nombre;
                System.out.println("Juego Cargado: " + this.juego);

                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        this.matriz[i][j].reiniciar();
                    }
                }

                lector=new BufferedReader(new FileReader("C:\\Sudoku\\"+nombre+".txt"));
                String linea;
                int fila,
                        columna,
                        valor,
                        tmp;
                boolean inicial;

                StringTokenizer st;

                while((linea = lector.readLine())!= null)
                {
                    st = new StringTokenizer(linea,",");
                    fila=Integer.parseInt(st.nextToken());
                    columna=Integer.parseInt(st.nextToken());
                    valor=Integer.parseInt(st.nextToken());
                    tmp = Integer.parseInt(st.nextToken());
                    if(tmp==1){
                        inicial = true;
                    }
                    else{
                        inicial = false;
                    }
                    this.matriz[fila][columna].setValor(valor,inicial);
                }
                lector.close();
            }
            catch(IOException e){
                this.frame.showMessage("Ocurrio un Error", (""+e));
                String archivo = String.valueOf(this.frame.showGames());

                if((archivo.equalsIgnoreCase("null")) || archivo.equalsIgnoreCase("")){
                    this.Open("Juego 1");

                }
                else{
                    this.Open(archivo);
                }
            }
        }
    }

    /**
     * Abre el juego ganador relacionado con el juego que se esta jugando
     */
    public void ganador(){
        this.Open("Ganador" + juego);
    }

    /**
     * Guarda el avance del juego
     *
     * @param nombre del juego que se va a guardar
     */
    public void Save(String nombre)
    {
        try
        {
            PrintWriter escritor = new PrintWriter(new FileWriter("C:\\Sudoku\\"+nombre+".txt"));
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(this.matriz[i][j].isUtilizado() && this.matriz[i][j].isInicial()){
                        escritor.println( i + "," + j + "," + this.getValor(i, j)  + ",1");
                    }

                    else if(this.matriz[i][j].isUtilizado()){
                        escritor.println( i + "," + j + "," + this.getValor(i, j)  + ",0");
                    }
                }
            }
            escritor.close();
            this.frame.showMessage("Guardado", "El juego se guardo correctamente.");
            System.out.println( "Juego Guardado: "+ nombre);

        }
        catch(IOException e)
        {
            this.frame.showMessage("Ocurrio un Error", (""+e));
        }
    }

    /**
     * @return conjunto de casillas que se esta utilizando
     */
	public Casilla[][] getMatriz() {
		return matriz;
	}

    /**
     * Cambia la matriz base
     * TODO Falta un repaint PROBAR
     * @param matriz conjunto de casillas
     */
	public void setMatriz(Casilla[][] matriz) {
		this.matriz = matriz;
	}
}