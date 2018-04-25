import java.awt.event.*;
import javax.swing.*;
public class Mouse  extends MouseAdapter{
    Ventana frame;

    public Mouse(Ventana x){
        this.frame = x;
    }

    public void mouseClicked(MouseEvent e)
    {
        try {

            //Calcula la casilla
            int fila = Math.round(e.getY()/50);
            int columna = Math.round(e.getX()/50);
            int valor = -1;

            //Recibe el valor
            boolean correcto = false;
            while (!correcto) {
                String xx = JOptionPane.showInputDialog(null, "Inserte un valor",
                        "Escriba un numero del 1 al 9.");
                try{
                    ///Huevos de Pascua
                    if (xx.compareToIgnoreCase("Elias") == 0 || xx.compareToIgnoreCase("Lafarga") == 0){
                        this.frame.showMessage("Ganaste!!!", "Quien lo diria... xD");
                        this.frame.ganador();
                        valor = 0;
                        correcto = true;
                    }

                    else{
                        valor = Integer.parseInt(xx);
                        //System.out.println(valor);
                        if (valor > 0 && valor < 10) {
                            correcto = true;
                        }
                        if(valor == 0){
                            correcto = true;
                            this.frame.clearValor(fila, columna);

                        }
                    }
                }
                catch (NullPointerException e1){
                    correcto = true;
                }
                catch(NumberFormatException e1){

                }
            }

            //Envia datos
            if(valor > 0){
                this.frame.setValor(fila,columna,valor);
            }

        }

        catch (NumberFormatException e2) {
            // Se cerro la ventana no hacer nada....
        }
    }
}