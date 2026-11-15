import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class PantallaFinal extends JPanel {
    private static Ranking ranking = new Ranking();

    private Juego juego;
    private JPanel contenido = new JPanel();
    private JPanel panelLetras = new JPanel();
    private JPanel panelRanking = new JPanel();
    private JTextField campoLetras = new JTextField(4);
    private JLabel aviso = new JLabel(" ");

    public PantallaFinal(Juego juego) {
        this.juego = juego;
        setLayout(new GridBagLayout());

        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel(juego.getTitulo());
        titulo.setFont(Fuentes.titulo(40f));
        contenido.add(centrar(titulo));

        JLabel cara = armarCara();
        if (cara != null) {
            contenido.add(centrar(cara));
        }

        JLabel motivo = new JLabel("<html><body style='width:420px; text-align:center'>" + juego.getMotivo() + "</body></html>");
        motivo.setFont(Fuentes.texto(16f));
        contenido.add(centrar(motivo));
        contenido.add(centrar(new JLabel(" ")));

        JLabel resumen = new JLabel("Turno: " + juego.getTurno() + "     Puntaje: " + juego.getPuntaje());
        resumen.setFont(Fuentes.texto(18f));
        contenido.add(centrar(resumen));
        contenido.add(centrar(new JLabel(" ")));

        if (juego.getPuntaje() > 0 && ranking.entra(juego.getPuntaje())) {
            armarPanelLetras();
            contenido.add(centrar(panelLetras));
        }

        panelRanking.setLayout(new BoxLayout(panelRanking, BoxLayout.Y_AXIS));
        actualizarRanking();
        contenido.add(centrar(panelRanking));
        contenido.add(centrar(new JLabel(" ")));
        contenido.add(centrar(armarBotones()));

        add(contenido);
    }

    private JComponent centrar(JComponent componente) {
        componente.setAlignmentX(Component.CENTER_ALIGNMENT);
        return componente;
    }

    private JLabel armarCara() {
        Visitante visitante = juego.getVisitante();
        if (visitante == null) {
            return null;
        }
        URL url = getClass().getResource("/img/" + visitante.getImagen());
        if (url == null) {
            return null;
        }
        return new JLabel(new ImageIcon(url));
    }

    private void armarPanelLetras() {
        panelLetras.setLayout(new BoxLayout(panelLetras, BoxLayout.Y_AXIS));
        JLabel texto = new JLabel("¡Tu puntaje entra al TOP 5! Escribí tus 3 letras:");
        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPuntaje();
            }
        });
        panelLetras.add(centrar(texto));
        panelLetras.add(centrar(campoLetras));
        panelLetras.add(centrar(guardar));
        panelLetras.add(centrar(aviso));
    }

    private void guardarPuntaje() {
        String letras = campoLetras.getText().trim().toUpperCase();
        if (letras.length() != 3 || !soloLetras(letras)) {
            aviso.setText("Tienen que ser exactamente 3 letras.");
            return;
        }
        ranking.agregar(letras, juego.getPuntaje());
        contenido.remove(panelLetras);
        actualizarRanking();
        contenido.revalidate();
        contenido.repaint();
    }

    private boolean soloLetras(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isLetter(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void actualizarRanking() {
        panelRanking.removeAll();
        JLabel encabezado = new JLabel("----- TOP 5 -----");
        encabezado.setFont(Fuentes.titulo(22f));
        panelRanking.add(centrar(encabezado));
        if (ranking.getCantidad() == 0) {
            panelRanking.add(centrar(new JLabel("Todavía no hay puntajes.")));
        }
        for (int i = 0; i < ranking.getCantidad(); i++) {
            JLabel fila = new JLabel((i + 1) + ". " + ranking.getIniciales(i) + "   " + ranking.getPuntaje(i));
            fila.setFont(Fuentes.texto(16f));
            panelRanking.add(centrar(fila));
        }
        panelRanking.revalidate();
        panelRanking.repaint();
    }

    private JPanel armarBotones() {
        JPanel botones = new JPanel();
        JButton otraVez = new JButton("Jugar de nuevo");
        otraVez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encargada = juego.getEncargada();
                Window ventana = SwingUtilities.getWindowAncestor(PantallaFinal.this);
                ventana.dispose();
                new VentanaJuego(new Juego(encargada));
            }
        });
        JButton salir = new JButton("Salir");
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        botones.add(otraVez);
        botones.add(salir);
        return botones;
    }
}