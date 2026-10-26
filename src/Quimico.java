public abstract class Quimico {
    protected boolean necesitaFrio;

    public Quimico(boolean necesitaFrio) {
        this.necesitaFrio = necesitaFrio;
    }

    public boolean necesitaFrio() {
        return necesitaFrio;
    }

    public boolean esDelMismoTipoQue(Quimico otro) {
        return getNombre().equals(otro.getNombre());
    }

    public boolean coincideConPedido(Quimico pedido) {
        return esDelMismoTipoQue(pedido) && necesitaFrio == pedido.necesitaFrio();
    }

    public String getTemperatura() {
        if (necesitaFrio) {
            return "frío";
        }
        return "calor";
    }

    public String conArticulo() {
        if (esFemenino()) {
            return "una " + getNombreEnTexto();
        }
        return "un " + getNombreEnTexto();
    }

    public String conArticuloDefinido() {
        if (esFemenino()) {
            return "la " + getNombreEnTexto();
        }
        return "el " + getNombreEnTexto();
    }

    public String conOtro() {
        if (esFemenino()) {
            return "otra " + getNombreEnTexto();
        }
        return "otro " + getNombreEnTexto();
    }

    public abstract boolean esCompatibleCon(Quimico otro);

    public abstract String getNombre();

    public abstract String getNombreEnTexto();

    public abstract boolean esFemenino();

    public abstract String getImagen();
}
