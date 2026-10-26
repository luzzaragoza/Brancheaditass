import java.util.HashMap;

public class Deposito {
    private HashMap<String, Contenedor> contenedores = new HashMap<>();
    private String ultimoMotivo = "";

    public Deposito() {
        contenedores.put("H1", new Heladera("H1"));
        contenedores.put("H2", new Heladera("H2"));
        contenedores.put("H3", new Heladera("H3"));
        contenedores.put("E1", new Estante("E1"));
        contenedores.put("E2", new Estante("E2"));
        contenedores.put("E3", new Estante("E3"));
    }

    public Contenedor buscar(String codigo) {
        return contenedores.get(codigo);
    }

    public boolean guardar(Quimico q, String codigo) {
        Contenedor c = buscar(codigo);

        if (c == null) {
            ultimoMotivo = "No existe ese contenedor";
            return false;
        }
        if (c.estaLleno()) {
            ultimoMotivo = "El contenedor está lleno";
            return false;
        }
        if (!c.aceptaTemperatura(q)) {
            ultimoMotivo = "Temperatura incorrecta para este químico";
            return false;
        }
        if (c.getCantidad() > 0) {
            Quimico ultimo = c.getQuimico(c.getCantidad() -1);
            if (!ultimo.esCompatibleCon(q)) {
                ultimoMotivo = "Incompatible con el último químico guardado";
                return false;
            }
        }

        c.guardar(q);
        ultimoMotivo = "";
        return true;
    }

    public boolean guardar(Quimico q) {
        for (Contenedor c :
        contenedores.values()) {
            if (c.puedeGuardar(q)) {
                return guardar(q, c.getCodigo());
            }
        }
        ultimoMotivo = "No entra en ningún contenedor";
        return false;
    }

    public boolean retirar(String codigo, int posicion, Quimico pedido) {
        Contenedor c = buscar(codigo);
        if (c == null) {
            ultimoMotivo = "No existe ese contenedor";
            return false;
        }
        if (posicion < 0 || posicion >= c.getCantidad()) {
            ultimoMotivo = "Esa casilla está vacía";
            return false;
        }

        Quimico q = c.getQuimico(posicion);
        if (!q.coincideConPedido(pedido)) {
            ultimoMotivo = "Retiro equivocado";
            return false;
        }

        boolean ok = c.sacar(posicion);
        if (!ok) {
            ultimoMotivo = "Quedaron incompatibles pegados";
            return false;
        }

        ultimoMotivo = "";
        return true;
    }

    public String getUltimoMotivo() {
        return ultimoMotivo;
    }

    public boolean canjear(String codigo) {
        Contenedor c = buscar(codigo);

        if (c != null && c.estaLleno()) {
            c.vaciar();
            return true;
        }
        return false;
    }

}

