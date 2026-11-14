import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import java.net.URL;

public class PanelContenedor extends JPanel {
    private Contenedor contenedor;
    private JButton[] casillas = new JButton[4];
    private JButton botonCanjear;
    private Border[] bordes = new Border[4];
    private static final Dimension TAMANIO_CASILLA = new Dimension(56, 73);

    public PanelContenedor(Contenedor contenedor, Color fondo) {
        this.contenedor = contenedor;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(fondo);

        JLabel codigo = new JLabel(" " + contenedor.getCodigo() + " ");
        codigo.setForeground(Colores.TINTA);
        add(codigo);

        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new JButton();
            casillas[i].setMinimumSize(TAMANIO_CASILLA);
            casillas[i].setPreferredSize(TAMANIO_CASILLA);
            casillas[i].setMaximumSize(TAMANIO_CASILLA);
            pintarBoton(casillas[i], Colores.CREMA);
            casillas[i].addMouseListener(new MouseEncima(i));
            add(casillas[i]);
        }

        botonCanjear = new JButton("Canjear +" + Juego.PUNTOS_CANJE);
        pintarBoton(botonCanjear, Colores.ROSA);
        add(botonCanjear);
    }

    public void pintarBoton(JButton boton, Color fondo) {
        boton.setBackground(fondo);
        boton.setForeground(Colores.TINTA);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
    }

    public void actualizar(boolean esDejar) {
        int cantidad = contenedor.getCantidad();
        for (int i = 0; i < casillas.length; i++) {
            if (i < cantidad) {
                Quimico q = contenedor.getQuimico(i);
                URL url = getClass().getResource("/img/" + q.getImagen());
                if (url != null) {
                    casillas[i].setIcon(new ImageIcon(url));
                    casillas[i].setText("");
                } else {
                    casillas[i].setIcon(null);
                    casillas[i].setText(q.getNombre());
                }
                if (i == cantidad - 1) {
                    bordes[i] = BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.ROSA);
                } else {
                    bordes[i] = BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA);
                }
            } else if (i == cantidad && esDejar) {
                casillas[i].setIcon(null);
                casillas[i].setText("+");
                bordes[i] = BorderFactory.createDashedBorder(Colores.APAGADO, 3, 6, 4, false);
            } else {
                casillas[i].setIcon(null);
                casillas[i].setText("");
                bordes[i] = BorderFactory.createLineBorder(Colores.APAGADO, 1);
            }
            casillas[i].setBorder(bordes[i]);
        }

        botonCanjear.setVisible(contenedor.estaLleno());
    }

    public void setActivo(boolean activo) {
        for (int i = 0; i < casillas.length; i++) {
            casillas[i].setEnabled(activo);
        }
        botonCanjear.setEnabled(activo);
    }

    private class MouseEncima extends MouseAdapter {
        private int posicion;

        public MouseEncima(int posicion) {
            this.posicion = posicion;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (casillas[posicion].isEnabled()) {
                Border rosa = BorderFactory.createLineBorder(Colores.ROSA, 2);
                casillas[posicion].setBorder(BorderFactory.createCompoundBorder(rosa, bordes[posicion]));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            casillas[posicion].setBorder(bordes[posicion]);
        }
    }

    public JButton getCasilla(int posicion) {
        return casillas[posicion];
    }

    public JButton getBotonCanjear() {
        return botonCanjear;
    }
}