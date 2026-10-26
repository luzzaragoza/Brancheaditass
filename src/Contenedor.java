public abstract class Contenedor {
    private static final int LUGARES = 4;
    protected String codigo;
    protected Quimico[] quimicos;
    protected int cantidad;

    public Contenedor() {
        this("SIN CODIGO");
    }

    public Contenedor(String codigo) {
        this.codigo = codigo;
        this.quimicos = new Quimico[LUGARES];
        this.cantidad = 0;
    }

    public boolean estaLleno() {
        return cantidad == LUGARES;
    }

    public boolean puedeGuardar(Quimico q) {
        if (estaLleno()) {
            return false;
        }
        if (!aceptaTemperatura(q)) {
            return false;
        }
        if (cantidad == 0) {
            return true;
        }
        Quimico ultimo = quimicos[cantidad - 1];
        return ultimo.esCompatibleCon(q);
    }

    public boolean guardar(Quimico q) {
        if (!puedeGuardar(q)) {
            return false;
        }
        quimicos[cantidad] = q;
        cantidad++;
        return true;
    }

    public boolean sacar(int posicion) {
        for (int i = posicion; i < cantidad - 1; i++) {
            quimicos[i] = quimicos[i + 1];
        }
        cantidad--;
        quimicos[cantidad] = null;

        if (posicion > 0 && posicion < cantidad) {
            Quimico antes = quimicos[posicion - 1];
            Quimico ahora = quimicos[posicion];
            return antes.esCompatibleCon(ahora);
        }
        return true;
    }

    void vaciar() {
        for (int i = 0; i < cantidad; i++) {
            quimicos[i] = null;
        }
        cantidad = 0;
    }

    public String getCodigo() { return codigo; }
    public int getCantidad() { return cantidad; }
    public Quimico getQuimico(int posicion) { return quimicos[posicion]; }

    public abstract boolean aceptaTemperatura(Quimico q);
}