import javax.swing.*;

public class PanelContenedor extends JPanel {
    private Contenedor contenedor;
    private JButton[] casillas = new JButton[4];
    private JButton botonCanjear;

    public PanelContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel(contenedor.getCodigo() + ""));

        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new JButton();
            add(casillas[i]);
        }

        botonCanjear = new JButton("Canjear +" + Juego.PUNTOS_CANJE);
        add(botonCanjear);

        actualizar();
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
