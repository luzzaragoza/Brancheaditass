public class Heladera extends Contenedor {
    public Heladera(String codigo) {
        super(codigo);
    }

    public boolean aceptaTemperatura(Quimico q) {
        return q.necesitaFrio();
    }
}