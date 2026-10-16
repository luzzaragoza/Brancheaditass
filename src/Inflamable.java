public class Inflamable extends Quimico {
    public Inflamable(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public String getNombre() { return "Inflamable"; }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}