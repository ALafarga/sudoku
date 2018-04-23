import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Ventana extends Frame implements WindowListener, ActionListener {
    private Canvas lienzo;
    private Tablero tablero;

    public Ventana(){
        this.tablero = new Tablero(this);
        this.setTitle("Sudoku Extremo");
        this.lienzo = new Canvas(){
            public void paint(Graphics g){
                int aux = 150;
                int aux2 = 50;

                //Dibuja los cuadros
                g.setColor(new Color(250,255,253));
                g.fillRect(0,0,150,150);
                g.setColor(new Color(230,244,233));
                g.fillRect(150,0,300,150);
                g.setColor(new Color(251,222,217));
                g.fillRect(300,0,450,150);
                g.setColor(new Color(222,253,215));
                g.fillRect(0,150,150,300);
                g.setColor(new Color(215,215,253));
                g.fillRect(150,150,300,300);
                g.setColor(new Color(173,218,193));
                g.fillRect(300,150,450,300);
                g.setColor(new Color(164,154,252));
                g.fillRect(0,300,150,450);
                g.setColor(new Color(231,235,156));
                g.fillRect(150,300,300,450);
                g.setColor(new Color(254,160,137));
                g.fillRect(300,300,450,450);

                //Dibuja las separaciones
                for(int i=1;i<3;i++){
                    g.setColor(Color.magenta);
                    g.drawRect(aux*i, 0, 1, 450);
                    g.drawRect(0, aux*i, 450, 1);
                }

                for(int i=1;i<9;i++){
                    g.setColor(Color.orange);
                    g.drawLine(aux2*i, 0, aux2*i, 450);
                    g.drawLine(0,aux2*i,  450, aux2*i);
                }

                //Dibuja los numeros en el canvas
                Font manuel = new Font("Arial", Font.BOLD, 20);
                g.setFont(manuel);
                g.setColor(Color.black);

                int px = 20;
                int py = 33;
                String val;

                for (int z=0; z<9; z++){
                    for(int i=0; i<9; i++){
                        val = ""+tablero.getValor(z,i);
                        if(tablero.getInicial(z,i)){
                            g.setColor(Color.red.darker());
                        }
                        else{
                            g.setColor(Color.black);
                        }

                        if(val.compareToIgnoreCase("0") > 0){
                            g.drawString(val, px, py);
                        }
                        px += 50;
                    }
                    px = 20;
                    py += 50;
                }//End dibuja
            }

        };//End Lienzo


        //Interfaz Grafica
        //Da tamaño a lienzo y lo agrega al frame *No modificar*
        this.lienzo.setSize(450, 450);
        this.add(lienzo, BorderLayout.CENTER);

        //Agrega botones
        Panel botonera = new Panel();
        botonera.setLayout(new GridLayout(3, 2));

        Panel botonera1=new Panel();
        botonera1.setLayout(new GridLayout(1,1));

        Panel aux=new Panel();
        aux.setLayout(new BorderLayout());

        Button cargarJ = new Button("Abrir Juego");
        Button salvarJ = new Button("Guardar Juego");
        Button selectJ = new Button("Seleccionar Juego");
        Button reloadJ = new Button("Reiniciar Juego");
        Button deshacer = new Button("Deshacer");
        Button resolver = new Button("resolver");
        Button parar = new Button("parar");
        

        botonera.add(selectJ);
        botonera.add(reloadJ);
        botonera.add(salvarJ);
        botonera.add(cargarJ);
        botonera.add(resolver);
        botonera.add(parar);
        botonera1.add(deshacer);
        
        aux.add(botonera1,BorderLayout.NORTH);
        aux.add(botonera, BorderLayout.SOUTH);

        this.add(aux, BorderLayout.SOUTH);

        //Empaqueta y muetra la ventana *No modificar*
        this.pack();
        //this.setResizable(false);
        this.setVisible(true);


        //Agrega Detectores de Eventos

        //Detecta el comportamiento del raton *No Modificar*
        Mouse raton = new Mouse(this);
        this.lienzo.addMouseListener(raton);

        cargarJ.addActionListener(this);
        salvarJ.addActionListener(this);
        selectJ.addActionListener(this);
        reloadJ.addActionListener(this);
        deshacer.addActionListener(this);

        //Agrega Funcionalidad para cerrar desde titulo *No Modificar*
        this.addWindowListener(new WindowAdapter(){
                                   public void windowClosing(WindowEvent evt){
                                       System.exit(0);
                                   }
                               }


        );//Lienzo End

    }//Ventana End

    public void actionPerformed(ActionEvent arg0) {
        Button b = (Button) arg0.getSource();
        if(b.getLabel().equals("Abrir Juego")){
            String nombre = JOptionPane.showInputDialog("Escribe la partida que deseas jugar: (Nombre)");
            if(nombre != null){
                this.tablero.Open(nombre);
            }
            this.lienzo.enable();
            this.lienzo.repaint();
        }

        if(b.getLabel().equals("Guardar Juego")){
            String nombre = JOptionPane.showInputDialog("Como desea guardar esta partida? (Nombre):");
            if(nombre != null){
                this.tablero.Save(nombre);
            }
            this.lienzo.repaint();
        }

        if(b.getLabel().equals("Deshacer"))
        {
            this.tablero.deshacer();
        }

        if(b.getLabel().equals("Seleccionar Juego")){
            this.tablero.Open(String.valueOf(this.showGames()));
            this.lienzo.enable();
            this.lienzo.repaint();
        }

        if(b.getLabel().equals("Reiniciar Juego")){
            this.tablero.rePlay();
            this.lienzo.enable();
            this.lienzo.repaint();
        }
    }

    public Object showGames(){
        Object[] juegosdisponibles = { "Juego 1", "Juego 2", "Juego 3" };
        Object juegoe = JOptionPane.showInputDialog(null,"Escoje un juego", "Cargar juego nuevo",
                JOptionPane.INFORMATION_MESSAGE, null,
                juegosdisponibles, juegosdisponibles[0]);
        return juegoe;
    }

    public void showMessage(String title, String message){
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public void clearValor(int fila, int columna){
        this.tablero.clearValor(fila, columna);
        this.lienzo.repaint();
    }

    public void ganador(){
        this.tablero.ganador();
        this.lienzo.repaint();
    }

    public void extiGame(){
        int z = JOptionPane.showConfirmDialog(this, "Desea salir del juego?", "Aviso",JOptionPane.YES_NO_OPTION);
        if (z == 0){
            System.exit(0);
        }

        else{
            this.lienzo.disable();
        }
    }

    //Recibe datos del mouse
    public void setValor(int fila, int columna, int valor){
        if(this.tablero.setValor(fila, columna, valor)){
            this.lienzo.repaint();
            if(this.tablero.gano()){
                int x = JOptionPane.showConfirmDialog(this, "Jugar Otro?", "Ganaste!",JOptionPane.YES_NO_OPTION);
                if(x == 0){
                    this.tablero.Open(String.valueOf(this.showGames())); //Carga juego escogido por el usuario.
                    this.lienzo.repaint();
                }

                else{
                    this.extiGame(); //Salir del juego?
                }
            }
        }

        else{
            int x = JOptionPane.showConfirmDialog(this, "Perdiste! Volver a Intentar?", "Aviso",JOptionPane.YES_NO_OPTION);
            if(x == 0){
                this.tablero.rePlay();
                this.lienzo.repaint();
            }

            else{
                int y = JOptionPane.showConfirmDialog(this, "Desea cargar otro juego?", "Aviso",JOptionPane.YES_NO_OPTION);
                if(y == 0){
                    this.tablero.Open(String.valueOf(this.showGames()));//Carga juego escogido por el usuario.
                    this.lienzo.repaint();
                }
                else{
                    this.extiGame(); //Salir del juego ?
                }
            }
        }
    }


    public static void main(String[] args) {
        Ventana main = new Ventana();
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent arg0) {

    }

    @Override
    public void windowClosing(WindowEvent arg0) {

    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {

    }

    @Override
    public void windowIconified(WindowEvent arg0) {

    }

    @Override
    public void windowOpened(WindowEvent arg0) {

    }
}