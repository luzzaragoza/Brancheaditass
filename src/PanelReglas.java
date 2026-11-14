import javax.swing.*;
import java.awt.Color;
import java.net.URL;

public class PanelReglas extends JPanel {
    private JButton botonEmpezar;

    public PanelReglas() {
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

        JLabel bajada = new JLabel("<html>Del otro lado de la ventana llegan a dejar o retirar químicos.<br>"
                + "Vos decidís dónde va cada uno.</html>");
        bajada.setForeground(Colores.CREMA);
        bajada.setFont(Fuentes.texto(16f));
        bajada.setBounds(40, 160, 600, 50);
        add(bajada);

        add(cara("mora_cara.png", 720));
        add(cara("bigotes_cara.png", 858));

        JPanel tarjeta1 = tarjeta(1, "Frío a la heladera, calor al estante",
                "Cada químico avisa qué temperatura necesita. Si lo guardás en el lugar equivocado, perdés.", 40);
        tarjeta1.add(cartel("Necesita frío » Heladera", Colores.CARTEL_FRIO, 82));
        tarjeta1.add(cartel("Necesita calor » Estante", Colores.CARTEL_CALOR, 130));
        add(tarjeta1);

        JPanel tarjeta2 = tarjeta(2, "Nunca dos del mismo grupo pegados",
                "Ácido y Base son un grupo; Inflamable y Oxidante, el otro. Se alternan, y tampoco van dos iguales.", 360);
        String[] dibujos = {"acido.png", "inflamable.png", "base.png", "oxidante.png"};
        for (int i = 0; i < dibujos.length; i++) {
            tarjeta2.add(dibujo(dibujos[i], 20 + i * 50, 82));
        }
        JLabel bien = new JLabel("✔ bien");
        bien.setForeground(Colores.TINTA);
        bien.setBounds(222, 100, 70, 24);
        tarjeta2.add(bien);
        add(tarjeta2);

        JPanel tarjeta3 = tarjeta(3, "Si sacás uno del medio, se corren",
                "Lo nuevo entra siempre al final. Al retirar, los que quedan pegados también se tienen que llevar bien.", 680);
        tarjeta3.add(dibujo("acido.png", 20, 82));
        tarjeta3.add(dibujo("inflamable.png", 70, 82));
        tarjeta3.add(dibujo("base.png", 120, 82));
        JLabel sale = new JLabel("<html>Si sale el inflamable,<br>ácido y base quedan<br>pegados: ✘</html>");
        sale.setForeground(Colores.TINTA);
        sale.setBounds(172, 82, 130, 63);
        tarjeta3.add(sale);
        add(tarjeta3);

        botonEmpezar = new JButton("EMPEZAR A ATENDER");
        botonEmpezar.setFont(Fuentes.titulo(20f));
        botonEmpezar.setBackground(Colores.ROSA);
        botonEmpezar.setForeground(Colores.TINTA);
        botonEmpezar.setOpaque(true);
        botonEmpezar.setFocusPainted(false);
        botonEmpezar.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        botonEmpezar.setBounds(40, 590, 266, 58);
        add(botonEmpezar);

        JLabel canje = new JLabel("<html>Estante lleno: podés canjearlo por puntos extra. Pero si "
                + "después vuelven a buscar algo de ahí, perdés.</html>");
        canje.setForeground(Colores.CREMA);
        canje.setFont(Fuentes.texto(14f));
        canje.setBounds(340, 590, 440, 50);
        add(canje);

        JLabel equipo = new JLabel("Sofia1 · Sofia2 · Lyssie · Luz");
        equipo.setForeground(Colores.APAGADO);
        equipo.setFont(Fuentes.texto(13f));
        equipo.setBounds(800, 605, 200, 20);
        add(equipo);
    }

    private JPanel tarjeta(int numero, String titulo, String texto, int x) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(null);
        tarjeta.setBackground(Colores.CREMA);
        tarjeta.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        tarjeta.setBounds(x, 245, 304, 315);

        JLabel etiquetaNumero = new JLabel("" + numero, SwingConstants.CENTER);
        etiquetaNumero.setOpaque(true);
        etiquetaNumero.setBackground(Colores.ROSA);
        etiquetaNumero.setForeground(Colores.TINTA);
        etiquetaNumero.setFont(Fuentes.titulo(16f));
        etiquetaNumero.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
        etiquetaNumero.setBounds(20, 22, 26, 26);
        tarjeta.add(etiquetaNumero);

        JLabel etiquetaTitulo = new JLabel("<html>" + titulo + "</html>");
        etiquetaTitulo.setForeground(Colores.TINTA);
        etiquetaTitulo.setFont(Fuentes.titulo(18f));
        etiquetaTitulo.setBounds(58, 16, 230, 48);
        tarjeta.add(etiquetaTitulo);

        JLabel etiquetaTexto = new JLabel("<html>" + texto + "</html>");
        etiquetaTexto.setForeground(Colores.TINTA);
        etiquetaTexto.setFont(Fuentes.texto(14f));
        etiquetaTexto.setBounds(20, 180, 264, 110);
        etiquetaTexto.setVerticalAlignment(SwingConstants.TOP);
        tarjeta.add(etiquetaTexto);

        return tarjeta;
    }

    private JLabel cartel(String texto, Color fondo, int y) {
        JLabel cartel = new JLabel(texto, SwingConstants.CENTER);
        cartel.setOpaque(true);
        cartel.setBackground(fondo);
        cartel.setForeground(Colores.TINTA);
        cartel.setFont(Fuentes.texto(14f));
        cartel.setBorder(BorderFactory.createLineBorder(Colores.TINTA, 2));
        cartel.setBounds(20, y, 264, 38);
        return cartel;
    }

    private JLabel dibujo(String archivo, int x, int y) {
        JLabel dibujo = new JLabel();
        URL url = getClass().getResource("/img/" + archivo);
        if (url != null) {
            dibujo.setIcon(new ImageIcon(url));
        }
        dibujo.setBounds(x, y, 46, 63);
        return dibujo;
    }

    private JLabel cara(String archivo, int x) {
        JLabel cara = new JLabel("", SwingConstants.CENTER);
        URL url = getClass().getResource("/img/" + archivo);
        if (url != null) {
            cara.setIcon(new ImageIcon(url));
        }
        cara.setOpaque(true);
        cara.setBackground(Colores.LUZ);
        cara.setBorder(BorderFactory.createMatteBorder(3, 3, 6, 3, Colores.TINTA));
        cara.setBounds(x, 66, 124, 124);
        return cara;
    }

    public JButton getBotonEmpezar() {
        return botonEmpezar;
    }
}