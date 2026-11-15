package modelo;

public class Visitante implements Dibujable {
    private final String nombre;
    private final String imagen;

    public Visitante(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String getImagen() {
        return imagen;
    }
}