import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

public class PanelContenedor extends JPanel {
    private Contenedor contenedor;
    private JButton[] casillas = new JButton[4];
    private JButton botonCanjear;
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
            add(casillas[i]);
        }

        botonCanjear = new JButton("Canjear +" + Juego.PUNTOS_CANJE);
        pintarBoton(botonCanjear, Colores.ROSA);
        add(botonCanjear);

        actualizar();
    }

    public void pintarBoton(JButton boton, Color fondo) {
        boton.setBackground(fondo);
        boton.setForeground(Colores.TINTA);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
    }

    public void actualizar() {
        for (int i = 0; i < casillas.length; i++) {
            if (i < contenedor.getCantidad()) {
                Quimico q = contenedor.getQuimico(i);
                URL url  = getClass().getResource("/img/" + q.getImagen());
                if (url != null) {
                    casillas[i].setIcon(new ImageIcon(url));
                    casillas[i].setText("");
                } else {
                    casillas[i].setIcon(null);
                    casillas[i].setText(q.getNombre());
                }
            } else {
                casillas[i].setIcon(null);
                casillas[i].setText("vacía");
            }
        }

        botonCanjear.setVisible(contenedor.estaLleno());
    }

    public JButton getCasilla(int posicion) {
        return casillas[posicion];
    }

    public JButton getBotonCanjear() {
        return botonCanjear;
    }
}
