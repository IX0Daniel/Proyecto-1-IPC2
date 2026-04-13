package Modelos;

public class Destino {

    private int id_destino;
    private String nombre;
    private String pais;
    private String descripcion;

    public Destino(int id_destino, String nombre, String pais, String descripcion) {
        this.id_destino = id_destino;
        this.nombre = nombre;
        this.pais = pais;
        this.descripcion = descripcion;
    }

    public int getId_destino() {

        return id_destino;
    }

    public void setId_destino(int id_destino) {

        this.id_destino = id_destino;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getPais() {

        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
