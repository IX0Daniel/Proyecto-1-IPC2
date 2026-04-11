package Modelos;

public class Paquete {


    private int id_paquete;
    private String nombre;
    private int id_destino;
    private int duracion;
    private int precio;
    private int capacidad;
    private boolean estado;

    public Paquete() {}

    public Paquete(int id_paquete, String nombre, int id_destino, int duracion, int precio, int capacidad, boolean estado) {
        this.id_paquete = id_paquete;
        this.nombre = nombre;
        this.id_destino = id_destino;
        this.duracion = duracion;
        this.precio = precio;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_destino() {
        return id_destino;
    }

    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
