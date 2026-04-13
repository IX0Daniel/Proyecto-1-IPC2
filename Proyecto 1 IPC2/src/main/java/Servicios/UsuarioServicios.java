package Servicios;

import BaseDatos.UsuarioDB;
import Modelos.Destino;
import Modelos.Usuario;

import java.sql.SQLException;

public class UsuarioServicios {

    private final UsuarioDB usuarioDB = new UsuarioDB();

    public Usuario login(String username, String password) {

        if (username == null || password == null ||
                username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        try {
            return usuarioDB.login(username, password);
        } catch (Exception e) {
            throw new RuntimeException("Error en login", e);
        }
    }

    public void crear(String username, String password, int id_rol) {
        try {
            usuarioDB.create(username, password, id_rol);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear destino", e);
        }
    }

}
