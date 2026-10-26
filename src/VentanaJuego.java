import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaJuego extends JFrame {
    private Juego juego;
    private CardLayout cartas;
    private JPanel pantallas;
    private PanelMostrador panelMostrador;
    private PanelDeposito panelDeposito;

    public VentanaJuego(Juego juego) {
        this.juego = juego;
        setTitle("Alquimia");
        setSize(1024, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cartas = new CardLayout();
        pantallas = new JPanel(cartas);

        panelMostrador = new PanelMostrador(juego);
        panelDeposito = new PanelDeposito(juego);
        pantallas.add(panelMostrador, "mostrador");
        pantallas.add(panelDeposito, "deposito");

        panelMostrador.getBotonIr().addActionListener(new IrAlDeposito());
        for (int i = 0; i < 6; i++) {
            PanelContenedor panel = panelDeposito.getPanel(i);
            for (int j = 0; j < 4; j++) {
                panel.getCasilla(j).addActionListener(new ClicCasilla(i, j));
            }
            panel.getBotonCanjear().addActionListener(new Canjear(i));
        }

        add(pantallas);
        setVisible(true);
    }

    public void actualizarPantalla() {
        panelMostrador.actualizar();
        panelDeposito.actualizar();
    }

    public void mostrarMensaje(String texto) {
        panelDeposito.mostrarMensaje(texto);
    }

    private class IrAlDeposito implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarMensaje("Hacé clic en el estante donde va, o en el químico que entregás.");
            cartas.show(pantallas, "deposito");
        }
    }

    private class ClicCasilla implements ActionListener {
        private int estante;
        private int posicion;

        public ClicCasilla(int estante, int posicion) {
            this.estante = estante;
            this.posicion = posicion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String codigo = panelDeposito.getCodigo(estante);
            boolean salioBien = juego.jugar(codigo, posicion);
            actualizarPantalla();
            if (salioBien) {
                cartas.show(pantallas, "mostrador");
            } else {
                mostrarMensaje("Perdiste: " + juego.getMotivo());
            }
        }
    }

    private class Canjear implements ActionListener {
        private int estante;

        public Canjear(int estante) {
            this.estante = estante;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            juego.canjear(panelDeposito.getCodigo(estante));
            actualizarPantalla();
            if (juego.estaTerminado()) {
                mostrarMensaje("Perdiste: " + juego.getMotivo());
            } else {
                mostrarMensaje("¡Canjeaste! Puntaje: " + juego.getPuntaje());
            }
        }
    }

    public static void main(String[] args) {
        new VentanaJuego(new Juego());
    }
}