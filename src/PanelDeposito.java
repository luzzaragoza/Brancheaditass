import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;

public class PanelDeposito extends JPanel {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};
    private static final int[] FILAS_HELADERA = {77, 199, 323};
    private static final int[] FILAS_ESTANTES = {45, 178, 311};

    private Juego juego;
    private JLabel etiquetaTurno;
    private JLabel etiquetaPuntaje;
    private JLabel etiquetaLlevas;
    private JLabel cuadradito;
    private JLabel etiquetaPedido;
    private PanelContenedor[] paneles = new PanelContenedor[CODIGOS.length];
    private JLabel globoPedido;
    private JLabel etiquetaMensaje;
    private JLabel etiquetaEncargada;
    private JLabel etiquetaAyuda;
    private JButton botonVolver;

    public PanelDeposito(Juego juego) {
        this.juego = juego;
        setLayout(null);
        setBackground(Colores.PARED_DEPOSITO);

        // barra de arriba
        JPanel barra = new JPanel(null);
        barra.setBackground(Colores.TINTA);
        barra.setBounds(0, 0, 1024, 56);
        barra.add(texto("ALQUIMIA", Fuentes.titulo(20f), Colores.CREMA, 20, 14, 110, 28));
        barra.add(texto("· DEPÓSITO", Fuentes.texto(13f), Colores.LAVANDA, 122, 18, 120, 24));
        etiquetaLlevas = texto("", Fuentes.texto(12f), Colores.LAVANDA, 330, 16, 110, 24);
        etiquetaLlevas.setHorizontalAlignment(SwingConstants.RIGHT);
        barra.add(etiquetaLlevas);
        cuadradito = new JLabel();
        cuadradito.setOpaque(true);
        cuadradito.setBorder(BorderFactory.createLineBorder(Colores.CREMA, 2));
        cuadradito.setBounds(452, 15, 26, 26);
        barra.add(cuadradito);
        etiquetaPedido = texto("", Fuentes.texto(17f), Colores.CREMA, 490, 14, 300, 28);
        barra.add(etiquetaPedido);
        etiquetaTurno = texto("", Fuentes.titulo(18f), Colores.CREMA, 760, 14, 140, 28);
        etiquetaTurno.setHorizontalAlignment(SwingConstants.RIGHT);
        barra.add(etiquetaTurno);
        barra.add(texto("♥", new Font(Font.SANS_SERIF, Font.BOLD, 22), Colores.ROSA, 920, 13, 24, 28));
        etiquetaPuntaje = texto("", Fuentes.titulo(18f), Colores.CREMA, 946, 14, 70, 28);
        barra.add(etiquetaPuntaje);
        add(barra);

        // la heladera y la estantería son JLabel con la imagen, y los contenedores van adentro
        JLabel heladera = new JLabel(imagen("heladera_x2.png"));
        heladera.setLayout(null);
        heladera.setBounds(16, 103, 456, 514);
        heladera.add(cartel("HELADERA", 150, 10, 156, 22));

        JLabel estantes = new JLabel(imagen("estanteria_x2.png"));
        estantes.setLayout(null);
        estantes.setBounds(554, 158, 456, 458);
        estantes.add(cartel("ESTANTES", 40, 8, 150, 22));

        for (int i = 0; i < paneles.length; i++) {
            if (i < 3) {
                paneles[i] = new PanelContenedor(juego.getContenedor(CODIGOS[i]), Colores.CARTEL_FRIO);
                paneles[i].setLocation(22, FILAS_HELADERA[i]);
                heladera.add(paneles[i]);
            } else {
                paneles[i] = new PanelContenedor(juego.getContenedor(CODIGOS[i]), Colores.CARTEL_CALOR);
                paneles[i].setLocation(24, FILAS_ESTANTES[i - 3]);
                estantes.add(paneles[i]);
            }
        }
        add(heladera);
        add(estantes);

        // la encargada, entre la heladera y los estantes
        globoPedido = new JLabel();
        globoPedido.setOpaque(true);
        globoPedido.setHorizontalAlignment(SwingConstants.CENTER);
        globoPedido.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Colores.TINTA));
        globoPedido.setBounds(484, 432, 56, 78);
        add(globoPedido);
        etiquetaEncargada = new JLabel();
        etiquetaEncargada.setBounds(480, 524, 64, 90);
        add(etiquetaEncargada);

        // la parte de abajo
        JPanel piso = new JPanel(null);
        piso.setBackground(Colores.FONDO);
        piso.setBounds(0, 620, 1024, 100);

        JPanel cajaMensaje = new JPanel(null);
        cajaMensaje.setBackground(Colores.FONDO);
        cajaMensaje.setBorder(BorderFactory.createLineBorder(Colores.APAGADO, 2));
        cajaMensaje.setBounds(20, 22, 798, 62);
        etiquetaMensaje = texto(" ", Fuentes.texto(16f), Colores.CREMA, 20, 8, 770, 24);
        cajaMensaje.add(etiquetaMensaje);
        etiquetaAyuda = texto(" ", Fuentes.texto(14f), Colores.LAVANDA, 20, 32, 770, 22);
        cajaMensaje.add(etiquetaAyuda);
        piso.add(cajaMensaje);

        botonVolver = new JButton("<html><center>VOLVER AL<br>MOSTRADOR</center></html>");
        botonVolver.setBackground(Colores.FONDO);
        botonVolver.setForeground(Colores.CREMA);
        botonVolver.setFont(Fuentes.titulo(15f));
        botonVolver.setOpaque(true);
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createLineBorder(Colores.CREMA, 3));
        botonVolver.setBounds(835, 22, 168, 62);
        piso.add(botonVolver);
        add(piso);

        actualizar();
    }

    private JLabel texto(String texto, Font fuente, Color color, int x, int y, int ancho, int alto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        etiqueta.setBounds(x, y, ancho, alto);
        return etiqueta;
    }

    private JLabel cartel(String texto, int x, int y, int ancho, int alto) {
        JLabel cartel = texto(texto, Fuentes.titulo(14f), Colores.TINTA, x, y, ancho, alto);
        cartel.setOpaque(true);
        cartel.setBackground(Colores.CREMA);
        cartel.setHorizontalAlignment(SwingConstants.CENTER);
        cartel.setBorder(BorderFactory.createLineBorder(Colores.APAGADO, 1));
        return cartel;
    }

    private ImageIcon imagen(String archivo) {
        URL url = getClass().getResource("/img/" + archivo);
        if (url == null) {
            return null;
        }
        return new ImageIcon(url);
    }

    public void actualizar() {
        etiquetaEncargada.setIcon(imagen(juego.getEncargada().toLowerCase().replace(" ", "") + "_x2.png"));
        etiquetaTurno.setText("TURNO " + juego.getTurno());
        etiquetaPuntaje.setText("" + juego.getPuntaje());

        String lleno = null;
        for (int i = 0; i < paneles.length; i++) {
            paneles[i].actualizar(!juego.esRetiro());
            paneles[i].setActivo(!juego.estaTerminado());
            if (lleno == null && juego.getContenedor(CODIGOS[i]).estaLleno()) {
                lleno = CODIGOS[i];
            }
        }

        Quimico pedido = juego.getPedido();
        String nombre = pedido.getNombre().toLowerCase();
        String el = "el ";
        if (pedido instanceof Base) {
            el = "la ";
        }
        String temperatura;
        if (pedido.necesitaFrio()) {
            temperatura = "frío";
        } else {
            temperatura = "calor";
        }
        cuadradito.setBackground(Colores.de(pedido));
        globoPedido.setBackground(Colores.de(pedido));
        globoPedido.setIcon(imagen(pedido.getImagen()));

        if (juego.esRetiro()) {
            etiquetaLlevas.setText("TE PIDEN");
            etiquetaPedido.setText(pedido.getNombre() + " · estaba en " + temperatura);
            globoPedido.setVisible(false);
            mostrarMensaje("Hacé clic en el químico que vas a entregar. Si sacás uno del medio, se corren.");
        } else {
            etiquetaLlevas.setText("LLEVÁS");
            etiquetaPedido.setText(pedido.getNombre() + " · necesita " + temperatura);
            globoPedido.setVisible(true);
            mostrarMensaje("Hacé clic en el estante donde va " + el + nombre + ". Entra al final de la fila.");
        }

        if (lleno == null) {
            etiquetaAyuda.setText(" ");
        } else {
            etiquetaAyuda.setText(lleno + " está lleno: podés canjearlo por +" + Juego.PUNTOS_CANJE
                    + ", pero si después vienen a buscar algo de ahí, perdés.");
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

    public JButton getBotonVolver() {
        return botonVolver;
    }
}
