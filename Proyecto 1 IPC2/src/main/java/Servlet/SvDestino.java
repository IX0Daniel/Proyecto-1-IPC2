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
import java.sql.Statement;


@WebServlet("/destinos")
public class SvDestino extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection conn = ConexionDB.getConexion()) {

            String sql = "SELECT nombre, pais, descripcion FROM destino";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"nombre\":\"").append(rs.getString("nombre")).append("\",")
                        .append("\"pais\":\"").append(rs.getString("pais")).append("\",")
                        .append("\"descripcion\":\"").append(rs.getString("descripcion")).append("\"")


                        .append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST ejecutado");
        resp.setContentType("application/json");

        try (Connection conn = ConexionDB.getConexion()) {

            String nombre = req.getParameter("nombre");
            String pais = req.getParameter("pais");
            String descripcion = req.getParameter("descripcion");

            String sql = "INSERT INTO destino (nombre, pais, descripcion) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, pais);
            stmt.setString(3, descripcion);

            stmt.executeUpdate();

            resp.getWriter().write("Destino insertado");

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: ----------------NO SE PUDO" + e.getMessage());
        }
    }



}
