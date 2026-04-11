package BaseDatos;

import Modelos.Destino;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinoDB {



    public List<Destino> getAll() throws SQLException {
        String sql = "SELECT id_destino, nombre, pais, descripcion FROM destino";
        List<Destino> lista = new ArrayList<>();

        try (
                Connection con = ConexionDB.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }


    public Destino getByID(int id_destino) throws SQLException{
        String sql = "Select * FROM destino WHERE id_destino = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_destino);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapRow(rs);
            }
        }
        return null;
    }



    public Destino create(String nombre, String pais, String descripcion) throws SQLException {

        System.out.println("descripcion: " + descripcion);

        String sql = "INSERT INTO destino (nombre, pais, descripcion) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){



            ps.setString(1, nombre);
            ps.setString(2, pais);
            ps.setString(3, descripcion);

            ps.executeUpdate();

            int idGenerado = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }



            return new Destino(idGenerado, nombre, pais, descripcion);
        }
    }


    public Destino update(int id_destino, String nombre, String pais, String descripcion) throws SQLException {
        System.out.println("Se va a actualizar un destino: " + id_destino + " - "+ nombre+ " - " + pais + " - " + descripcion);
        String sql = "UPDATE destino SET nombre=?, pais=?, descripcion=? WHERE id_destino=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, pais);

            ps.setString(3, descripcion);
            ps.setInt(4, id_destino);

            ps.executeUpdate();

            return new Destino(id_destino, nombre, pais, descripcion);
        }
    }

    public void delete(String id_destino) throws SQLException {
        String sql = "DELETE FROM destino WHERE id_destino=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id_destino);
            ps.executeUpdate();
        }
    }


    private Destino mapRow(ResultSet rs) throws SQLException {
        return new Destino(
                rs.getInt("id_destino"),
                rs.getString("nombre"),
                rs.getString("pais"),
                rs.getString("descripcion")
        );
    }



}
