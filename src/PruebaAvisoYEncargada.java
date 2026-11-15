public class PruebaAvisoYEncargada {
    public static void main(String[] args) {
        System.out.println("=== Estante lleno: aviso, no derrota ===");
        Juego juego = new Juego();
        int intentos = 0;
        while (intentos < 500 && !estaLlenoAlgunoDeSuTemperatura(juego)) {
            intentos++;
            juego = new Juego();
            llenarSinPerder(juego);
        }
        String lleno = llenoDeSuTemperatura(juego);
        if (lleno == null) {
            System.out.println("No se pudo armar el caso");
            return;
        }
        System.out.println("Pedido: " + juego.getPedido().getNombre() + " de " + juego.getPedido().getTemperatura() + ", lleno: " + lleno);
        int turnoAntes = juego.getTurno();
        boolean resultado = juego.jugar(lleno, 0);
        System.out.println("jugar devolvió: " + resultado + " (tiene que dar false)");
        System.out.println("terminado: " + juego.estaTerminado() + " (tiene que dar false)");
        System.out.println("turno igual: " + (juego.getTurno() == turnoAntes) + " (tiene que dar true)");
        System.out.println("aviso: " + juego.getAviso());

        System.out.println();
        System.out.println("=== Elegir encargada: Luz ===");
        int vecesLuz = 0;
        for (int i = 0; i < 50; i++) {
            Juego j = new Juego();
            j.elegirEncargada("Luz");
            if (j.getVisitante().getNombre().equals("Luz")) {
                vecesLuz++;
            }
        }
        Juego j = new Juego();
        j.elegirEncargada("Luz");
        System.out.println("Encargada: " + j.getEncargada() + ", visitantes: " + j.getCantidadVisitantes() + " (tiene que dar 6)");
        System.out.println("Veces que vino Luz: " + vecesLuz + " (tiene que dar 0)");
    }

    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};

    private static void llenarSinPerder(Juego juego) {
        for (int t = 0; t < 30 && !juego.estaTerminado(); t++) {
            if (estaLlenoAlgunoDeSuTemperatura(juego)) {
                return;
            }
            if (juego.esRetiro()) {
                return;
            }
            boolean jugo = false;
            for (String c : CODIGOS) {
                if (!jugo && juego.getContenedor(c).puedeGuardar(juego.getPedido())) {
                    juego.jugar(c, 0);
                    jugo = true;
                }
            }
            if (!jugo) {
                return;
            }
        }
    }

    private static String llenoDeSuTemperatura(Juego juego) {
        for (String c : CODIGOS) {
            Contenedor k = juego.getContenedor(c);
            if (!juego.esRetiro() && k.estaLleno() && k.aceptaTemperatura(juego.getPedido())) {
                return c;
            }
        }
        return null;
    }

    private static boolean estaLlenoAlgunoDeSuTemperatura(Juego juego) {
        return !juego.estaTerminado() && llenoDeSuTemperatura(juego) != null;
    }
}
