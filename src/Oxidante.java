public class Oxidante extends Quimico {
    public Oxidante(boolean necesitaFrio) {
        super(necesitaFrio);
    }

    @Override
    public String getNombre() { return "Oxidante"; }

    @Override
    public String getNombreEnTexto() { return "oxidante"; }

    @Override
    public boolean esFemenino() { return false; }

    @Override
    public String getImagen() { return "oxidante.png"; }

    @Override
    public boolean esCompatibleCon(Quimico otro) {
        return otro instanceof Acido || otro instanceof Base;
    }
}