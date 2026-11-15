import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    public static final int CUPOS = 5;

    private ArrayList<Puntaje> puntajes = new ArrayList<>();

    public boolean entra(int puntos) {
        if (puntajes.size() < CUPOS) {
            return true;
        }
        return puntos > puntajes.get(puntajes.size() - 1).getPuntos();
    }

    public void agregar(String iniciales, int puntos) {
        if (!entra(puntos)) {
            return;
        }
        puntajes.add(new Puntaje(iniciales, puntos));
        Collections.sort(puntajes);
        if (puntajes.size() > CUPOS) {
            puntajes.remove(puntajes.size() - 1);
        }
    }

    public int getCantidad() {
        return puntajes.size();
    }

    public String getIniciales(int posicion) {
        return puntajes.get(posicion).getIniciales();
    }

    public int getPuntaje(int posicion) {
        return puntajes.get(posicion).getPuntos();
    }
}