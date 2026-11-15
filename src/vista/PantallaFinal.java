package vista;

import modelo.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
        setBackground(Colores.FONDO);

        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Colores.FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel(juego.getTitulo());
        titulo.setFont(Fuentes.titulo(40f));
        titulo.setForeground(Colores.ROSA);
        contenido.add(centrar(titulo));
        contenido.add(centrar(new JLabel(" ")));

        JLabel cara = armarCara();
        if (cara != null) {
            contenido.add(centrar(cara));
            contenido.add(centrar(new JLabel(" ")));
        }

        JLabel motivo = new JLabel("<html><body style='width:420px; text-align:center'>" + juego.getMotivo() + "</body></html>");
        motivo.setFont(Fuentes.texto(14f));
        motivo.setForeground(Colores.CREMA);
        contenido.add(centrar(motivo));
        contenido.add(centrar(new JLabel(" ")));

        JLabel resumen = new JLabel("Turno: " + juego.getTurno() + "     Puntaje: " + juego.getPuntaje());
        resumen.setFont(Fuentes.titulo(20f));
        resumen.setForeground(Colores.CREMA);
        contenido.add(centrar(resumen));
        contenido.add(centrar(new JLabel(" ")));

        if (juego.getPuntaje() > 0 && ranking.entra(juego.getPuntaje())) {
            armarPanelLetras();
            contenido.add(centrar(panelLetras));
        }

        panelRanking.setLayout(new BoxLayout(panelRanking, BoxLayout.Y_AXIS));
        panelRanking.setBackground(Colores.FONDO);
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

    private void pintarBoton(JButton boton, Color fondo) {
        boton.setBackground(fondo);
        boton.setForeground(Colores.TINTA);
        boton.setFont(Fuentes.titulo(16f));
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Colores.TINTA));
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
        panelLetras.setBackground(Colores.FONDO);

        JLabel texto = new JLabel("¡Tu puntaje entra al TOP 5! Escribí tus 3 letras:");
        texto.setFont(Fuentes.texto(14f));
        texto.setForeground(Colores.CREMA);

        campoLetras.setMaximumSize(new Dimension(100, 32));
        campoLetras.setHorizontalAlignment(JTextField.CENTER);
        campoLetras.setFont(Fuentes.titulo(18f));
        campoLetras.setBackground(Colores.CREMA);
        campoLetras.setForeground(Colores.TINTA);
        campoLetras.setBorder(BorderFactory.createLineBorder(Colores.ROSA, 2));

        JButton guardar = new JButton(" Guardar ");
        pintarBoton(guardar, Colores.ROSA);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPuntaje();
            }
        });

        aviso.setFont(Fuentes.texto(14f));
        aviso.setForeground(Colores.ROSA);

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
        encabezado.setFont(Fuentes.titulo(20f));
        encabezado.setForeground(Colores.ROSA);
        panelRanking.add(centrar(encabezado));

        if (ranking.getCantidad() == 0) {
            JLabel vacio = new JLabel("Todavía no hay puntajes.");
            vacio.setFont(Fuentes.texto(14f));
            vacio.setForeground(Colores.APAGADO);
            panelRanking.add(centrar(vacio));
        }
        for (int i = 0; i < ranking.getCantidad(); i++) {
            JLabel fila = new JLabel((i + 1) + ". " + ranking.getIniciales(i) + "   " + ranking.getPuntaje(i));
            fila.setFont(Fuentes.texto(14f));
            fila.setForeground(Colores.CREMA);
            panelRanking.add(centrar(fila));
        }
        panelRanking.revalidate();
        panelRanking.repaint();
    }

    private JPanel armarBotones() {
        JPanel botones = new JPanel();
        botones.setBackground(Colores.FONDO);

        JButton otraVez = new JButton(" Jugar de nuevo ");
        pintarBoton(otraVez, Colores.ROSA);
        otraVez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encargada = juego.getEncargada();
                Window ventana = SwingUtilities.getWindowAncestor(PantallaFinal.this);
                ventana.dispose();
                new VentanaJuego(new Juego(encargada));
            }
        });

        JButton salir = new JButton(" Salir ");
        pintarBoton(salir, Colores.CREMA);
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