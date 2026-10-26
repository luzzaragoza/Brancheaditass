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

    public abstract boolean esCompatibleCon(Quimico otro);

    public abstract String getNombre();

    public abstract String getImagen();
}
