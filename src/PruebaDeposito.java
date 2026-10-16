public class PruebaDeposito {
    public static void main(String[] args) {
        Deposito d = new Deposito();

        //Caso 1: incompatibles
        System.out.println("Caso 1.a (base en H1, esperando true): " + d.guardar(new Base(true), "H1"));
        System.out.println("Caso 1.b (ácido en H1, esperando false): " + d.guardar(new Acido(true), "H1"));
        System.out.println("Motivo: " + d.getUltimoMotivo());

        //Caso 2: temperatura incorrecta
        System.out.println("Caso 2 (ácido de frío en E1, esperando false): " + d.guardar(new Acido(true), "E1"));
        System.out.println("Motivo: " + d.getUltimoMotivo());

        //Caso 3: canjear un estante lleno
        d.guardar(new Acido(false), "E2");
        d.guardar(new Inflamable(false), "E2");
        d.guardar(new Base(false), "E2");
        d.guardar(new Oxidante(false), "E2");
        System.out.println("Caso 3.a (canjear E2 lleno, esperando true): " + d.canjear("E2"));
        System.out.println("Caso 3.b (guardar en E2 vacío, esperando true): " + d.guardar(new Base(false), "E2"));
        System.out.println("Caso 3.c (canjear E3 vacío, esperando false): " + d.canjear("E3"));
    }
}