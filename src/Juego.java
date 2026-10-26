public class Juego {
    public static final int PUNTOS_CANJE = 40;

    public static final boolean CANJE_GASTA_TURNO = false;

    public static final int MIN_PENDIENTES_PARA_RETIRO = 3;

    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};
    private static final int MAX_PENDIENTES = 100;

    private Deposito deposito;
    private int turno;
    private int puntaje;
    private boolean terminado;
    private String motivo;

    private Visitante[] visitantes;

    private TipoPedido tipoPedido;
    private Visitante visitante;
    private Quimico pedido;
    private int pendienteActual;

    private Visitante[] pendientesVisitante;
    private Quimico[] pendientesQuimico;
    private int cantidadPendientes;

    public Juego() {
        deposito = new Deposito();
        turno = 1;
        puntaje = 0;
        terminado = false;
        motivo = null;

        visitantes = new Visitante[2];
        visitantes[0] = new Visitante("Dra. Mora", "mora_x4.png");
        visitantes[1] = new Visitante("Dr. Bigotes", "bigotes_x4.png");

        pendientesVisitante = new Visitante[MAX_PENDIENTES];
        pendientesQuimico = new Quimico[MAX_PENDIENTES];
        cantidadPendientes = 0;

        generarPedido();
    }

    public boolean jugar(String codigo, int posicion) {
        if (terminado) {
            return false;
        }

        boolean salioBien;
        if (tipoPedido == TipoPedido.DEJAR) {
            salioBien = deposito.guardar(pedido, codigo);
        } else {
            salioBien = deposito.retirar(codigo, posicion, pedido);
        }

        if (!salioBien) {
            terminar(deposito.getUltimoMotivo());
            return false;
        }

        if (tipoPedido == TipoPedido.DEJAR) {
            agregarPendiente(visitante, pedido);
        } else {
            quitarPendiente(pendienteActual);
        }
        siguienteTurno();
        return true;
    }

    public boolean canjear(String codigo) {
        if (terminado) {
            return false;
        }
        if (!deposito.canjear(codigo)) {
            return false;
        }
        puntaje = puntaje + PUNTOS_CANJE;
        if (CANJE_GASTA_TURNO) {
            siguienteTurno();
        } else {
            revisarQueLoTenes();
        }
        return true;
    }

    private void siguienteTurno() {
        turno++;
        generarPedido();
    }

    private void generarPedido() {
        int moneda = (int) (Math.random() * 2);
        if (cantidadPendientes < MIN_PENDIENTES_PARA_RETIRO || moneda == 0) {
            generarPedidoDeDejar();
        } else {
            generarPedidoDeRetiro();
        }
    }

    private void generarPedidoDeDejar() {
        tipoPedido = TipoPedido.DEJAR;
        pendienteActual = -1;

        int cualVisitante = (int) (Math.random() * visitantes.length);
        int tipo = (int) (Math.random() * 4);
        boolean necesitaFrio = (int) (Math.random() * 2) == 0;

        visitante = visitantes[cualVisitante];
        pedido = crearQuimico(tipo, necesitaFrio);
    }

    private void generarPedidoDeRetiro() {
        tipoPedido = TipoPedido.RETIRAR;
        pendienteActual = (int) (Math.random() * cantidadPendientes);

        visitante = pendientesVisitante[pendienteActual];
        pedido = pendientesQuimico[pendienteActual];

        revisarQueLoTenes();
    }

    private void revisarQueLoTenes() {
        if (tipoPedido == TipoPedido.RETIRAR && !hayEnDeposito(pedido)) {
            terminar("¡NO LO TENÍAS! " + visitante.getNombre() + " volvió por su "
                    + pedido.getNombre() + ", pero ya no está en el depósito.");
        }
    }

    private Quimico crearQuimico(int tipo, boolean necesitaFrio) {
        if (tipo == 0) {
            return new Acido(necesitaFrio);
        } else if (tipo == 1) {
            return new Base(necesitaFrio);
        } else if (tipo == 2) {
            return new Inflamable(necesitaFrio);
        } else {
            return new Oxidante(necesitaFrio);
        }
    }

    private boolean hayEnDeposito(Quimico buscado) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = deposito.buscar(CODIGOS[i]);
            for (int j = 0; j < c.getCantidad(); j++) {
                if (c.getQuimico(j).coincideConPedido(buscado)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void agregarPendiente(Visitante quien, Quimico que) {
        if (cantidadPendientes < MAX_PENDIENTES) {
            pendientesVisitante[cantidadPendientes] = quien;
            pendientesQuimico[cantidadPendientes] = que;
            cantidadPendientes++;
        }
    }

    private void quitarPendiente(int posicion) {
        for (int i = posicion; i < cantidadPendientes - 1; i++) {
            pendientesVisitante[i] = pendientesVisitante[i + 1];
            pendientesQuimico[i] = pendientesQuimico[i + 1];
        }
        pendientesVisitante[cantidadPendientes - 1] = null;
        pendientesQuimico[cantidadPendientes - 1] = null;
        cantidadPendientes--;
    }

    private void terminar(String motivoDerrota) {
        terminado = true;
        motivo = motivoDerrota;
    }

    public boolean estaTerminado() {
        return terminado;
    }

    public boolean esRetiro() {
        return tipoPedido == TipoPedido.RETIRAR;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public Quimico getPedido() {
        return pedido;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getTurno() {
        return turno;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getCantidadPendientes() {
        return cantidadPendientes;
    }

    public Contenedor getContenedor(String codigo) {
        return deposito.buscar(codigo);
    }
}
