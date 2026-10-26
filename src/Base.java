public class Base extends Quimico {

    public Base(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return !(otro instanceof Acido || otro instanceof Base);
    }

    @Override
    public String getNombre() {
        return "Base";
    }

    @Override
    public String getImagen() {
        return "base.png";
    }
}
