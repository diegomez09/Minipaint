import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiniPaint extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

    private ButtonGroup modos;
    private JPanel area;
    private JLabel status;
    private Image buffer;
    private Image temporal;
    
    private final int PUNTOS        = 1;
    private final int LINEAS        = 2;
    private final int RECTANGULOS   = 3;  
    private final int CIRCULOS      = 4;
    private int modo;
    private int x, y;
    
    public MiniPaint (){
        super("MiniPaint 1.0");
        
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        
        // Opción Nuevo 
        JMenuItem opcionNuevo = new JMenuItem("Nuevo", 'N');
        opcionNuevo.addActionListener(this);
        opcionNuevo.setActionCommand("Nuevo");
        menuArchivo.add(opcionNuevo);
        menuArchivo.addSeparator();
        
        // Opción Salir
        JMenuItem opcionSalir = new JMenuItem("Salir", 'S');
        opcionSalir.addActionListener(this);
        opcionSalir.setActionCommand("Salir");
        menuArchivo.add(opcionSalir);
        
        menuBar.add(menuArchivo);
        
        modos = new ButtonGroup();
        
        // Menú Modo
        JMenu menuModo = new JMenu("Modo");
        
        // Opción Puntos
        JRadioButtonMenuItem opcionPuntos = new JRadioButtonMenuItem("Puntos", true);
        opcionPuntos.addActionListener(this);
        opcionPuntos.setActionCommand("Puntos");
        menuModo.add(opcionPuntos);
        modos.add(opcionPuntos);
        
        // Opción Lineas
        JRadioButtonMenuItem opcionLineas = new JRadioButtonMenuItem("Lineas");
        opcionLineas.addActionListener(this);
        opcionLineas.setActionCommand("Lineas");
        menuModo.add(opcionLineas);
        modos.add(opcionLineas);
        
        // Opción Rectángulos
        JRadioButtonMenuItem opcionRectangulos = new JRadioButtonMenuItem("Rectángulos");
        opcionRectangulos.addActionListener(this);
        opcionRectangulos.setActionCommand("Rectángulos");
        menuModo.add(opcionRectangulos);
        modos.add(opcionRectangulos);
        
        // Opción Círculos
        JRadioButtonMenuItem opcionCirculos = new JRadioButtonMenuItem("Círculos");
        opcionCirculos.addActionListener(this);
        opcionCirculos.setActionCommand("Círculos");
        menuModo.add(opcionCirculos);
        modos.add(opcionCirculos);
        
        menuBar.add(menuModo);
        
        area = new JPanel();
        area.addMouseListener(this);
        area.addMouseMotionListener(this);
        status = new JLabel("Status", JLabel.LEFT);
        
        // Asignar barra menues
        setJMenuBar(menuBar);
        
        // Agregar zona grafica
        getContentPane().add(area, BorderLayout.CENTER);
        
        // Agregar barra de estado
        getContentPane().add(status, BorderLayout.SOUTH);
        
        modo = PUNTOS;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        show();
        buffer = area.createImage(area.getWidth(), area.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println(comando);
        if(comando.equals("Nuevo")){
            area.getGraphics().clearRect(0, 0, area.getWidth(), area.getHeight());
        }
        else if(comando.equals("Salir")){
            if(JOptionPane.showConfirmDialog(this, "¿En verdad desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                dispose();
                System.exit(0);
            }
        }
        else if(comando.equals("Puntos")){
            modo = PUNTOS;
        } else if(comando.equals("Lineas")){
            modo = LINEAS;
        } else if(comando.equals("Rectángulos")) {
            modo = RECTANGULOS;
        } else if(comando.equals("Círculos")) {
            modo = CIRCULOS;
        }
            
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        temporal = area.createImage(area.getWidth(), area.getHeight());
        temporal.getGraphics().drawImage(buffer, 0, 0, this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buffer.getGraphics().drawImage(temporal, 0, 0, this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics g = temporal.getGraphics();
        switch(modo){
            case PUNTOS:
                g.fillOval(e.getX(), e.getY(), 1, 1);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case LINEAS:
                g.drawImage(buffer, 0, 0, area);
                g.drawLine(x, y, e.getX(), e.getY());
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case RECTANGULOS:
                g.drawImage(buffer, 0, 0, area);
                g.drawRect(x, y, e.getX()-x, e.getY()-y);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case CIRCULOS:
                g.drawImage(buffer, 0, 0, area);
                g.drawOval(x, y, e.getX()-x, e.getY()-y);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        status.setText("x = " + e.getX() + " | y = " + e.getY());
    }
    
    public static void main(String[] args) {
       MiniPaint as = new MiniPaint();
    }
}
