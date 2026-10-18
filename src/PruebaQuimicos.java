public class PruebaQuimicos {

    public static void main(String[] args) {
        Quimico acido = new Acido(true);
        Quimico base = new Base(true);

        System.out.println("ácido con base: " + acido.esCompatibleCon(base) + " (tiene que dar false)");
        System.out.println("base con ácido: " + base.esCompatibleCon(acido) + " (tiene que dar false)");
        System.out.println("ácido con ácido: " + acido.esCompatibleCon(new Acido(false)) + " (tiene que dar false)");
        System.out.println("base con base: " + base.esCompatibleCon(new Base(false)) + " (tiene que dar false)");

        System.out.println("ácido necesita frío: " + acido.necesitaFrio() + " (tiene que dar true)");
        System.out.println("nombre: " + acido.getNombre() + " (tiene que dar Acido)");

        System.out.println("ácido de frío coincide con pedido de ácido de frío: "
                + acido.coincideConPedido(new Acido(true)) + " (tiene que dar true)");
        System.out.println("ácido de frío coincide con pedido de ácido de calor: "
                + acido.coincideConPedido(new Acido(false)) + " (tiene que dar false)");
        System.out.println("ácido de frío coincide con pedido de base de frío: "
                + acido.coincideConPedido(base) + " (tiene que dar false)");
    }
}
