package vista;

import modelo.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

public class PanelMostrador extends JPanel {
    private Juego juego;
    private Ranking ranking = new Ranking();
    private JLabel etiquetaTurno;
    private JLabel etiquetaPuntaje;
    private JLabel corona;
    private JLabel etiquetaMejor;
    private JLabel etiquetaRecord;
    private JLabel escena;
    private JLabel etiquetaVisitante;
    private JLabel etiquetaEncargada;
    private JLabel sobreMostrador;
    private JLabel etiquetaNombre;
    private JLabel etiquetaViene;
    private JLabel etiquetaDialogo;
    private JLabel cajaIcono;
    private JLabel chipTipo;
    private JLabel etiquetaPedido;
    private JLabel chipTemperatura;
    private JLabel etiquetaQuien;
    private JButton botonIr;
    private JLabel etiquetaAyuda;

    public PanelMostrador(Juego juego) {
        this.juego = juego;
        setLayout(null);
        setBackground(Colores.FONDO);

        // barra de arriba
        JPanel barra = new JPanel(null);
        barra.setBackground(Colores.TINTA);
        barra.setBounds(0, 0, 1024, 56);
        barra.add(texto("ALQUIMIA", Fuentes.titulo(20f), Colores.CREMA, 20, 14, 110, 28));
        barra.add(texto("· MOSTRADOR", Fuentes.texto(13f), Colores.LAVANDA, 122, 18, 120, 24));
        etiquetaTurno = texto("", Fuentes.titulo(18f), Colores.CREMA, 610, 14, 140, 28);
        etiquetaTurno.setHorizontalAlignment(SwingConstants.RIGHT);
        barra.add(etiquetaTurno);
        barra.add(texto("♥", new Font(Font.SANS_SERIF, Font.BOLD, 22), Colores.ROSA, 770, 13, 24, 28));
        etiquetaPuntaje = texto("", Fuentes.titulo(18f), Colores.CREMA, 796, 14, 70, 28);
        barra.add(etiquetaPuntaje);
        corona = new JLabel(imagen("corona.png"));
        corona.setBounds(870, 21, 21, 12);
        barra.add(corona);
        etiquetaMejor = texto("MEJOR", Fuentes.texto(12f), Colores.LAVANDA, 898, 16, 50, 24);
        barra.add(etiquetaMejor);
        etiquetaRecord = texto("", Fuentes.titulo(18f), Colores.CREMA, 946, 14, 70, 28);
        barra.add(etiquetaRecord);
        add(barra);

        // la escena: el fondo es un JLabel con la imagen, y adentro van las personas y el globo
        escena = new JLabel(imagen("fondo_mostrador.png"));
        escena.setLayout(null);
        escena.setBounds(0, 56, 1024, 444);

        JPanel globo = caja(Colores.CREMA, 24, 20, 380, 146);
        etiquetaNombre = texto("", Fuentes.titulo(12f), Colores.CREMA, 18, 14, 100, 22);
        etiquetaNombre.setOpaque(true);
        etiquetaNombre.setBackground(Colores.TINTA);
        etiquetaNombre.setHorizontalAlignment(SwingConstants.CENTER);
        globo.add(etiquetaNombre);
        etiquetaViene = texto("", Fuentes.texto(13f), Colores.APAGADO, 128, 14, 200, 22);
        globo.add(etiquetaViene);
        etiquetaDialogo = texto("", Fuentes.texto(18f), Colores.TINTA, 18, 42, 344, 80);
        etiquetaDialogo.setVerticalAlignment(SwingConstants.TOP);
        globo.add(etiquetaDialogo);
        escena.add(globo);

        // lo que está arriba del mostrador: el frasco que traen o el ticket del retiro
        sobreMostrador = new JLabel();
        escena.add(sobreMostrador);

        etiquetaVisitante = new JLabel();
        etiquetaVisitante.setBounds(270, 192, 128, 180);
        etiquetaVisitante.setForeground(Colores.CREMA);
        escena.add(etiquetaVisitante);

        etiquetaEncargada = new JLabel(imagen(imagenEncargada("_x4.png")));
        etiquetaEncargada.setBounds(636, 192, 128, 180);
        escena.add(etiquetaEncargada);

        JLabel vos = texto("VOS", Fuentes.titulo(12f), Colores.TINTA, 676, 394, 44, 22);
        vos.setOpaque(true);
        vos.setBackground(Colores.CREMA);
        vos.setHorizontalAlignment(SwingConstants.CENTER);
        escena.add(vos);
        add(escena);

        // la tarjeta del pedido
        JPanel tarjeta = caja(Colores.CREMA, 20, 520, 668, 178);
        cajaIcono = new JLabel();
        cajaIcono.setHorizontalAlignment(SwingConstants.CENTER);
        cajaIcono.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        cajaIcono.setBounds(40, 18, 98, 135);
        tarjeta.add(cajaIcono);
        tarjeta.add(texto("PEDIDO", Fuentes.texto(13f), Colores.APAGADO, 162, 30, 60, 24));
        chipTipo = texto("", Fuentes.titulo(13f), Colores.TINTA, 226, 30, 76, 24);
        chipTipo.setOpaque(true);
        chipTipo.setHorizontalAlignment(SwingConstants.CENTER);
        chipTipo.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
        tarjeta.add(chipTipo);
        etiquetaPedido = texto("", Fuentes.titulo(36f), Colores.TINTA, 162, 58, 400, 50);
        tarjeta.add(etiquetaPedido);
        chipTemperatura = texto("", Fuentes.titulo(13f), Colores.TINTA, 162, 114, 160, 32);
        chipTemperatura.setOpaque(true);
        chipTemperatura.setHorizontalAlignment(SwingConstants.CENTER);
        chipTemperatura.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
        tarjeta.add(chipTemperatura);
        etiquetaQuien = texto("", Fuentes.texto(15f), Colores.APAGADO, 338, 118, 320, 24);
        tarjeta.add(etiquetaQuien);
        add(tarjeta);

        botonIr = new JButton();
        botonIr.setBackground(Colores.ROSA);
        botonIr.setForeground(Colores.TINTA);
        botonIr.setFont(Fuentes.titulo(18f));
        botonIr.setHorizontalAlignment(SwingConstants.LEFT);
        botonIr.setOpaque(true);
        botonIr.setFocusPainted(false);
        botonIr.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)));
        botonIr.setBounds(705, 548, 298, 68);
        add(botonIr);

        etiquetaAyuda = texto("", Fuentes.texto(15f), Colores.LAVANDA, 705, 626, 300, 50);
        etiquetaAyuda.setVerticalAlignment(SwingConstants.TOP);
        add(etiquetaAyuda);

        actualizar();
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

    private ImageIcon imagen(String archivo) {
        URL url = getClass().getResource("/img/" + archivo);
        if (url == null) {
            return null;
        }
        return new ImageIcon(url);
    }

    private String imagenEncargada(String terminacion) {
        return juego.getEncargada().toLowerCase().replace(" ", "") + terminacion;
    }

    private String conArticulo(String nombre) {
        if (nombre.startsWith("Dra.")) {
            return "la " + nombre;
        }
        if (nombre.startsWith("Dr.") || nombre.startsWith("Ing.")) {
            return "el " + nombre;
        }
        return nombre;
    }

    public void actualizar() {
        etiquetaEncargada.setIcon(imagen(imagenEncargada("_x4.png")));
        etiquetaTurno.setText("TURNO " + juego.getTurno());
        etiquetaPuntaje.setText("" + juego.getPuntaje());
        mostrarRecord();

        Visitante visitante = juego.getVisitante();
        etiquetaNombre.setText(visitante.getNombre().toUpperCase());
        ImageIcon cara = imagen(visitante.getImagen());
        etiquetaVisitante.setIcon(cara);
        if (cara == null) {
            etiquetaVisitante.setText("[" + visitante.getNombre() + "]");
        } else {
            etiquetaVisitante.setText("");
        }

        Quimico pedido = juego.getPedido();
        String nombre = pedido.getNombre().toLowerCase();
        String un = "un ";
        String el = "el ";
        if (pedido.esFemenino()) {
            un = "una ";
            el = "la ";
        }
        String temperatura;
        if (pedido.necesitaFrio()) {
            temperatura = "frío";
            chipTemperatura.setBackground(Colores.CARTEL_FRIO);
        } else {
            temperatura = "calor";
            chipTemperatura.setBackground(Colores.CARTEL_CALOR);
        }

        ImageIcon dibujo = imagen(pedido.getImagen());
        if (dibujo != null) {
            Image grande = dibujo.getImage().getScaledInstance(92, 126, Image.SCALE_REPLICATE);
            cajaIcono.setIcon(new ImageIcon(grande));
        }
        etiquetaPedido.setText(pedido.getNombre());

        if (juego.esRetiro()) {
            etiquetaViene.setText("viene a retirar");
            etiquetaDialogo.setText("<html>Vengo a buscar " + el + nombre + " que dejé. Estaba en "
                    + temperatura + ", ¿te acordás?</html>");
            chipTipo.setText("RETIRAR");
            chipTipo.setBackground(Colores.TINTA);
            chipTipo.setForeground(Colores.CREMA);
            chipTemperatura.setText("ESTABA EN " + temperatura.toUpperCase());
            sobreMostrador.setIcon(imagen("ticket.png"));
            sobreMostrador.setBounds(492, 204, 40, 40);
            etiquetaQuien.setText("Lo busca " + conArticulo(visitante.getNombre()));
            botonIr.setText("IR A BUSCARLO  »");
            etiquetaAyuda.setText("<html>Allá atrás elegís cuál le entregás.</html>");
        } else {
            etiquetaViene.setText("viene a dejar");
            etiquetaDialogo.setText("<html>¡Hola! Te traigo " + un + nombre + ". Tiene que ir al "
                    + temperatura + ", ¿sí?</html>");
            chipTipo.setText("DEJAR");
            chipTipo.setBackground(Colores.ROSA);
            chipTipo.setForeground(Colores.TINTA);
            chipTemperatura.setText("NECESITA " + temperatura.toUpperCase());
            sobreMostrador.setIcon(imagen("frasco_" + pedido.getImagen()));
            sobreMostrador.setBounds(492, 196, 40, 48);
            etiquetaQuien.setText("Lo deja " + conArticulo(visitante.getNombre()));
            botonIr.setText("IR AL DEPÓSITO  »");
            etiquetaAyuda.setText("<html>Allá atrás elegís en qué estante guardarlo.</html>");
        }

        animarLlegada();
    }

    // el mejor puntaje de puntajes.txt; si todavía no hay ninguno, no se muestra
    private void mostrarRecord() {
        boolean hayRecord = ranking.getCantidad() > 0;
        corona.setVisible(hayRecord);
        etiquetaMejor.setVisible(hayRecord);
        etiquetaRecord.setVisible(hayRecord);
        if (hayRecord) {
            etiquetaRecord.setText("" + ranking.getPuntaje(0));
        }
    }

    private void animarLlegada() {
        Thread hilo = new Thread(new LlegadaVisitante());
        hilo.start();
    }

    private class LlegadaVisitante implements Runnable {
        @Override
        public void run() {
            for (int x = -140; x <= 270; x = x + 8) {
                SwingUtilities.invokeLater(new MoverVisitante(x));
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    return;
                }
            }
            SwingUtilities.invokeLater(new MoverVisitante(270));
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
