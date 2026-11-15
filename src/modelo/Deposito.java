package modelo;

import java.util.HashMap;

public class Deposito {
    private HashMap<String, Contenedor> contenedores = new HashMap<>();

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

    public void guardar(Quimico q, String codigo) throws JugadaInvalidaException {
        Contenedor c = buscar(codigo);

        if (c == null) {
            throw new JugadaInvalidaException("¡NO EXISTE!", "No hay ningún contenedor " + codigo + ".");
        }
        if (c.estaLleno()) {
            throw new JugadaInvalidaException("¡NO ENTRA!", codigo + " ya tiene " + c.getLugares() + " químicos: había que canjearlo o guardar en otro.");
        }
        if (!c.aceptaTemperatura(q)) {
            throw new JugadaInvalidaException("¡SE ARRUINÓ!", "Guardaste " + q.getNombre() + " que necesita "
                    + q.getTemperatura() + " en " + codigo + ".");
        }
        if (c.getCantidad() > 0) {
            Quimico ultimo = c.getQuimico(c.getCantidad() - 1);
            if (!ultimo.esCompatibleCon(q)) {
                String titulo = "¡EXPLOTÓ!";
                if (ultimo.esDelMismoTipoQue(q)) {
                    titulo = "¡SE PUDRIERON!";
                }
                throw new JugadaInvalidaException(titulo, "Pusiste " + q.getNombre() + " detrás de "
                        + ultimo.getNombre() + " en " + codigo + ".");
            }
        }

        c.guardar(q);
    }

    public void guardar(Quimico q) throws JugadaInvalidaException {
        for (Contenedor c : contenedores.values()) {
            if (c.puedeGuardar(q)) {
                guardar(q, c.getCodigo());
                return;
            }
        }
        throw new JugadaInvalidaException("¡NO ENTRA!", q.getNombre() + " de " + q.getTemperatura()
                + " no entra en ningún contenedor.");
    }

    public void retirar(String codigo, int posicion, Quimico pedido) throws JugadaInvalidaException {
        Contenedor c = buscar(codigo);
        if (c == null) {
            throw new JugadaInvalidaException("¡NO EXISTE!", "No hay ningún contenedor " + codigo + ".");
        }
        if (posicion < 0 || posicion >= c.getCantidad()) {
            throw new JugadaInvalidaException("¡ESE NO ERA!", "Te pidieron " + pedido.getNombre() + " de "
                    + pedido.getTemperatura() + " y elegiste una casilla vacía de " + codigo + ".");
        }

        Quimico q = c.getQuimico(posicion);
        if (!q.coincideConPedido(pedido)) {
            throw new JugadaInvalidaException("¡ESE NO ERA!", "Te pidieron " + pedido.getNombre() + " de "
                    + pedido.getTemperatura() + " y entregaste " + q.getNombre() + " de "
                    + q.getTemperatura() + " de " + codigo + ".");
        }

        boolean ok = c.sacar(posicion);
        if (!ok) {
            Quimico antes = c.getQuimico(posicion - 1);
            Quimico ahora = c.getQuimico(posicion);
            String titulo = "¡EXPLOTÓ!";
            if (antes.esDelMismoTipoQue(ahora)) {
                titulo = "¡SE PUDRIERON!";
            }
            throw new JugadaInvalidaException(titulo, "Al sacar " + q.getNombre() + " de " + codigo
                    + ", quedaron pegados " + antes.getNombre() + " y " + ahora.getNombre() + ".");
        }
    }

    public void canjear(String codigo) throws JugadaInvalidaException {
        Contenedor c = buscar(codigo);
        if (c == null) {
            throw new JugadaInvalidaException("NO SE PUEDE CANJEAR", "No hay ningún contenedor " + codigo + ".");
        }
        if (!c.estaLleno()) {
            throw new JugadaInvalidaException("NO SE PUEDE CANJEAR", codigo
                    + " no está lleno. Solo se puede canjear uno lleno (" + c.getLugares() + " químicos).");
        }
        c.vaciar();
    }
}
