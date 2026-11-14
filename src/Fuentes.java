import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class Fuentes {
    private static final Font TITULO = cargar("/fuentes/PixelifySans-Bold.ttf");
    private static final Font TEXTO = cargar("/fuentes/Nunito-ExtraBold.ttf");

    private static Font cargar(String ruta) {
        try {
            InputStream archivo = Fuentes.class.getResourceAsStream(ruta);
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, archivo);
            archivo.close();
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuente);
            return fuente;
        } catch (Exception e) {
            System.out.println("No se pudo cargar " + ruta + ": se usa la fuente común.");
            return new Font(Font.SANS_SERIF, Font.BOLD, 12);
        }
    }

    public static Font titulo(float tamanio) {
        return TITULO.deriveFont(tamanio);
    }

    public static Font texto(float tamanio) {
        return TEXTO.deriveFont(tamanio);
    }
}
