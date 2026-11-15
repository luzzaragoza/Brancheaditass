package vista;

import modelo.Juego;
import modelo.Ranking;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class PantallaFinal extends JPanel {
    private static Ranking ranking = new Ranking();

    private Juego juego;
    private JPanel panelIngreso;
    private JPanel listaRanking;
    private JTextField campoLetras;
    private JLabel aviso;

    public PantallaFinal(Juego juego) {
        this.juego = juego;
        setLayout(null);
        setBackground(Colores.FONDO);

        JPanel barra = new JPanel(null);
        barra.setBackground(Colores.TINTA);
        barra.setBounds(0, 0, 1024, 56);
        barra.add(texto("ALQUIMIA", Fuentes.titulo(20f), Colores.CREMA, 20, 14, 130, 28));
        barra.add(texto("· FIN DE LA PARTIDA", Fuentes.texto(13f), Colores.LAVANDA, 140, 18, 200, 24));
        add(barra);

        String tituloTexto = juego.getTitulo() != null ? juego.getTitulo() : "¡FIN DE LA PARTIDA!";
        JLabel titulo = texto(tituloTexto, Fuentes.titulo(36f), Colores.ROSA, 36, 74, 560, 44);
        add(titulo);

        JLabel escena = new JLabel(imagen(archivoEscena(tituloTexto)));
        escena.setLayout(null);
        escena.setOpaque(true);
        escena.setBackground(Colores.PARED_DEPOSITO);
        escena.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        escena.setBounds(36, 128, 560, 220);

        String imgEncargada = juego.getEncargada().toLowerCase().replace(" ", "") + "_x4.png";
        JLabel encargada = new JLabel(imagen(imgEncargada));
        encargada.setBounds(150, 32, 128, 180);
        escena.add(encargada);

        JLabel efecto = new JLabel(imagen(archivoEfecto(tituloTexto)));
        efecto.setBounds(290, 48, 112, 144);
        escena.add(efecto);
        add(escena);

        JPanel cardPaso = caja(Colores.CREMA, 36, 364, 560, 146);
        JLabel chipPaso = texto("QUÉ PASÓ", Fuentes.titulo(13f), Colores.CREMA, 18, 14, 110, 24);
        chipPaso.setOpaque(true);
        chipPaso.setBackground(Colores.TINTA);
        chipPaso.setHorizontalAlignment(SwingConstants.CENTER);
        cardPaso.add(chipPaso);

        String motivoTexto = juego.getMotivo() != null ? juego.getMotivo() : "";
        JLabel motivo = texto("<html>" + motivoTexto + "</html>", Fuentes.texto(15f), Colores.TINTA, 18, 46, 524, 48);
        motivo.setVerticalAlignment(SwingConstants.TOP);
        cardPaso.add(motivo);

        JLabel pista = texto("<html><b>Pista:</b> " + obtenerPista(tituloTexto) + "</html>",
                Fuentes.texto(13f), Colores.APAGADO, 18, 98, 524, 38);
        pista.setVerticalAlignment(SwingConstants.TOP);
        cardPaso.add(pista);
        add(cardPaso);

        JPanel boxTurno = caja(Colores.CREMA, 36, 524, 272, 86);
        boxTurno.add(texto("LLEGASTE AL TURNO", Fuentes.titulo(13f), Colores.APAGADO, 16, 12, 240, 20));
        boxTurno.add(texto("" + juego.getTurno(), Fuentes.titulo(34f), Colores.TINTA, 16, 36, 240, 40));
        add(boxTurno);

        JPanel boxPuntaje = caja(Colores.CREMA, 324, 524, 272, 86);
        boxPuntaje.add(texto("PUNTAJE FINAL", Fuentes.titulo(13f), Colores.APAGADO, 16, 12, 240, 20));
        boxPuntaje.add(texto("" + juego.getPuntaje(), Fuentes.titulo(34f), Colores.ROSA, 16, 36, 240, 40));
        add(boxPuntaje);

        JButton botonJugar = new JButton("JUGAR DE NUEVO");
        pintarBoton(botonJugar, Colores.ROSA);
        botonJugar.setBounds(36, 626, 272, 56);
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encargadaActual = juego.getEncargada();
                Window ventana = SwingUtilities.getWindowAncestor(PantallaFinal.this);
                ventana.dispose();
                new VentanaJuego(new Juego(encargadaActual));
            }
        });
        add(botonJugar);

        JButton botonReglas = new JButton("VER REGLAS");
        pintarBoton(botonReglas, Colores.CREMA);
        botonReglas.setBounds(324, 626, 272, 56);
        botonReglas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window ventana = SwingUtilities.getWindowAncestor(PantallaFinal.this);
                if (ventana instanceof VentanaJuego) {
                    ((VentanaJuego) ventana).mostrarReglas();
                }
            }
        });
        add(botonReglas);

        JPanel cardRanking = caja(Colores.CREMA, 624, 128, 364, 554);
        JLabel tituloRanking = texto("MEJORES PUNTAJES", Fuentes.titulo(20f), Colores.TINTA, 22, 20, 320, 28);
        cardRanking.add(tituloRanking);

        JLabel subRanking = texto("TOP 5 DE ESTA COMPUTADORA", Fuentes.texto(12f), Colores.APAGADO, 22, 48, 320, 20);
        cardRanking.add(subRanking);

        listaRanking = new JPanel();
        listaRanking.setLayout(new BoxLayout(listaRanking, BoxLayout.Y_AXIS));
        listaRanking.setBackground(Colores.CREMA);
        listaRanking.setBounds(22, 82, 320, 290);
        cardRanking.add(listaRanking);

        if (juego.getPuntaje() > 0 && ranking.entra(juego.getPuntaje())) {
            panelIngreso = new JPanel(null);
            panelIngreso.setBackground(Colores.PARED_DEPOSITO);
            panelIngreso.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
            panelIngreso.setBounds(22, 384, 320, 146);

            panelIngreso.add(texto("¡ENTRASTE AL TOP 5!", Fuentes.titulo(14f), Colores.ROSA, 14, 10, 292, 22));
            panelIngreso.add(texto("Escribí tus 3 iniciales:", Fuentes.texto(13f), Colores.CREMA, 14, 34, 292, 20));

            campoLetras = new JTextField(4);
            campoLetras.setFont(Fuentes.titulo(20f));
            campoLetras.setHorizontalAlignment(SwingConstants.CENTER);
            campoLetras.setBackground(Colores.CREMA);
            campoLetras.setForeground(Colores.TINTA);
            campoLetras.setBorder(BorderFactory.createLineBorder(Colores.ROSA, 2));
            campoLetras.setBounds(14, 62, 120, 40);
            panelIngreso.add(campoLetras);

            JButton botonGuardar = new JButton("GUARDAR");
            pintarBoton(botonGuardar, Colores.ROSA);
            botonGuardar.setFont(Fuentes.titulo(14f));
            botonGuardar.setBounds(146, 62, 160, 40);
            botonGuardar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guardarPuntaje();
                }
            });
            panelIngreso.add(botonGuardar);

            aviso = texto(" ", Fuentes.texto(12f), Colores.ROSA, 14, 110, 292, 22);
            panelIngreso.add(aviso);

            cardRanking.add(panelIngreso);
        }

        actualizarRanking();
        add(cardRanking);
    }

    private String archivoEscena(String titulo) {
        if (titulo.contains("EXPLOT")) {
            return "escena_explota.png";
        }
        if (titulo.contains("PUDRIERON")) {
            return "escena_pudren.png";
        }
        if (titulo.contains("ARRUIN")) {
            return "escena_temperatura.png";
        }
        if (titulo.contains("NO LO TEN")) {
            return "escena_canjeaste.png";
        }
        return "escena_equivocado.png";
    }

    private String archivoEfecto(String titulo) {
        if (titulo.contains("EXPLOT")) {
            return "efecto_hollin.png";
        }
        if (titulo.contains("PUDRIERON")) {
            return "efecto_olor.png";
        }
        return "efecto_ups.png";
    }

    private String obtenerPista(String titulo) {
        if (titulo.contains("EXPLOT")) {
            return "Nunca dejes Ácido con Base ni Inflamable con Oxidante pegados en la fila.";
        }
        if (titulo.contains("PUDRIERON")) {
            return "No pongas dos químicos iguales seguidos en el mismo estante.";
        }
        if (titulo.contains("ARRUIN")) {
            return "Frío va en la heladera (H1-H3) y calor en los estantes (E1-E3).";
        }
        if (titulo.contains("NO LO TEN")) {
            return "Si canjeás un estante lleno, después no vas a poder entregar lo que estaba ahí.";
        }
        return "Revisá bien el tipo y la temperatura antes de confirmar la jugada.";
    }

    private void guardarPuntaje() {
        String letras = campoLetras.getText().trim().toUpperCase();
        if (letras.length() != 3 || !soloLetras(letras)) {
            aviso.setText("Tienen que ser exactamente 3 letras.");
            return;
        }
        ranking.agregar(letras, juego.getPuntaje(), juego.getTurno());
        panelIngreso.setVisible(false);
        actualizarRanking();
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
        listaRanking.removeAll();
        if (ranking.getCantidad() == 0) {
            JLabel vacio = new JLabel("Todavía no hay puntajes guardados.");
            vacio.setFont(Fuentes.texto(14f));
            vacio.setForeground(Colores.APAGADO);
            listaRanking.add(vacio);
        }
        for (int i = 0; i < ranking.getCantidad(); i++) {
            String linea = (i + 1) + ".  " + ranking.getIniciales(i)
                    + "   —   " + ranking.getPuntaje(i) + " pts  (Turno " + ranking.getTurno(i) + ")";
            JLabel fila = new JLabel(linea);
            fila.setFont(Fuentes.texto(15f));
            fila.setForeground(i == 0 ? Colores.ROSA : Colores.TINTA);
            fila.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
            listaRanking.add(fila);
        }
        listaRanking.revalidate();
        listaRanking.repaint();
    }

    private JLabel texto(String texto, Font fuente, Color color, int x, int y, int ancho, int alto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        etiqueta.setBounds(x, y, ancho, alto);
        return etiqueta;
    }

    private JPanel caja(Color fondo, int x, int y, int ancho, int alto) {
        JPanel caja = new JPanel(null);
        caja.setBackground(fondo);
        caja.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        caja.setBounds(x, y, ancho, alto);
        return caja;
    }

    private void pintarBoton(JButton boton, Color fondo) {
        boton.setBackground(fondo);
        boton.setForeground(Colores.TINTA);
        boton.setFont(Fuentes.titulo(16f));
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
    }

    private ImageIcon imagen(String archivo) {
        URL url = getClass().getResource("/img/" + archivo);
        if (url == null) {
            return null;
        }
        return new ImageIcon(url);
    }
}