import javax.swing.*;
import java.awt.Color;

public class PanelContenedor extends JPanel {
    private Contenedor contenedor;
    private JButton[] casillas = new JButton[4];
    private JButton botonCanjear;

    public PanelContenedor(Contenedor contenedor, Color fondo) {
        this.contenedor = contenedor;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(fondo);

        JLabel codigo = new JLabel(" " + contenedor.getCodigo() + " ");
        codigo.setForeground(Colores.TINTA);
        add(codigo);

        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new JButton();
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
                casillas[i].setText(contenedor.getQuimico(i).getNombre());
            } else {
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
