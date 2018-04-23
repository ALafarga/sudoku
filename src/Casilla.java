public class Casilla{

    private int valor; //Momment
    private boolean inicial,
            utilizado;

    public Casilla() {
        this.valor = 0;
        this.inicial = false;
        this.utilizado = false;
    }

    public Casilla(int valor, boolean inicial){
        this.valor = valor;
        this.inicial = inicial;
        this.utilizado = false;
    }

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

    public boolean setValor(int valor,boolean x){
        this.valor=valor;
        this.utilizado=true;
        this.inicial = x;//comentario
        return true;
    }

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