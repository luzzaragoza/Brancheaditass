public class Oxidante extends Quimico {
    public Oxidante(String nombre, boolean necesitaFrio) {
        super(nombre, necesitaFrio);
    }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}