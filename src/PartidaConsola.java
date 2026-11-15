import java.util.Scanner;

public class PartidaConsola {
    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ranking ranking = new Ranking();
        mostrarBienvenida(sc);

        boolean otraVez = true;
        while (otraVez) {
            Juego juego = new Juego();
            jugarPartida(sc, juego);

            if (!juego.estaTerminado()) {
                break;
            }
            mostrarFinal(sc, juego, ranking);
            otraVez = preguntarOtraVez(sc);
        }
        sc.close();
    }

    private static void jugarPartida(Scanner sc, Juego juego) {
        while (!juego.estaTerminado()) {
            mostrarDeposito(juego);
            mostrarPedido(juego);

            System.out.print("Contenedor (H1, H2, H3, E1, E2, E3), CANJE o FIN: ");
            String respuesta = sc.nextLine().trim().toUpperCase();

            if (respuesta.equals("")) {
                System.out.println("No escribiste nada. Escribí un código, CANJE o FIN.");
                continue;
            }
            if (respuesta.equals("FIN")) {
                return;
            }

            if (respuesta.equals("CANJE")) {
                canjear(sc, juego);
            } else if (!esCodigoValido(respuesta)) {
                System.out.println("Ese contenedor no existe. Usá H1, H2, H3, E1, E2 o E3.");
            } else if (juego.esRetiro()) {
                int casilla = leerCasilla(sc);
                if (casilla > juego.getContenedor(respuesta).getCantidad()) {
                    System.out.println("Esa casilla está vacía. Elegí una que tenga un químico.");
                    continue;
                }
                juego.jugar(respuesta, casilla - 1);
            } else {
                juego.jugar(respuesta, 0);
            }
        }
    }

    private static void mostrarFinal(Scanner sc, Juego juego, Ranking ranking) {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("            FIN DE LA PARTIDA");
        System.out.println("=====================================");
        System.out.println(juego.getTitulo());
        System.out.println(juego.getMotivo());
        System.out.println("Turno: " + juego.getTurno() + "  Puntaje: " + juego.getPuntaje());

        if (juego.getPuntaje() > 0 && ranking.entra(juego.getPuntaje())) {
            System.out.println();
            System.out.println("¡Tu puntaje entra al TOP 5!");
            String iniciales = leerIniciales(sc);
            ranking.agregar(iniciales, juego.getPuntaje());
        }
        mostrarRanking(ranking);
    }

    private static String leerIniciales(Scanner sc) {
        while (true) {
            System.out.print("Escribí tus 3 letras: ");
            String texto = sc.nextLine().trim().toUpperCase();
            if (texto.length() == 3 && soloLetras(texto)) {
                return texto;
            }
            System.out.println("Tienen que ser exactamente 3 letras.");
        }
    }

    private static boolean soloLetras(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isLetter(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void mostrarRanking(Ranking ranking) {
        System.out.println();
        System.out.println("----- TOP 5 -----");
        if (ranking.getCantidad() == 0) {
            System.out.println("Todavía no hay puntajes.");
        }
        for (int i = 0; i < ranking.getCantidad(); i++) {
            System.out.println((i + 1) + ". " + ranking.getIniciales(i) + "  " + ranking.getPuntaje(i));
        }
        System.out.println();
    }

    private static boolean preguntarOtraVez(Scanner sc) {
        while (true) {
            System.out.print("¿Jugar de nuevo? (S/N): ");
            String texto = sc.nextLine().trim().toUpperCase();
            if (texto.equals("S")) {
                return true;
            }
            if (texto.equals("N")) {
                System.out.println("¡Gracias por jugar!");
                return false;
            }
            System.out.println("Escribí S o N.");
        }
    }

    private static void canjear(Scanner sc, Juego juego) {
        String llenos = contenedoresLlenos(juego);
        if (llenos.equals("")) {
            System.out.println("Todavía no hay ningún contenedor lleno. Solo se puede canjear uno lleno (4 químicos).");
            return;
        }

        System.out.println("Contenedores llenos: " + llenos);
        System.out.print("¿Qué contenedor canjeás? ");
        String codigo = sc.nextLine().trim().toUpperCase();

        if (!esCodigoValido(codigo)) {
            System.out.println("Ese contenedor no existe. Usá H1, H2, H3, E1, E2 o E3.");
        } else if (juego.canjear(codigo)) {
            System.out.println("Canje hecho. Puntaje: " + juego.getPuntaje());
        } else {
            System.out.println("No se pudo canjear: el contenedor tiene que estar lleno.");
        }
    }

    private static String contenedoresLlenos(Juego juego) {
        String lista = "";
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            if (c.estaLleno()) {
                if (!lista.equals("")) {
                    lista = lista + ", ";
                }
                lista = lista + CODIGOS[i];
            }
        }
        return lista;
    }

    private static void mostrarBienvenida(Scanner sc) {
        System.out.println("=====================================");
        System.out.println("   BIENVENIDO AL DEPÓSITO DE QUÍMICOS");
        System.out.println("=====================================");
        System.out.println();
        System.out.println("CÓMO JUGAR");
        System.out.println("- Vienen visitantes a dejar y a retirar químicos.");
        System.out.println("- Tu trabajo es guardarlos bien. Si te equivocás, perdés.");
        System.out.println("- Hay 3 heladeras (H1, H2, H3) y 3 estantes (E1, E2, E3).");
        System.out.println("- Los químicos de frío van en heladeras y los de calor en estantes.");
        System.out.println("- Cada contenedor es una fila de 4 lugares. El químico nuevo va al final.");
        System.out.println("- Si sacás uno del medio, los de atrás se corren hacia adelante");
        System.out.println("  y pueden quedar juntos dos que no se llevan bien.");
        System.out.println("- Cada jugada bien hecha suma puntos. Los mejores 5 puntajes quedan en el TOP 5.");
        System.out.println("- Si un contenedor se llena (4 químicos), escribí CANJE y se vacía.");
        System.out.println("- Escribí FIN para salir.");
        System.out.println();
        System.out.println("REGLAS: NO pueden quedar uno al lado del otro");
        System.out.println("- Dos químicos iguales.");
        System.out.println("- Ácido con Base.");
        System.out.println("- Inflamable con Oxidante.");
        System.out.println("Todo lo demás sí: Ácido o Base con Inflamable u Oxidante.");
        System.out.println("Solo importa el último de la fila: el que entra se compara con él.");
        System.out.println();
        System.out.print("Apretá Enter para empezar...  ");
        sc.nextLine();
    }

    private static boolean esCodigoValido(String codigo) {
        for (int i = 0; i < CODIGOS.length; i++) {
            if (CODIGOS[i].equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    private static int leerCasilla(Scanner sc) {
        while (true) {
            System.out.print("¿Qué casilla sacás? (1 a 4): ");
            String texto = sc.nextLine().trim();
            if (texto.length() == 1 && texto.charAt(0) >= '1' && texto.charAt(0) <= '4') {
                return texto.charAt(0) - '0';
            }
            System.out.println("Escribí un número del 1 al 4.");
        }
    }

    private static void mostrarDeposito(Juego juego) {
        System.out.println();
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = juego.getContenedor(CODIGOS[i]);
            String linea = CODIGOS[i] + ": ";
            for (int j = 0; j < c.getCantidad(); j++) {
                linea = linea + "[" + (j + 1) + ":" + texto(c.getQuimico(j)) + "] ";
            }
            System.out.println(linea);
        }
    }

    private static void mostrarPedido(Juego juego) {
        System.out.println();
        System.out.println("Turno " + juego.getTurno() + " | Puntaje " + juego.getPuntaje());
        System.out.println(juego.getVisitante().getNombre() + " viene a "
                + juego.getTipoPedido() + ": " + texto(juego.getPedido()));
    }

    private static String texto(Quimico q) {
        if (q.necesitaFrio()) {
            return q.getNombre() + "-frio";
        }
        return q.getNombre() + "-calor";
    }
}