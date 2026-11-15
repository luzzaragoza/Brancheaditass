package vista;

import modelo.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import java.net.URL;

public class PanelContenedor extends JPanel {
    public static final int ANCHO = 410;
    public static final int ALTO = 116;

    private Contenedor contenedor;
    private Color fondo;
    private JButton[] casillas;
    private JButton botonCanjear;
    private Border[] bordes;
    private JLabel[] etiquetas;
    private JLabel etiquetaCuenta;

    public PanelContenedor(Contenedor contenedor, Color fondo) {
        this.contenedor = contenedor;
        this.fondo = fondo;
        casillas = new JButton[contenedor.getLugares()];
        bordes = new Border[contenedor.getLugares()];
        etiquetas = new JLabel[contenedor.getLugares()];
        setLayout(null);
        setOpaque(false);
        setSize(ANCHO, ALTO);

        JLabel codigo = new JLabel(contenedor.getCodigo());
        codigo.setOpaque(true);
        codigo.setBackground(Colores.TINTA);
        codigo.setForeground(Colores.CREMA);
        codigo.setFont(Fuentes.titulo(15f));
        codigo.setHorizontalAlignment(SwingConstants.CENTER);
        codigo.setBounds(12, 38, 38, 38);
        add(codigo);

        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new JButton();
            casillas[i].setBounds(62 + i * 62, 12, 52, 70);
            pintarBoton(casillas[i], Colores.CREMA);
            casillas[i].addMouseListener(new MouseEncima(i));
            add(casillas[i]);

            etiquetas[i] = new JLabel();
            etiquetas[i].setForeground(Colores.TINTA);
            etiquetas[i].setFont(Fuentes.texto(12f));
            etiquetas[i].setHorizontalAlignment(SwingConstants.CENTER);
            etiquetas[i].setBounds(62 + i * 62 - 5, 86, 62, 18);
            add(etiquetas[i]);
        }

        etiquetaCuenta = new JLabel();
        etiquetaCuenta.setForeground(Colores.APAGADO);
        etiquetaCuenta.setFont(Fuentes.titulo(15f));
        etiquetaCuenta.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaCuenta.setBounds(312, 38, 76, 38);
        add(etiquetaCuenta);

        botonCanjear = new JButton("<html><center>CANJEAR<br>+" + Juego.PUNTOS_CANJE + "</center></html>");
        pintarBoton(botonCanjear, Colores.ROSA);
        botonCanjear.setFont(Fuentes.titulo(13f));
        botonCanjear.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        botonCanjear.setBounds(312, 14, 80, 68);
        add(botonCanjear);
    }

    public void pintarBoton(JButton boton, Color fondo) {
        boton.setBackground(fondo);
        boton.setForeground(Colores.TINTA);
        boton.setFont(Fuentes.texto(18f));
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
    }

    public void actualizar(boolean esDejar) {
        int cantidad = contenedor.getCantidad();
        for (int i = 0; i < casillas.length; i++) {
            etiquetas[i].setText("" + (i + 1));
            if (i < cantidad) {
                Quimico q = contenedor.getQuimico(i);
                URL url = getClass().getResource("/img/" + q.getImagen());
                if (url != null) {
                    ImageIcon dibujo = new ImageIcon(url);
                    casillas[i].setIcon(dibujo);
                    casillas[i].setDisabledIcon(dibujo);
                    casillas[i].setText("");
                } else {
                    casillas[i].setIcon(null);
                    casillas[i].setText(q.getNombre());
                }
                bordes[i] = BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA);
                if (i == cantidad - 1) {
                    etiquetas[i].setText("último");
                }
            } else if (i == cantidad && esDejar) {
                casillas[i].setBackground(Colores.CREMA);
                casillas[i].setIcon(null);
                casillas[i].setText("+");
                bordes[i] = BorderFactory.createDashedBorder(Colores.APAGADO, 3, 6, 4, false);
            } else {
                casillas[i].setBackground(fondo);
                casillas[i].setIcon(null);
                casillas[i].setText("");
                bordes[i] = BorderFactory.createLineBorder(Colores.LAVANDA, 2);
            }
            casillas[i].setBorder(bordes[i]);
        }

        etiquetaCuenta.setText(cantidad + "/" + casillas.length);
        etiquetaCuenta.setVisible(!contenedor.estaLleno());
        botonCanjear.setVisible(contenedor.estaLleno());
    }

    public int getCantidadCasillas() {
        return casillas.length;
    }

    public void setActivo(boolean activo) {
        for (int i = 0; i < casillas.length; i++) {
            casillas[i].setEnabled(activo);
        }
        botonCanjear.setEnabled(activo);
    }

    private class MouseEncima extends MouseAdapter {
        private int posicion;

        public MouseEncima(int posicion) {
            this.posicion = posicion;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (casillas[posicion].isEnabled()) {
                Border rosa = BorderFactory.createLineBorder(Colores.ROSA, 2);
                casillas[posicion].setBorder(BorderFactory.createCompoundBorder(rosa, bordes[posicion]));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            casillas[posicion].setBorder(bordes[posicion]);
        }
    }

    public JButton getCasilla(int posicion) {
        return casillas[posicion];
    }

    public JButton getBotonCanjear() {
        return botonCanjear;
    }
}
