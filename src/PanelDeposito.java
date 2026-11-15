import javax.swing.*;
import java.awt.Font;

public class PanelDeposito extends JPanel {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};

    private Juego juego;
    private JLabel etiquetaPedido;
    private PanelContenedor[] paneles = new PanelContenedor[CODIGOS.length];
    private JLabel etiquetaMensaje;

    public PanelDeposito(Juego juego) {
        this.juego = juego;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colores.PARED_DEPOSITO);

        etiquetaPedido = etiqueta("", Fuentes.texto(18f));
        add(etiquetaPedido);

        add(etiqueta("HELADERA", Fuentes.titulo(22f)));
        for (int i =0; i < paneles.length; i++) {
            if (i ==3) {
                add(etiqueta("ESTANTES", Fuentes.titulo(22f)));
            }
            if (i < 3) {
                paneles[i] = new PanelContenedor(juego.getContenedor(CODIGOS[i]), Colores.CARTEL_FRIO);
            } else {
                paneles[i] = new PanelContenedor(juego.getContenedor(CODIGOS[i]), Colores.CARTEL_CALOR);
            }
            add(paneles[i]);
        }
        etiquetaMensaje = etiqueta(" ", Fuentes.texto(16f));
        add(etiquetaMensaje);

        actualizar();
    }

    private JLabel etiqueta(String texto, Font fuente) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Colores.CREMA);
        etiqueta.setFont(fuente);
        return etiqueta;
    }

    public void actualizar() {
        for (int i = 0; i < paneles.length; i++) {
            paneles[i].actualizar(!juego.esRetiro());
            paneles[i].setActivo(!juego.estaTerminado());
        }

        Quimico pedido = juego.getPedido();
        String temperatura;
        if (pedido.necesitaFrio()) {
            temperatura = "de frío";
        } else {
            temperatura = "de calor";
        }

        if (juego.esRetiro()) {
            etiquetaPedido.setText("Te piden: " + pedido.getNombre() + " " + temperatura);
        } else {
            etiquetaPedido.setText("Llevás: " + pedido.getNombre() + " " + temperatura);
        }
    }

    public void mostrarMensaje(String texto) {
        etiquetaMensaje.setText(texto);
    }

    public PanelContenedor getPanel(int i) {
        return paneles[i];
    }

    public String getCodigo(int i) {
        return CODIGOS[i];
    }
}