package BaseDatos;

import Modelos.Destino;
import Modelos.Usuario;

import java.sql.*;

public class UsuarioDB {


    public Usuario login(String username, String password) throws SQLException {

        String sql = "SELECT u.id_usuario, u.username, r.nombre AS rol " +
                "FROM usuario u " +
                "JOIN rol r ON u.id_rol = r.id_rol " +
                "WHERE u.username = ? AND u.password = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("username"),
                            rs.getString("rol")
                    );
                }
            }
        }

        return null;
    }


    public void create(String username, String password, int id_rol) throws SQLException {


        String sql = "INSERT INTO usuario (username, password, id_rol) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, id_rol);
            ps.executeUpdate();


        }
    }
}
