import java.util.ArrayList;

public abstract class Contenedor {
    private static final int LUGARES = 4;
    protected String codigo;
    protected int lugares;
    protected ArrayList<Quimico> quimicos;

    public Contenedor(String codigo) {
        this(codigo, LUGARES);
    }

    public Contenedor(String codigo, int lugares) {
        this.codigo = codigo;
        this.lugares = lugares;
        this.quimicos = new ArrayList<>();
    }

    public boolean estaLleno() {
        return quimicos.size() == lugares;
    }

    public boolean puedeGuardar(Quimico q) {
        if (estaLleno()) {
            return false;
        }
        if (!aceptaTemperatura(q)) {
            return false;
        }
        if (quimicos.size() == 0) {
            return true;
        }
        Quimico ultimo = quimicos.get(quimicos.size() - 1);
        return ultimo.esCompatibleCon(q);
    }

    public boolean guardar(Quimico q) {
        if (!puedeGuardar(q)) {
            return false;
        }
        quimicos.add(q);
        return true;
    }

    public boolean sacar(int posicion) {
        if (posicion < 0 || posicion >= quimicos.size()) {
            return true;
        }
        quimicos.remove(posicion);

        if (posicion > 0 && posicion < quimicos.size()) {
            Quimico antes = quimicos.get(posicion - 1);
            Quimico ahora = quimicos.get(posicion);
            return antes.esCompatibleCon(ahora);
        }
        return true;
    }

    public boolean sePuedeSacar(int posicion) { // va en contenedor y no en juego: contenedor es el que conoce su fila.
        if (posicion == 0 || posicion == quimicos.size() -1) {
            return true;
        }
        Quimico antes = quimicos.get(posicion -1);
        Quimico despues = quimicos.get(posicion +1);
        return antes.esCompatibleCon(despues);
    }

    void vaciar() {
        quimicos.clear();
    }

    public String getCodigo() { return codigo; }
    public int getCantidad() { return quimicos.size(); }
    public Quimico getQuimico(int posicion) { return quimicos.get(posicion); }

    public abstract boolean aceptaTemperatura(Quimico q);
}
