package com.example.proyecto1_ipc2;


import BaseDatos.ConexionDB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/testdb")
public class prueba extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");

        try (Connection conn = ConexionDB.getConexion()) {
            resp.getWriter().write("Conexion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("ERROR: " + e.getMessage());
        }
    }
}