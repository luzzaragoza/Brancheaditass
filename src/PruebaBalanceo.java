import java.util.HashMap;

public class PruebaBalanceo {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};
    private static final int PARTIDAS = 1000;

    public static void main(String[] args) {
        int turnos = 0;
        int partidasConCanje = 0;
        int retirosEnElMedio = 0;
        HashMap<String, Integer> derrotas = new HashMap<>();

        for (int p = 0; p < PARTIDAS; p++) {
            Juego juego = new Juego();
            int canjes = 0;
            boolean seTrabo = false;
            while (!juego.estaTerminado() && juego.getTurno() < 1000) {
                if (juego.esRetiro()) {
                    if (!hayEnPunta(juego)) {
                        retirosEnElMedio++;
                    }
                    retirar(juego);
                } else {
                    String codigo = buscarDondeGuardar(juego);
                    if (codigo == null) {
                        codigo = buscarLlenoParaCanjear(juego);
                        if (codigo != null) {
                            juego.canjear(codigo);
                            canjes++;
                        } else {
                            seTrabo = true;
                            codigo = "H1";
                        }
                    }
                    juego.jugar(codigo, 0);
                }
            }

            turnos = turnos + juego.getTurno();
            if (canjes > 0) {
                partidasConCanje++;
            }
            String titulo = juego.getTitulo();
            if (seTrabo) {
                titulo = "No había lugar válido";
            }
            if (derrotas.containsKey(titulo)) {
                derrotas.put(titulo, derrotas.get(titulo) + 1);
            } else {
                derrotas.put(titulo, 1);
            }
        }

        System.out.println("Partidas jugadas: " + PARTIDAS);
        System.out.println("Turno promedio en que se pierde: " + (turnos / PARTIDAS));
        System.out.println("Partidas que llegan a canjear: " + (partidasConCanje * 100 / PARTIDAS) + "%");
        System.out.println("Retiros de un químico que solo estaba en el medio: " + retirosEnElMedio + " (tiene que dar 0)");
        System.out.println("De qué se pierde:");
        for (String titulo : derrotas.keySet()) {
            System.out.println("   " + titulo + " " + (derrotas.get(titulo) * 100 / PARTIDAS) + "%");
        }
    }

    private static String buscarDondeGuardar(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            if (juego.getContenedor(CODIGOS[i]).puedeGuardar(juego.getPedido())) {
                return CODIGOS[i];
            }
        }
        return null;
    }

    private static String buscarLlenoParaCanjear(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            if (c.estaLleno() && c.aceptaTemperatura(juego.getPedido())) {
                return CODIGOS[i];
            }
        }
        return null;
    }

    private static boolean hayEnPunta(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            int ultimo = c.getCantidad() - 1;
            if (ultimo >= 0) {
                if (c.getQuimico(0).coincideConPedido(juego.getPedido())
                        || c.getQuimico(ultimo).coincideConPedido(juego.getPedido())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void retirar(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            int ultimo = c.getCantidad() - 1;
            if (ultimo >= 0) {
                if (c.getQuimico(0).coincideConPedido(juego.getPedido())) {
                    juego.jugar(CODIGOS[i], 0);
                    return;
                }
                if (c.getQuimico(ultimo).coincideConPedido(juego.getPedido())) {
                    juego.jugar(CODIGOS[i], ultimo);
                    return;
                }
            }
        }
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            for (int j = 0; j < c.getCantidad(); j++) {
                if (c.getQuimico(j).coincideConPedido(juego.getPedido())) {
                    juego.jugar(CODIGOS[i], j);
                    return;
                }
            }
        }
    }
}
