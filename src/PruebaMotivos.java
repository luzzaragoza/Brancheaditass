public class PruebaMotivos {

    public static void main(String[] args) {
        Deposito d;

        System.out.println("--- Dos iguales");
        d = new Deposito();
        d.guardar(new Oxidante(false), "E1");
        d.guardar(new Oxidante(false), "E1");
        mostrar(d);

        System.out.println("--- Explosión");
        d = new Deposito();
        d.guardar(new Acido(true), "H2");
        d.guardar(new Base(true), "H2");
        mostrar(d);

        System.out.println("--- Temperatura");
        d = new Deposito();
        d.guardar(new Inflamable(false), "H1");
        mostrar(d);

        System.out.println("--- Retiro equivocado");
        d = new Deposito();
        d.guardar(new Base(false), "E3");
        d.retirar("E3", 0, new Inflamable(false));
        mostrar(d);

        System.out.println("--- Al correrse");
        d = new Deposito();
        d.guardar(new Acido(true), "H1");
        d.guardar(new Inflamable(true), "H1");
        d.guardar(new Base(true), "H1");
        d.retirar("H1", 1, new Inflamable(true));
        mostrar(d);

        System.out.println("--- Estante lleno");
        d = new Deposito();
        d.guardar(new Acido(false), "E2");
        d.guardar(new Inflamable(false), "E2");
        d.guardar(new Base(false), "E2");
        d.guardar(new Oxidante(false), "E2");
        d.guardar(new Acido(false), "E2");
        mostrar(d);

        System.out.println("--- Retiro equivocado por temperatura");
        d = new Deposito();
        d.guardar(new Inflamable(true), "H3");
        d.retirar("H3", 0, new Inflamable(false));
        mostrar(d);

        System.out.println("--- No lo tenías");
        Juego juego = buscarPartidaNoLoTenias();
        System.out.println(juego.getTitulo());
        System.out.println(juego.getMotivo());
    }

    private static Juego buscarPartidaNoLoTenias() {
        Juego juego = new Juego("Luz");
        for (int partida = 0; partida < 1000; partida++) {
            juego = new Juego("Luz");
            while (!juego.estaTerminado()) {
                jugarCanjeandoTodo(juego);
            }
            if (juego.getTitulo().equals("¡NO LO TENÍAS!")) {
                return juego;
            }
        }
        return juego;
    }

    private static void mostrar(Deposito d) {
        System.out.println(d.getUltimoTitulo());
        System.out.println(d.getUltimoMotivo());
    }

    private static void jugarCanjeandoTodo(Juego juego) {
        String[] codigos = {"H1", "H2", "H3", "E1", "E2", "E3"};
        if (juego.esRetiro()) {
            for (String c : codigos) {
                Contenedor k = juego.getContenedor(c);
                int ultimo = k.getCantidad() - 1;
                if (ultimo >= 0 && k.getQuimico(ultimo).coincideConPedido(juego.getPedido())) {
                    juego.jugar(c, ultimo);
                    return;
                }
            }
            juego.jugar("H1", 0);
            return;
        }
        for (String c : codigos) {
            if (juego.getContenedor(c).puedeGuardar(juego.getPedido())) {
                juego.jugar(c, 0);
                juego.canjear(c);
                return;
            }
        }
        juego.jugar("E1", 0);
    }
}
