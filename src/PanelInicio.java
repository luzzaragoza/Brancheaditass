import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class PanelInicio extends JPanel {
    private static final String[] NOMBRES = {"Lyss", "Luz", "Sofi 1", "Sofi 2"};
    private static final Color[] COLORES = {Colores.LYSS, Colores.LUZ, Colores.SOFI1, Colores.SOFI2};

    private String elegida;
    private JButton[] fichas = new JButton[NOMBRES.length];
    private JLabel etiquetaElegida;
    private JButton botonSeguir;

    public PanelInicio(String encargada) {
        elegida = encargada;
        setLayout(null);
        setBackground(Colores.FONDO);

        JLabel arriba = new JLabel("DEPÓSITO DE QUÍMICOS · TURNO NOCHE");
        arriba.setForeground(Colores.APAGADO);
        arriba.setFont(Fuentes.texto(13f));
        arriba.setBounds(40, 36, 400, 20);
        add(arriba);

        JLabel titulo = new JLabel("ALQUIMIA");
        titulo.setForeground(Colores.CREMA);
        titulo.setFont(Fuentes.titulo(80f));
        titulo.setBounds(40, 60, 500, 90);
        add(titulo);

        JLabel pregunta = new JLabel("¿Quién atiende hoy?");
        pregunta.setForeground(Colores.ROSA);
        pregunta.setFont(Fuentes.titulo(32f));
        pregunta.setBounds(40, 165, 600, 40);
        add(pregunta);

        JLabel bajada = new JLabel("Elegí quién está detrás del mostrador. Las otras tres pueden venir a dejar o retirar químicos.");
        bajada.setForeground(Colores.CREMA);
        bajada.setFont(Fuentes.texto(16f));
        bajada.setBounds(40, 212, 944, 24);
        add(bajada);

        for (int i = 0; i < NOMBRES.length; i++) {
            fichas[i] = ficha(NOMBRES[i], 40 + i * 248);
            fichas[i].addActionListener(new ElegirFicha(NOMBRES[i]));
            add(fichas[i]);
        }

        botonSeguir = new JButton("SEGUIR  »");
        botonSeguir.setFont(Fuentes.titulo(20f));
        botonSeguir.setBackground(Colores.ROSA);
        botonSeguir.setForeground(Colores.TINTA);
        botonSeguir.setOpaque(true);
        botonSeguir.setFocusPainted(false);
        botonSeguir.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        botonSeguir.setBounds(40, 590, 266, 58);
        add(botonSeguir);

        etiquetaElegida = new JLabel(" ");
        etiquetaElegida.setForeground(Colores.CREMA);
        etiquetaElegida.setFont(Fuentes.texto(16f));
        etiquetaElegida.setBounds(340, 605, 440, 24);
        add(etiquetaElegida);

        JLabel equipo = new JLabel("Sofia1 · Sofia2 · Lyssie · Luz");
        equipo.setForeground(Colores.APAGADO);
        equipo.setFont(Fuentes.texto(13f));
        equipo.setBounds(800, 605, 200, 20);
        add(equipo);

        pintarFichas();
    }

    private JButton ficha(String nombre, int x) {
        JButton ficha = new JButton(nombre);
        URL url = getClass().getResource("/img/" + nombre.toLowerCase().replace(" ", "") + "_x4.png");
        if (url != null) {
            ficha.setIcon(new ImageIcon(url));
        }
        ficha.setVerticalTextPosition(SwingConstants.BOTTOM);
        ficha.setHorizontalTextPosition(SwingConstants.CENTER);
        ficha.setFont(Fuentes.titulo(22f));
        ficha.setForeground(Colores.TINTA);
        ficha.setOpaque(true);
        ficha.setFocusPainted(false);
        ficha.setBounds(x, 270, 200, 280);
        return ficha;
    }

    private void pintarFichas() {
        for (int i = 0; i < fichas.length; i++) {
            if (NOMBRES[i].equals(elegida)) {
                fichas[i].setBackground(COLORES[i]);
                fichas[i].setBorder(BorderFactory.createMatteBorder(5, 5, 9, 5, Colores.ROSA));
            } else {
                fichas[i].setBackground(Colores.CREMA);
                fichas[i].setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
            }
        }
        etiquetaElegida.setText("Atiende " + elegida + ".");
    }

    public String getElegida() {
        return elegida;
    }

    public JButton getBotonSeguir() {
        return botonSeguir;
    }

    private class ElegirFicha implements ActionListener {
        private String nombre;

        public ElegirFicha(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            elegida = nombre;
            pintarFichas();
        }
    }
}
