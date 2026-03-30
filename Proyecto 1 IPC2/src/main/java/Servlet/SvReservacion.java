package Servlet;

import BaseDatos.ConexionDB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;


@WebServlet("/reservaciones")
public class SvReservacion extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (Connection conn = ConexionDB.getConexion()) {

            String fechaViaje = req.getParameter("fecha_viaje");
            String idPaquete = req.getParameter("id_paquete");
            String cantidad = req.getParameter("cantidad_personas");

            //Generar código simple
            String codigo = "RES" + System.currentTimeMillis();

            if(codigo.length() >= 10){

                codigo = codigo.substring(0, 10);

            }




            // 🔥 Usuario fijo (temporal)
            int idUsuario = 1;

            String sql = """
            INSERT INTO reservacion 
            (codigo, fecha_creacion, fecha_viaje, id_paquete, id_usuario, cantidad_personas, costo_total, estado)
            VALUES (?, NOW(), ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, codigo);
            stmt.setString(2, fechaViaje);
            stmt.setInt(3, Integer.parseInt(idPaquete));
            stmt.setInt(4, idUsuario);
            stmt.setInt(5, Integer.parseInt(cantidad));

            // costo temporal
            stmt.setDouble(6, 0.0);

            stmt.setString(7, "PENDIENTE");

            stmt.executeUpdate();

            resp.getWriter().write("{\"mensaje\":\"Reservacion creada\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }



}
