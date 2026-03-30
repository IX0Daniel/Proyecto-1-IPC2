package Servlet;

import BaseDatos.ConexionDB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/reservacion-cliente")
public class SvReservacionCliente extends HttpServlet {


    @Override
    public void init() {
        System.out.println("SERVLET CARGADO");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("NO SE ----------------------------");
        response.setContentType("application/json");

        try (Connection conn = ConexionDB.getConexion()) {

            String idReservacion = request.getParameter("id_reservacion");
            String dpiCliente = request.getParameter("dpi_cliente");

            System.out.println("id_reservacion: " + idReservacion);
            System.out.println("dpi_cliente: " + dpiCliente);


            String sql = "INSERT INTO reservacion_cliente (id_reservacion, dpi_cliente) VALUES (?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idReservacion));
            stmt.setString(2, dpiCliente);

            stmt.executeUpdate();

            response.getWriter().write("{\"mensaje\":\"Cliente asignado a reservacion\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try (Connection conn = ConexionDB.getConexion()) {

            String sql = """
            SELECT 
                r.id_reservacion,
                r.codigo,
                r.fecha_viaje,
                p.nombre AS paquete,
                d.nombre AS destino,
                r.cantidad_personas
            FROM reservacion r
            JOIN paquete p ON r.id_paquete = p.id_paquete
            JOIN destino d ON p.id_destino = d.id_destino
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"id_reservacion\":").append(rs.getInt("id_reservacion")).append(",")
                        .append("\"codigo\":\"").append(rs.getString("codigo")).append("\",")
                        .append("\"fecha_viaje\":\"").append(rs.getString("fecha_viaje")).append("\",")
                        .append("\"paquete\":\"").append(rs.getString("paquete")).append("\",")
                        .append("\"destino\":\"").append(rs.getString("destino")).append("\",")
                        .append("\"cantidad_personas\":").append(rs.getInt("cantidad_personas"))
                        .append("}");
            }

            json.append("]");
            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


}
