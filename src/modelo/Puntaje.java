package modelo;

public class Puntaje implements Comparable<Puntaje> {
    private String iniciales;
    private int puntos;
    private int turno;

    public Puntaje(String iniciales, int puntos, int turno) {
        this.iniciales = iniciales;
        this.puntos = puntos;
        this.turno = turno;
    }

    public String getIniciales() {
        return iniciales;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getTurno() {
        return turno;
    }

    @Override
    public int compareTo(Puntaje otro) {
        return otro.puntos - this.puntos;
    }
}