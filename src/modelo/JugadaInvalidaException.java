package modelo;

public class JugadaInvalidaException extends Exception {
    private String titulo;
    private String motivo;

    public JugadaInvalidaException(String titulo, String motivo) {
        super(motivo);
        this.titulo = titulo;
        this.motivo = motivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMotivo() {
        return motivo;
    }
}
