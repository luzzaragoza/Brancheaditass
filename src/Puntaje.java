public class Puntaje implements Comparable<Puntaje> {
    private String iniciales;
    private int puntos;

    public Puntaje(String iniciales, int puntos) {
        this.iniciales = iniciales;
        this.puntos = puntos;
    }

    public String getIniciales() {
        return iniciales;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public int compareTo(Puntaje otro) {
        return otro.puntos - this.puntos;
    }
}