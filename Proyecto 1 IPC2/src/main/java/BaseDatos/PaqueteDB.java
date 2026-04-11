package BaseDatos;

import Modelos.Cliente;
import Modelos.Paquete;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDB {


    public List<Paquete> getAll() throws SQLException {
        //String sql = "SELECT dpi, nombre, fecha_nacimiento, telefono, email, nacionalidad FROM cliente";


        String sql = "SELECT id_paquete, nombre, id_destino, duracion, precio, capacidad, estado FROM paquete";
        List<Paquete> lista = new ArrayList<>();

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


    public void delete(int id_paquete) throws SQLException {
        String sql = "DELETE FROM paquete WHERE id_destino=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_paquete);
            ps.executeUpdate();
        }
    }


    public Paquete update(int id_paquete, String nombre, int id_destino, int duracion, int precio, int capacidad, boolean estado) throws SQLException {
        String sql = "UPDATE paquete SET nombre=?, id_destino=?, duracion=?, precio=?, capacidad=?, estado=? WHERE id_paquete=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id_destino);
            ps.setInt(3, duracion);
            ps.setInt(4, precio);
            ps.setInt(5, capacidad);
            ps.setBoolean(6, estado);

            ps.setInt(7, id_paquete);

            ps.executeUpdate();

            return new Paquete(id_paquete, nombre, id_destino, duracion, precio, capacidad, estado);
        }
    }

    public Paquete getByID(int id_paquete) throws SQLException{
        String sql = "Select * FROM paquete WHERE id_paquete = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_paquete);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapRow(rs);
            }
        }
        return null;
    }



    public Paquete create(String nombre, int id_destino, int duracion, int precio, int capacidad, boolean estado) throws SQLException {
        String sql = "INSERT INTO paquete (nombre, id_destino, duracion, precio, capacidad, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
             //PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id_destino);

            ps.setInt(3, duracion);

            ps.setDouble(4, precio);
            ps.setInt(5, capacidad);
            ps.setBoolean(6, estado);

            ps.executeUpdate();

            int idGenerado = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
            return new Paquete(idGenerado, nombre, id_destino, duracion, precio, capacidad, estado);
        }
    }


    private Paquete mapRow(ResultSet rs) throws SQLException {
        return new Paquete(
                rs.getInt("id_paquete"),
                rs.getString("nombre"),
                rs.getInt("id_destino"),
                rs.getInt("duracion"),
                rs.getInt("precio"),
                rs.getInt("capacidad"),
                rs.getBoolean("estado")


        );
    }

}


