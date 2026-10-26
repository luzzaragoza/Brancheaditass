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

        etiquetaTurno = new JLabel();
        etiquetaTurno.setBounds(20, 20, 200, 30);
        add(etiquetaTurno);

        etiquetaPuntaje = new JLabel();
        etiquetaPuntaje.setBounds(800, 20, 200, 30);
        add(etiquetaPuntaje);

        etiquetaVisitante = new JLabel();
        etiquetaVisitante.setBounds(260, 192, 128, 180);
        add(etiquetaVisitante);

        etiquetaNombre = new JLabel();
        etiquetaNombre.setBounds(420, 230, 400, 30);
        add(etiquetaNombre);

        etiquetaPedido = new JLabel();
        etiquetaPedido.setBounds(420, 270, 400, 30);
        add(etiquetaPedido);

        botonIr = new JButton("Ir al depósito");
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
    }

    public JButton getBotonIr() {
        return botonIr;
    }
}