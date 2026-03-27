package Servlet;

import BaseDatos.ConexionDB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
        import java.util.ArrayList;


@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection conn = ConexionDB.getConexion()) {

            String sql = "SELECT dpi, nombre, telefono, email FROM cliente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"dpi\":\"").append(rs.getString("dpi")).append("\",")
                        .append("\"nombre\":\"").append(rs.getString("nombre")).append("\",")
                        .append("\"telefono\":\"").append(rs.getString("telefono")).append("\",")
                        .append("\"correo\":\"").append(rs.getString("email")).append("\"")


                        .append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }



    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST ejecutado");
        resp.setContentType("text/plain");

        try (Connection conn = ConexionDB.getConexion()) {

            String dpi = req.getParameter("dpi");
            String nombre = req.getParameter("nombre");
            String telefono = req.getParameter("telefono");
            String email = req.getParameter("email");
            String nacionalidad = req.getParameter("nacionalidad");

            String sql = "INSERT INTO cliente (dpi, nombre, telefono, email, nacionalidad) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dpi);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, nacionalidad);

            stmt.executeUpdate();

            resp.getWriter().write("Cliente insertado");

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: ----------------NO SE PUDO" + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");

        String dpi = req.getParameter("dpi");

        try (Connection conn = ConexionDB.getConexion()) {

            String sql = "DELETE FROM cliente WHERE dpi = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dpi);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                resp.getWriter().write("Cliente eliminado");
            } else {
                resp.getWriter().write("No se encontró el cliente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String sql = """
            UPDATE cliente
            SET nacionalidad = ?
            WHERE dpi = ?
        """;

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Aleman");
            ps.setString(2, "999");

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}