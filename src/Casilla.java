public class Casilla{

    private int valor; //Valor de la casilla
    private boolean inicial, //Indica si el valor es parte del juego o introducido por el usuario
            utilizado;  //Indica si la casilla contiene un valor diferente de 0

    /**
     * Inicializa casilla con valores default de 0, no iniciado y no utilizado
     */
    public Casilla() {
        this.valor = 0;
        this.inicial = false;
        this.utilizado = false;
    }

    /**
     * Inicializa la casilla con el valor recibido asi como la informacion de si
     *
     * @param valor
     * @param inicial
     */
    public Casilla(int valor, boolean inicial){
        this.valor = valor;
        this.inicial = inicial;
        this.utilizado = false;
    }

    /**
     * Escribe valor si y solo si la casilla no fue inicializada por el juego
     *
     * <p>Establece el parametro utilizado como true para bloquear la casilla
     * de ser cambiada</p>
     *
     * @param valor
     * @return si la asignacion fue correctamente ejecutada
     */
    public boolean setValor(int valor){
        if (0<valor && valor<=9 && this.inicial==false)	{
            this.valor=valor;
            this.utilizado=true;
            return true;
        }

        else{
            return false;
        }
    }

    /**
     * Se escribe valor en la casilla
     *
     * @param valor a escribir
     * @param inicial
     * @return
     */
    public boolean setValor(int valor,boolean inicial){
        this.valor=valor;
        this.utilizado=true;
        this.inicial = inicial;//comentario
        return true;
    }

    /**
     * Reinicia la casilla
     */
    public void reiniciar(){
        this.valor = 0;
        this.utilizado = false;
        this.inicial = false;
    }

    public boolean isInicial(){
        return inicial;
    }

    public void setInicial(boolean inicial){
        this.inicial = inicial;
    }

    public boolean isUtilizado(){
        return utilizado;
    }

    public void setUtilizado(boolean utilizado){
        this.utilizado = utilizado;
    }

    public int getValor(){
        return valor;
    }
}