public class Oxidante extends Quimico {
    public Oxidante(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public String getNombre() { return "Oxidante"; }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}