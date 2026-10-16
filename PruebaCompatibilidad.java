public class PruebaCompatibilidad {
    public static void main(String[] args) {
        Quimico[] qs = {
                new Acido("Acido", true),
                new Base("Base", true),
                new Inflamable("Inflamable", true),
                new Oxidante("Oxidante", true)
        };

        // Filas y columnas en el mismo orden: Acido, Base, Inflamable, Oxidante
        boolean[][] esperado = {
                {false, false, true, true},
                {false, false, true, true},
                {true, true, false, false},
                {true, true, false, false}
        };

        for (int i = 0; i < qs.length; i++) {
            for (int j = 0; j < qs.length; j++) {
                boolean real = qs[i].esCompatibleCon(qs[j]);
                String res = (real == esperado[i][j]) ? "OK" : "ERROR";
                System.out.println(qs[i].getNombre() + " con " + qs[j].getNombre() + ": " + real + " " + res);
            }
        }
    }
}