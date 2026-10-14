public class PruebaDeposito {
    public class PruebaDeposito {
        public static void main(String[] args) {
            Deposito d = new Deposito();

            // Caso 1: se guarda bien
            Quimico q1 = new Acido("Ácido A");
            System.out.println("Caso 1: " + d.guardar(q1, "H1"));
            System.out.println("Motivo: " + d.getUltimoMotivo());

            // Caso 2: temperatura incorrecta
            Quimico q2 = new Inflamable("Inflamable A");
            System.out.println("Caso 2: " + d.guardar(q2, "H1"));
            System.out.println("Motivo: " + d.getUltimoMotivo());

            // Caso 3: incompatible con el último guardado
            Quimico q3 = new Base("Base A");
            System.out.println("Caso 3: " + d.guardar(q3, "H1"));
            System.out.println("Motivo: " + d.getUltimoMotivo());
        }
    }
}
