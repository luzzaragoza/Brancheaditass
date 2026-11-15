package pruebas;

import modelo.*;

public class PruebaDeposito {
    public static void main(String[] args) {
        Deposito d = new Deposito();

        System.out.println("Caso 1: base en H1 y después ácido en H1 (esperando ¡EXPLOTÓ!)");
        try {
            d.guardar(new Base(true), "H1");
            d.guardar(new Acido(true), "H1");
            System.out.println("ERROR: no lanzó la excepción");
        } catch (JugadaInvalidaException e) {
            System.out.println(e.getTitulo() + " " + e.getMotivo());
        }

        System.out.println("Caso 2: ácido de frío en E1 (esperando ¡SE ARRUINÓ!)");
        try {
            d.guardar(new Acido(true), "E1");
            System.out.println("ERROR: no lanzó la excepción");
        } catch (JugadaInvalidaException e) {
            System.out.println(e.getTitulo() + " " + e.getMotivo());
        }

        System.out.println("Caso 3: llenar E2, canjearlo y volver a guardar (esperando OK)");
        try {
            d.guardar(new Acido(false), "E2");
            d.guardar(new Inflamable(false), "E2");
            d.guardar(new Base(false), "E2");
            d.guardar(new Oxidante(false), "E2");
            d.canjear("E2");
            d.guardar(new Base(false), "E2");
            System.out.println("OK");
        } catch (JugadaInvalidaException e) {
            System.out.println("ERROR: " + e.getMotivo());
        }

        System.out.println("Caso 4: canjear E3 vacío (esperando que no se pueda)");
        try {
            d.canjear("E3");
            System.out.println("ERROR: no lanzó la excepción");
        } catch (JugadaInvalidaException e) {
            System.out.println(e.getMotivo());
        }
    }
}
