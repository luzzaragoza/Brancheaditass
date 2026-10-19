public class Estante extends Contenedor {
    public Estante(String codigo) {
        super(codigo);
    }

    public boolean aceptaTemperatura(Quimico q) {
        return !q.necesitaFrio();
    }
}