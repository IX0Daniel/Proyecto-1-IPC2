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

@WebServlet("/paquetes")
public class SvPaquetes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("aplication/json");

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection conn = ConexionDB.getConexion()) {

            String sql = "SELECT nombre, id_destino, duracion, precio, capacidad, estado FROM paquete";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"nombre\":\"").append(rs.getString("nombre")).append("\",")
                        .append("\"id_destino\":\"").append(rs.getString("id_destino")).append("\",")
                        .append("\"duracion\":\"").append(rs.getString("duracion")).append("\"")
                        .append("\"precio\":\"").append(rs.getString("precio")).append("\",")
                        .append("\"capacidad\":\"").append(rs.getString("capacidad")).append("\",")
                        .append("\"estado\":\"").append(rs.getString("estado")).append("\"")



                        .append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        response.getWriter().write(json.toString());


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("aplication/json");

        try (Connection connection = ConexionDB.getConexion()){

            String nombre = request.getParameter("nombre");
            String id_destino = request.getParameter("id_destino");
            String duracion = request.getParameter("duracion");
            String precio = request.getParameter("precio");
            String capacidad = request.getParameter("capacidad");
            String estado = request.getParameter("estado");

            String sql = "INSERT INTO paquete (nombre, id_destino, duracion, precio, capacidad, estado) VALUES (?, ?, ?, ?, ? ,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, Integer.parseInt(id_destino));
            stmt.setInt(3, Integer.parseInt(duracion));
            stmt.setInt(4, Integer.parseInt(precio));
            stmt.setInt(5, Integer.parseInt(capacidad));
            stmt.setBoolean(6, Boolean.parseBoolean(estado));

            stmt.executeUpdate();

            response.getWriter().write("Paquete insertado");


        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: ----------------NO SE PUDO" + e.getMessage());
        }



    }


}
