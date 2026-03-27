package com.example.proyecto1_ipc2;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import BaseDatos.ConexionDB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    String numero  =  null;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
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


    public void destroy() {
    }
}