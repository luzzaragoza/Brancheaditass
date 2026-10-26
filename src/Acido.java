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

    @Override
    public String getNombreEnTexto() {
        return "ácido";
    }

    @Override
    public boolean esFemenino() {
        return false;
    }

    @Override
    public String getImagen() {
        return "acido.png";
    }
}
