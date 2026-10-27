import javax.swing.*;

public class PanelDeposito extends JPanel {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};

    private Juego juego;
    private JLabel etiquetaPedido;
    private PanelContenedor[] paneles = new PanelContenedor[CODIGOS.length];
    private JLabel etiquetaMensaje;

    public PanelDeposito(Juego juego) {
        this.juego = juego;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        etiquetaPedido = new JLabel();
        add(etiquetaPedido);

        add(new JLabel("HELADERA"));
        for (int i = 0; i < paneles.length; i++) {
            if (i == 3) {
                add(new JLabel("ESTANTES"));
            }
            paneles[i] = new PanelContenedor(juego.getContenedor(CODIGOS[i]));
            add(paneles[i]);
        }

        etiquetaMensaje = new JLabel(" ");
        add(etiquetaMensaje);

        actualizar();
    }

    public void actualizar() {
        for (int i = 0; i < paneles.length; i++) {
            paneles[i].actualizar();
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