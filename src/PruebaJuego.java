public class PruebaJuego {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};

    public static void main(String[] args) {
        System.out.println("=== Parte 1: sin pendientes, 20 pedidos ===");
        int dejar = 0;
        for (int i = 1; i <= 20; i++) {
            Juego juego = new Juego();
            System.out.println(i + ". " + describir(juego));
            if (!juego.esRetiro()) {
                dejar++;
            }
        }
        System.out.println("De dejar: " + dejar + " de 20 (tiene que dar 20)");

        System.out.println();
        System.out.println("=== Parte 2: una partida de hasta 40 turnos ===");
        Juego juego = new Juego();
        int retiros = 0;
        int canjes = 0;
        while (!juego.estaTerminado() && juego.getTurno() <= 40) {
            System.out.println("Turno " + juego.getTurno() + ": " + describir(juego)
                    + "  (pendientes: " + juego.getCantidadPendientes() + ")");

            if (juego.esRetiro()) {
                retiros++;
                String[] lugar = buscarParaRetirar(juego);
                juego.jugar(lugar[0], Integer.parseInt(lugar[1]));
            } else {
                String codigo = buscarDondeGuardar(juego);
                if (codigo == null) {
                    codigo = "E1";
                }
                juego.jugar(codigo, 0);

                String lleno = buscarLleno(juego);
                if (lleno != null && juego.canjear(lleno)) {
                    canjes++;
                    System.out.println("   Canje de " + lleno + ". Puntaje: " + juego.getPuntaje());
                }
            }
        }
        System.out.println();
        System.out.println("Terminó: " + juego.estaTerminado() + "  Motivo: " + juego.getMotivo());
        System.out.println("Turno: " + juego.getTurno() + "  Puntaje: " + juego.getPuntaje()
                + "  Retiros pedidos: " + retiros + "  Canjes: " + canjes);
    }

    private static String describir(Juego juego) {
        Quimico q = juego.getPedido();
        String temperatura;
        if (q.necesitaFrio()) {
            temperatura = "frío";
        } else {
            temperatura = "calor";
        }
        return juego.getVisitante().getNombre() + " viene a " + juego.getTipoPedido()
                + " " + q.getNombre() + " de " + temperatura;
    }

    private static String buscarDondeGuardar(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            if (juego.getContenedor(CODIGOS[i]).puedeGuardar(juego.getPedido())) {
                return CODIGOS[i];
            }
        }
        return null;
    }

    private static String buscarLleno(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            if (juego.getContenedor(CODIGOS[i]).estaLleno()) {
                return CODIGOS[i];
            }
        }
        return null;
    }

    private static String[] buscarParaRetirar(Juego juego) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            for (int j = c.getCantidad() - 1; j >= 0; j--) {
                if (c.getQuimico(j).coincideConPedido(juego.getPedido())) {
                    return new String[] {CODIGOS[i], String.valueOf(j)};
                }
            }
        }
        return new String[] {"H1", "0"};
    }
}
