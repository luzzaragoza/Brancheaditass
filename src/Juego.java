public class Juego {
    public static final int PUNTOS_CANJE = 40;

    public static final boolean CANJE_GASTA_TURNO = false;

    public static final int MIN_PENDIENTES_PARA_RETIRO = 3;

    public static final int VUELVE_POR_CANJEADO = 5; // 1 de cada 5 retiros vuelve por algo canjeado

    private static final String[] CODIGOS = {"H1", "H2", "H3", "E1", "E2", "E3"};
    private static final int MAX_PENDIENTES = 100;

    private Deposito deposito;
    private int turno;
    private int puntaje;
    private boolean terminado;
    private String titulo;
    private String motivo;
    private String encargada;

    private Visitante[] visitantes;

    private TipoPedido tipoPedido;
    private Visitante visitante;
    private Quimico pedido;
    private int pendienteActual;

    private Visitante[] pendientesVisitante;
    private Quimico[] pendientesQuimico;
    private int cantidadPendientes;

    public Juego() {
        this("Sofi 1");
    }

    public Juego(String encargada) {
        this.encargada = encargada;
        deposito = new Deposito();
        turno = 1;
        puntaje = 0;
        terminado = false;
        titulo = null;
        motivo = null;

        Visitante[] todos = {
            new Visitante("Lyss", "lyss_x4.png"),
            new Visitante("Luz", "luz_x4.png"),
            new Visitante("Sofi 1", "sofi1_x4.png"),
            new Visitante("Sofi 2", "sofi2_x4.png"),
            new Visitante("Dra. Mora", "mora_x4.png"),
            new Visitante("Dr. Bigotes", "bigotes_x4.png"),
            new Visitante("Ing. Paco", "paco_x4.png")
        };
        armarVisitantes(todos);

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
            terminar(deposito.getUltimoTitulo(), deposito.getUltimoMotivo());
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
        visitante = visitantes[cualVisitante];

        Quimico[] posibles = new Quimico[8];
        int cantidadPosibles = 0;
        boolean[] temperaturas = {true, false};
        for (int tipo = 0; tipo < 4; tipo ++) {
            for (boolean necesitaFrio : temperaturas) {
                Quimico q = crearQuimico(tipo, necesitaFrio);
                if (tieneLugar(q)) {
                    posibles[cantidadPosibles] = q;
                    cantidadPosibles++;
                }
            }
        }
        if (cantidadPosibles > 0) {
            pedido = posibles[(int) (Math.random() * cantidadPosibles)];
        } else {
            int tipo = (int) (Math.random() * 4);
            boolean necesitaFrio = (int) (Math.random() * 2) == 0;
            pedido = crearQuimico(tipo, necesitaFrio);
        }
    }
    private void generarPedidoDeRetiro() {
        int[] entregables = new int[cantidadPendientes];
        int cantidadEntregables = 0;
        int [] canjeados = new int [cantidadPendientes];
        int cantidadCanjeados = 0;

        for (int i = 0; i < cantidadPendientes; i++) {
            if (sePuedeEntregar(pendientesQuimico[i])) {
                entregables[cantidadEntregables] = i;
                cantidadEntregables++;
            } else if (!hayEnDeposito(pendientesQuimico[i])) {
                canjeados[cantidadCanjeados] = i;
                cantidadCanjeados++;
            }
        }

        boolean vuelvePorCanjeado = (int) (Math.random() * VUELVE_POR_CANJEADO) == 0;
        if (vuelvePorCanjeado && cantidadCanjeados > 0) {
            elegirRetiro(canjeados[(int) (Math.random() * cantidadCanjeados)]);
        } else {
            generarPedidoDeDejar();
        }
    }

    private void elegirRetiro(int posicion) {
        tipoPedido = TipoPedido.RETIRAR;
        pendienteActual = posicion;

        visitante = pendientesVisitante[posicion];
        pedido = pendientesQuimico[posicion];

        revisarQueLoTenes();
    }

    private void revisarQueLoTenes() {
        if (tipoPedido == TipoPedido.RETIRAR && !hayEnDeposito(pedido)) {
            terminar("¡NO LO TENÍAS!", visitante.getNombre() + " volvió por su "
                    + pedido.getNombre() + " de " + pedido.getTemperatura()
                    + ", pero ya no queda en el depósito: lo canjeaste.");
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

    private boolean tieneLugar(Quimico q) {
        for (int i = 0; i< CODIGOS.length; i++) {
            if (deposito.buscar(CODIGOS[i]).puedeGuardar(q)) {
                return true;
            }
        }
        return false;
    }

    private boolean sePuedeEntregar(Quimico buscado) {
        for (int i = 0; i < CODIGOS.length; i++) {
            Contenedor c = deposito.buscar(CODIGOS[i]);
            for (int j = 0; j < c.getCantidad(); j++) {
                if (c.getQuimico(j).coincideConPedido(buscado) && c.sePuedeSacar(j)) {
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

    private void armarVisitantes(Visitante[] todos) {
        int cantidad = 0;
        for (Visitante v : todos) {
            if (!v.getNombre().equals(encargada)) {
                cantidad++;
            }
        }
        visitantes = new Visitante[cantidad];
        int i = 0;
        for (Visitante v : todos) {
            if (!v.getNombre().equals(encargada)) {
                visitantes[i] = v;
                i++;
            }
        }
    }

    private void terminar(String tituloDerrota, String motivoDerrota) {
        terminado = true;
        titulo = tituloDerrota;
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

    public String getTitulo() {
        return titulo;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getEncargada() {
        return encargada;
    }

    public int getCantidadVisitantes() {
        return visitantes.length;
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
