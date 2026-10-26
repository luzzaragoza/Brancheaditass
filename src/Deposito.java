import java.util.HashMap;

public class Deposito {
    private HashMap<String, Contenedor> contenedores = new HashMap<>();
    private String ultimoMotivo = "";
    private String ultimoTitulo = "";

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
            perder("¡NO EXISTE!", "No hay ningún contenedor " + codigo + ".");
            return false;
        }
        if (c.estaLleno()) {
            perder("¡NO ENTRA!", codigo + " ya tiene 4 químicos: había que canjearlo o guardar en otro.");
            return false;
        }
        if (!c.aceptaTemperatura(q)) {
            perder("¡SE ARRUINÓ!", "Guardaste " + q.getNombre() + " que necesita "
                    + q.getTemperatura() + " en " + codigo + ".");
            return false;
        }
        if (c.getCantidad() > 0) {
            Quimico ultimo = c.getQuimico(c.getCantidad() - 1);
            if (!ultimo.esCompatibleCon(q)) {
                String titulo = "¡EXPLOTÓ!";
                if (ultimo.esDelMismoTipoQue(q)) {
                    titulo = "¡SE PUDRIERON!";
                }
                perder(titulo, "Pusiste " + q.getNombre() + " detrás de " + ultimo.getNombre()
                        + " en " + codigo + ".");
                return false;
            }
        }

        c.guardar(q);
        limpiarMotivo();
        return true;
    }

    public boolean guardar(Quimico q) {
        for (Contenedor c :
        contenedores.values()) {
            if (c.puedeGuardar(q)) {
                return guardar(q, c.getCodigo());
            }
        }
        perder("¡NO ENTRA!", q.getNombre() + " de " + q.getTemperatura() + " no entra en ningún contenedor.");
        return false;
    }

    public boolean retirar(String codigo, int posicion, Quimico pedido) {
        Contenedor c = buscar(codigo);
        if (c == null) {
            perder("¡NO EXISTE!", "No hay ningún contenedor " + codigo + ".");
            return false;
        }
        if (posicion < 0 || posicion >= c.getCantidad()) {
            perder("¡ESE NO ERA!", "Te pidieron " + pedido.getNombre() + " de " + pedido.getTemperatura()
                    + " y elegiste una casilla vacía de " + codigo + ".");
            return false;
        }

        Quimico q = c.getQuimico(posicion);
        if (!q.coincideConPedido(pedido)) {
            perder("¡ESE NO ERA!", "Te pidieron " + pedido.getNombre() + " de " + pedido.getTemperatura()
                    + " y entregaste " + q.getNombre() + " de " + q.getTemperatura() + " de " + codigo + ".");
            return false;
        }

        boolean ok = c.sacar(posicion);
        if (!ok) {
            Quimico antes = c.getQuimico(posicion - 1);
            Quimico ahora = c.getQuimico(posicion);
            String titulo = "¡EXPLOTÓ!";
            if (antes.esDelMismoTipoQue(ahora)) {
                titulo = "¡SE PUDRIERON!";
            }
            perder(titulo, "Al sacar " + q.getNombre() + " de " + codigo + ", quedaron pegados "
                    + antes.getNombre() + " y " + ahora.getNombre() + ".");
            return false;
        }

        limpiarMotivo();
        return true;
    }

    private void perder(String titulo, String motivo) {
        ultimoTitulo = titulo;
        ultimoMotivo = motivo;
    }

    private void limpiarMotivo() {
        ultimoTitulo = "";
        ultimoMotivo = "";
    }

    public String getUltimoMotivo() {
        return ultimoMotivo;
    }

    public String getUltimoTitulo() {
        return ultimoTitulo;
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

