public abstract class Quimico {
    protected boolean necesitaFrio;

    public Quimico(boolean necesitaFrio) {
        this.necesitaFrio = necesitaFrio;
    }

    public boolean necesitaFrio() {
        return necesitaFrio;
    }

    public boolean coincideConPedido(Quimico pedido) {
        return getNombre().equals(pedido.getNombre()) && necesitaFrio == pedido.necesitaFrio();
    }

    public abstract boolean esCompatibleCon(Quimico otro);

    public abstract String getNombre();
}
