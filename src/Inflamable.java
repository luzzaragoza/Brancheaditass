public class Inflamable extends Quimico {
    public Inflamable(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public String getNombre() { return "Inflamable"; }

    @Override
    public String getNombreEnTexto() { return "inflamable"; }

    @Override
    public boolean esFemenino() { return false; }

    @Override
    public String getImagen() { return "inflamable.png"; }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}