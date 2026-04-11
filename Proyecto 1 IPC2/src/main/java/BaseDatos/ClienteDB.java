package BaseDatos;

import Modelos.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDB {

    public List<Cliente> getAll() throws SQLException {
        String sql = "SELECT dpi, nombre, fecha_nacimiento, telefono, email, nacionalidad FROM cliente";
        List<Cliente> lista = new ArrayList<>();

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


    public Cliente getByDpi(String dpi) throws SQLException{
        String sql = "Select * FROM cliente WHERE dpi = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dpi);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapRow(rs);
            }
        }
        return null;
    }



    public Cliente create(String dpi, String nombre, String fechaNacimiento, String telefono, String email, String nacionalidad) throws SQLException {
        String sql = "INSERT INTO cliente (dpi, nombre, fecha_nacimiento, telefono, email, nacionalidad) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dpi);
            ps.setString(2, nombre);

            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()){
                ps.setDate(3, Date.valueOf(LocalDate.parse(fechaNacimiento)));
                System.out.println("Fecha ingresada");
            }else{
                ps.setNull(3, Types.DATE);
            }


            ps.setString(4, telefono);
            ps.setString(5, email);
            ps.setString(6, nacionalidad);

            ps.executeUpdate();

            return new Cliente(dpi, nombre, fechaNacimiento, telefono, email, nacionalidad);
        }
    }


    public Cliente update(String dpi, String telefono, String email) throws SQLException {
        String sql = "UPDATE cliente SET telefono=?, email=? WHERE dpi=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, telefono);
            ps.setString(2, email);

            ps.setString(3, dpi);
            ps.executeUpdate();

            return new Cliente(dpi, null, null, telefono, null, null);
        }
    }

    public void delete(String dpi) throws SQLException {
        String sql = "DELETE FROM cliente WHERE dpi=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dpi);
            ps.executeUpdate();
        }
    }


    private Cliente mapRow(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getString("dpi"),
                rs.getString("nombre"),
                rs.getString("fecha_nacimiento"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("nacionalidad")


        );
    }

}