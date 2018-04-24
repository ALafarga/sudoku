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
    
    public boolean resolver(Casilla[][] tablero){
    	for(int row=0; row<9; row++){
    		for(int col=0; col<9; col++){
    			System.out.print("Checking ["+row+"] ["+col+"]: ");
    			if(!matriz[row][col].isUtilizado() && !matriz[row][col].isInicial()){
    				for(int k=0; k<=9; k++){
    					System.out.print("put a "+k+" ... ");
    					boolean valid = this.frame.setValor(row, col, k);
    					if(valid && resolver(tablero)){
    						System.out.println("Correct!");
    						return true;
    					}
    					System.out.println("Incorrect");
    					this.frame.clearValor(row, col);
    				}
    				return false;
    			} else {
    				System.out.println("Initial value or already solved");
    			}
    		}
    	}
    	return true;
    }
    
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

    public void clearValor(int fila, int columna){
        if(this.matriz[fila][columna].isInicial()){
            this.frame.showMessage("Tramposo...", "No puedes borrar ese número!");
        }
        else{
            this.matriz[fila][columna].reiniciar();
        }
    }

    public boolean inconsistencia(int fila,int columna) {
        for(int i=0;i<9;i++){
            if(matriz[fila][columna].getValor() == matriz[fila][i].getValor() && columna!=i){
                return true;
            }

            else if (matriz[fila][columna].getValor() == matriz[i][columna].getValor() && fila!=i)	{
                return true;
            }

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

                if(this.buscar(fila,columna,inicio,fin)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean buscar(int fila, int columna, int inicio, int fin){
        for (int j=inicio;j<=inicio+2;j++) {
            for (int h=fin; h<=fin+2;h++) {
                if (this.matriz[j][h].getValor() == this.matriz[fila][columna].getValor() && j!=fila && columna!=h)	{
                    return true;
                }
            }
        }
        return false;
    }

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

    public int getValor(int x, int y){
        return this.matriz[x][y].getValor();
    }

    public boolean getInicial(int x, int y){
        return this.matriz[x][y].isInicial();
    }

    public boolean getUtilizado(int x, int y){
        return this.matriz[x][y].isUtilizado();
    }

    public void rePlay(){
        this.Open(this.juego);
    }

    public void Open(String direccion)
    {
        if(direccion.equalsIgnoreCase("") || direccion.equalsIgnoreCase("null")){
            System.out.println("El cielo se caeeee!!");
        }
        else{
            BufferedReader lector;

            try{
                this.juego=direccion;
                System.out.println("Juego Cargado: " + this.juego);

                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        this.matriz[i][j].reiniciar();
                    }
                }

                lector=new BufferedReader(new FileReader("C:\\Sudoku\\"+direccion+".txt"));
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

    public void ganador(){
        this.Open("Ganador");
    }

    public void Save(String direccion)
    {
        try
        {
            PrintWriter escritor = new PrintWriter(new FileWriter("C:\\Sudoku\\"+direccion+".txt"));
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(this.matriz[i][j].isUtilizado() && this.matriz[i][j].isInicial()){
                        escritor.println( i + "," + j + "," + this.getValor(i, j)  + ",1");
                        //System.out.println( i + "," + j + "," + this.getValor(i, j)  + ",1");
                    }

                    else if(this.matriz[i][j].isUtilizado()){
                        //System.out.println( i + "," + j + "," + this.getValor(i, j)  + ",0");
                        escritor.println( i + "," + j + "," + this.getValor(i, j)  + ",0");
                    }
                }
            }
            escritor.close();
            this.frame.showMessage("Guardado", "El juego se guardo correctamente.");
            System.out.println( "Juego Guardado: "+ direccion);

        }
        catch(IOException e)
        {
            this.frame.showMessage("Ocurrio un Error", (""+e));
        }
    }
	/*
	public static void main(String[]args)
	{
		Tablero tab = new Tablero();
		tab.matriz[0][0].setValor(1);	tab.matriz[0][4].setValor(5);
		tab.matriz[0][1].setValor(2);	tab.matriz[0][5].setValor(6);
		tab.matriz[0][2].setValor(3);	tab.matriz[0][6].setValor(7);
		tab.matriz[0][3].setValor(4);	tab.matriz[0][7].setValor(8);
		tab.matriz[0][8].setValor(9);	tab.matriz[1][8].setValor(1);
		System.out.println(tab.matriz[1][8].isUtilizado());
		System.out.println(tab.matriz[1][2].isUtilizado());
	}*/

	public Casilla[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(Casilla[][] matriz) {
		this.matriz = matriz;
	}

}