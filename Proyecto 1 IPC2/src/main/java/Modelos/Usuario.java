package Modelos;

public class Usuario {

    private int id_usuario;
    private String username;
    private String rol;

    public Usuario(int id_usuario, String username, String rol) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.rol = rol;
    }

    public int getId_usuario() {
        return id_usuario;
    }
    public String getUsername() {
        return username;
    }
    public String getRol() {
        return rol;
    }
}
