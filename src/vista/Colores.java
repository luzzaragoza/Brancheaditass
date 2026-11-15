package vista;

import modelo.*;
import java.awt.Color;

public class Colores {
    public static final Color FONDO = new Color(34, 23, 41);
    public static final Color PARED_DEPOSITO = new Color (42, 29, 51);
    public static final Color TINTA = new Color(30, 20, 36);
    public static final Color CREMA = new Color(251, 241, 230);
    public static final Color APAGADO = new Color(107, 86, 112);
    public static final Color LAVANDA = new Color(196, 178, 214);
    public static final Color CARTEL_FRIO = new Color(212, 230, 245);
    public static final Color CARTEL_CALOR = new Color(247, 220, 198);
    public static final Color ROSA = new Color(255, 143, 184);
    public static final Color LYSS = new Color(255, 154, 124);
    public static final Color LUZ = new Color(185, 150, 255);
    public static final Color SOFI1 = new Color(255, 108, 159);
    public static final Color SOFI2 = new Color(230, 125, 208);
    public static final Color ACIDO = new Color(99, 217, 160);
    public static final Color BASE = new Color(111, 155, 255);
    public static final Color INFLAMABLE = new Color(255, 122, 89);
    public static final Color OXIDANTE = new Color(255, 210, 77);

    public static Color de(Quimico q) {
        if (q instanceof Acido) {
            return ACIDO;
        }
        if (q instanceof Base) {
            return BASE;
        }
        if (q instanceof Inflamable) {
            return INFLAMABLE;
        }
        if (q instanceof Oxidante) {
            return OXIDANTE;
        }
        return CREMA;
    }
}
