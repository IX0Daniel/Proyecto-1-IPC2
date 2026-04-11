package BaseDatos;

import java.sql.*;

public class ReservacionDB {

    public int crearReservacion(String fechaViaje, int idPaquete, int idUsuario, int cantidad, double costo) throws SQLException {

        String sql = "INSERT INTO reservacion (codigo, fecha_creacion, fecha_viaje, id_paquete, id_usuario, cantidad_personas, costo_total, estado) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String codigo = ""+System.currentTimeMillis();

            if(codigo.length() >= 10){

                System.out.println("Códgio creado: " + codigo);

                codigo = codigo.substring(6, 13);
                codigo = "RES"+codigo;

            }

            ps.setString(1, codigo);
            ps.setDate(2, Date.valueOf(fechaViaje));
            ps.setInt(3, idPaquete);
            ps.setInt(4, idUsuario);
            ps.setInt(5, cantidad);
            ps.setDouble(6, costo);
            ps.setString(7, "pendiente");

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }

        throw new SQLException("No se creó reservación");
    }

    public void insertarCliente(int idReservacion, String dpi) throws SQLException {

        String sql = "INSERT INTO reservacion_cliente (id_reservacion, dpi_cliente) VALUES (?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idReservacion);
            ps.setString(2, dpi);
            ps.executeUpdate();
        }
    }
}
