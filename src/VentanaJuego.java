import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaJuego extends JFrame {
    private Juego juego;
    private CardLayout cartas;
    private JPanel pantallas;
    private PanelMostrador panelMostrador;
    private PanelDeposito panelDeposito;
    private PanelReglas panelReglas;

    public VentanaJuego(Juego juego) {
        this.juego = juego;
        setTitle("Alquimia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cartas = new CardLayout();
        pantallas = new JPanel(cartas);
        pantallas.setPreferredSize(new Dimension(1024, 720));

        panelReglas = new PanelReglas();
        panelMostrador = new PanelMostrador(juego);
        panelDeposito = new PanelDeposito(juego);
        pantallas.add(panelReglas, "reglas");
        pantallas.add(panelMostrador, "mostrador");
        pantallas.add(panelDeposito, "deposito");

        panelReglas.getBotonEmpezar().addActionListener(new Empezar());
        panelMostrador.getBotonIr().addActionListener(new IrAlDeposito());
        panelDeposito.getBotonVolver().addActionListener(new VolverAlMostrador());
        for (int i = 0; i < 6; i++) {
            PanelContenedor panel = panelDeposito.getPanel(i);
            for (int j = 0; j < 4; j++) {
                panel.getCasilla(j).addActionListener(new ClicCasilla(i, j));
            }
            panel.getBotonCanjear().addActionListener(new Canjear(i));
        }

        add(pantallas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actualizarPantalla() {
        panelMostrador.actualizar();
        panelDeposito.actualizar();
    }

    public void mostrarMensaje(String texto) {
        panelDeposito.mostrarMensaje(texto);
    }

    private class Empezar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cartas.show(pantallas, "mostrador");
            panelMostrador.actualizar();
        }
    }

    private class IrAlDeposito implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panelDeposito.actualizar();
            cartas.show(pantallas, "deposito");
        }
    }

    private class VolverAlMostrador implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cartas.show(pantallas, "mostrador");
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
            if (juego.estaTerminado()) {
                return;
            }
            String codigo = panelDeposito.getCodigo(estante);
            if (juego.esRetiro() && posicion >= juego.getContenedor(codigo).getCantidad()) {
                mostrarMensaje("Esa casilla está vacía. Hacé clic en una que tenga un químico.");
                return;
            }
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
            if (juego.estaTerminado()) {
                return;
            }
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
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cambiar el aspecto: se usa el de siempre.");
        }
        new VentanaJuego(new Juego());
    }
}