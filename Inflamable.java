public class Inflamable extends Quimico {
    public Inflamable(String nombre, boolean necesitaFrio) {
        super(nombre, necesitaFrio);
    }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}