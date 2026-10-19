public class Acido extends Quimico {

    public Acido(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return !(otro instanceof Acido || otro instanceof Base);
    }

    @Override
    public String getNombre() {
        return "Acido";
    }
}
