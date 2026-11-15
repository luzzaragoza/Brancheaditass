package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    public static final int CUPOS = 5;
    public static final String ARCHIVO = "puntajes.txt";

    private ArrayList<Puntaje> puntajes = new ArrayList<>();

    public Ranking() {
        cargar();
    }

    public boolean entra(int puntos) {
        if (puntajes.size() < CUPOS) {
            return true;
        }
        return puntos > puntajes.get(puntajes.size() - 1).getPuntos();
    }

    public void agregar(String iniciales, int puntos, int turno) {
        if (!entra(puntos)) {
            return;
        }
        puntajes.add(new Puntaje(iniciales, puntos, turno));
        Collections.sort(puntajes);
        if (puntajes.size() > CUPOS) {
            puntajes.remove(puntajes.size() - 1);
        }
        guardar();
    }

    private void cargar() {
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(ARCHIVO));
            String linea = lector.readLine();
            while (linea != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    try {
                        String ini = partes[0].trim();
                        int pts = Integer.parseInt(partes[1].trim());
                        int trn = Integer.parseInt(partes[2].trim());
                        puntajes.add(new Puntaje(ini, pts, trn));
                    } catch (NumberFormatException e) {
                        // línea mal escrita: se ignora
                    }
                }
                linea = lector.readLine();
            }
        } catch (IOException e) {
            // si el archivo todavía no existe, se empieza con el top 5 vacío
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.out.println("No se pudo cerrar " + ARCHIVO);
                }
            }
        }
        Collections.sort(puntajes);
        while (puntajes.size() > CUPOS) {
            puntajes.remove(puntajes.size() - 1);
        }
    }

    private void guardar() {
        PrintWriter escritor = null;
        try {
            escritor = new PrintWriter(new FileWriter(ARCHIVO));
            for (Puntaje p : puntajes) {
                escritor.println(p.getIniciales() + ";" + p.getPuntos() + ";" + p.getTurno());
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar el top 5: " + e.getMessage());
        } finally {
            if (escritor != null) {
                escritor.close();
            }
        }
    }

    public int getCantidad() {
        return puntajes.size();
    }

    public String getIniciales(int posicion) {
        return puntajes.get(posicion).getIniciales();
    }

    public int getPuntaje(int posicion) {
        return puntajes.get(posicion).getPuntos();
    }

    public int getTurno(int posicion) {
        return puntajes.get(posicion).getTurno();
    }
}