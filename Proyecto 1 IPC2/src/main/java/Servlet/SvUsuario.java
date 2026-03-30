package Servlet;

import BaseDatos.ConexionDB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;


@WebServlet("/usuarios")
public class SvUsuario extends HttpServlet {

    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("POST ejecutado");
        response.setContentType("application/json");

        try (Connection conn = ConexionDB.getConexion()) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String id_rol = request.getParameter("id_rol");

            String sql = "INSERT INTO usuario (username, password, id_rol) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, Integer.parseInt(id_rol));

            stmt.executeUpdate();

            response.getWriter().write("Usuario insertado");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: ----------------NO SE PUDO" + e.getMessage());
        }



    }


}
