/**
 * Guarda los 6 contenedores del juego y delega en ellos
 * las operaciones de guardar, retirar y canjear.
 */
public class Deposito {
    private Contenedor[] contenedores = new Contenedor[6]; // ejemplo de encapsulamiento --> con private solo la clase Deposito puede tocarlo.
    private String ultimoMotivo;

    public Deposito() {
        contenedores[0] = new Heladera("H1");
        contenedores[1] = new Heladera("H2");
        contenedores[2] = new Heladera("H3");
        contenedores[3] = new Estante("E1");
        contenedores[4] = new Estante("E2");
        contenedores[5] = new Estante("E3");
    }

    private Contenedor buscar(String codigo) {
        for (int i = 0; i < contenedores.length; i++) {
            if (contenedores[i].getCodigo().equals(codigo)) {
                return contenedores[i];
            }
        }
        return null;
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
        if (!c.temperaturaCorrecta(q)) {
            ultimoMotivo = "Temperatura incorrecta para este químico";
            return false;
        }
        if (!c.esCompatible(q)) {
            ultimoMotivo = "Incompatible con el último químico guardado";
            return false;
        }

        c.agregar(q);
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

