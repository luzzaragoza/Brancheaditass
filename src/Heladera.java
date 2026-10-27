public class Heladera extends Contenedor {
    public Heladera(String codigo) {
        super(codigo);
    }

    @Override
    public boolean aceptaTemperatura(Quimico q) {
        return q.necesitaFrio();
    }
}