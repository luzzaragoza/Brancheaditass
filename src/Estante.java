public class Estante extends Contenedor {
    public Estante(String codigo) {
        super(codigo);
    }
    @Override
    public boolean aceptaTemperatura(Quimico q) {
        return !q.necesitaFrio();
    }
}