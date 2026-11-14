import javax.swing.*;
import java.net.URL;

public class PanelMostrador extends JPanel {
    private Juego juego;
    private JLabel etiquetaTurno;
    private JLabel etiquetaPuntaje;
    private JLabel etiquetaVisitante;
    private JLabel etiquetaNombre;
    private JLabel etiquetaPedido;
    private JButton botonIr;

    public PanelMostrador(Juego juego) {
        this.juego = juego;
        setLayout(null);
        setBackground(Colores.FONDO);

        etiquetaTurno = new JLabel();
        etiquetaTurno.setBounds(20, 20, 200, 30);
        add(etiquetaTurno);
        etiquetaTurno.setForeground(Colores.CREMA);
        etiquetaTurno.setFont(Fuentes.titulo(22f));

        etiquetaPuntaje = new JLabel();
        etiquetaPuntaje.setBounds(800, 20, 200, 30);
        add(etiquetaPuntaje);
        etiquetaPuntaje.setForeground(Colores.CREMA);
        etiquetaPuntaje.setFont(Fuentes.titulo(22f));

        etiquetaVisitante = new JLabel();
        etiquetaVisitante.setBounds(260, 192, 128, 180);
        add(etiquetaVisitante);
        etiquetaVisitante.setForeground(Colores.CREMA);
        etiquetaVisitante.setFont(Fuentes.texto(14f));

        etiquetaNombre = new JLabel();
        etiquetaNombre.setBounds(420, 230, 400, 30);
        add(etiquetaNombre);
        etiquetaNombre.setForeground(Colores.CREMA);
        etiquetaNombre.setFont(Fuentes.titulo(26f));

        etiquetaPedido = new JLabel();
        etiquetaPedido.setBounds(420, 270, 400, 30);
        add(etiquetaPedido);
        etiquetaPedido.setForeground(Colores.CREMA);
        etiquetaPedido.setFont(Fuentes.texto(18f));

        botonIr = new JButton("Ir al depósito");
        botonIr.setBackground(Colores.ROSA);
        botonIr.setForeground(Colores.TINTA);
        botonIr.setFont(Fuentes.titulo(18f));
        botonIr.setOpaque(true);
        botonIr.setFocusPainted(false);
        botonIr.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 3));
        botonIr.setBounds(800, 600, 180, 40);
        add(botonIr);

        actualizar();
    }

    public void actualizar() {
        etiquetaTurno.setText("Turno " + juego.getTurno());
        etiquetaPuntaje.setText("Puntaje: " + juego.getPuntaje());

        Visitante visitante = juego.getVisitante();
        etiquetaNombre.setText(visitante.getNombre());

        URL url = getClass().getResource("/img/" + visitante.getImagen());
        if (url != null) {
            etiquetaVisitante.setIcon(new ImageIcon(url));
            etiquetaVisitante.setText("");
        } else {
            etiquetaVisitante.setIcon(null);
            etiquetaVisitante.setText("[" + visitante.getNombre() + "]");
        }

        Quimico pedido = juego.getPedido();
        String temperatura;
        if (pedido.necesitaFrio()) {
            temperatura = "de frío";
        } else {
            temperatura = "de calor";
        }

        if (juego.esRetiro()) {
            etiquetaPedido.setText("Viene a retirar: " + pedido.getNombre() + " " + temperatura);
        } else {
            etiquetaPedido.setText("Viene a dejar: " + pedido.getNombre() + " " + temperatura);
        }

        animarLlegada();
    }

    private void animarLlegada() {
        Thread hilo = new Thread(new LlegadaVisitante());
        hilo.start();
    }

    private class LlegadaVisitante implements Runnable {
        @Override
        public void run() {
            for (int x = -140; x<= 260; x = x + 8) {
                SwingUtilities.invokeLater(new MoverVisitante(x));
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private class MoverVisitante implements Runnable {
        private int x;

        public MoverVisitante(int x) {
            this.x = x;
        }

        @Override
        public void run() {
            etiquetaVisitante.setLocation(x, 192);
        }
    }

    public JButton getBotonIr() {
        return botonIr;
    }
}