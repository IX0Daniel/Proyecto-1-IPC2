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
            String fecha_nacimiento = req.getParameter("fecha_nacimiento");
            String telefono = req.getParameter("telefono");
            String email = req.getParameter("email");
            String nacionalidad = req.getParameter("nacionalidad");

            String sql = "INSERT INTO cliente (dpi, nombre, fecha_nacimiento, telefono, email, nacionalidad) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dpi);
            stmt.setString(2, nombre);
            stmt.setString(3, fecha_nacimiento);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.setString(6, nacionalidad);

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");

        try (Connection conn = ConexionDB.getConexion()) {

            String dpi = req.getParameter("dpi");
            String telefono = req.getParameter("telefono");

            if (dpi == null || telefono == null) {
                resp.getWriter().write("Faltan parametros");
                return;
            }

            String sql = "UPDATE cliente SET telefono=? WHERE dpi=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, telefono);
            stmt.setString(2, dpi);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                resp.getWriter().write("Telefono actualizado");
            } else {
                resp.getWriter().write("Cliente no encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    /*
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");

        String sql = "UPDATE cliente SET telefono = ? WHERE dpi = ? ";



        try (Connection conn = ConexionDB.getConexion()){

            String telefono = req.getParameter("telefono");

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "39015129");

            int filas = ps.executeUpdate();


            if (filas > 0) {
                resp.getWriter().write("Número de cliente actualizado");
            } else {
                resp.getWriter().write("No se encontró el cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();

            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
*/
}