package Servlet;

import BaseDatos.ClienteDB;
import BaseDatos.ConexionDB;
import Modelos.Cliente;
import Servicios.ClienteServicios;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import java.sql.*;
import java.util.List;


@WebServlet("/clientes/*")
public class SvClientes extends HttpServlet {

    private final ClienteServicios servicio = new ClienteServicios();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null) {
                List<Cliente> clientes = servicio.obtenerTodos();
                resp.getWriter().write(new Gson().toJson(clientes));
            }else{
                String dpi = pathInfo.substring(1);
                Cliente cliente = servicio.obtener(dpi);


                if(cliente == null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                resp.getWriter().write(new Gson().toJson(cliente));
            }

        } catch (Exception e) {

            System.out.println("No se pudo conectar - clientes");
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        try {
            Cliente cliente = gson.fromJson(req.getReader(), Cliente.class);
            Cliente creado = servicio.crear(cliente);

            if(creado.getDpi().equals("") || creado.getDpi() == null){
                return;
            }
            if(creado.getNombre().equals("") || creado.getNombre() == null){
                return;
            }
            if(creado.getNacionalidad().equals("") || creado.getNacionalidad() == null){
                return;
            }
            if(creado.getEmail().equals("") || creado.getEmail() == null){
                return;
            }
            res.setStatus(201);
            res.getWriter().write(gson.toJson(creado));
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        try {
            Cliente cliente = gson.fromJson(req.getReader(), Cliente.class);
            Cliente actualizado = servicio.actualizar(cliente);

            res.getWriter().write(gson.toJson(actualizado));
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            res.setStatus(400);
            return;
        }
        String dpi = pathInfo.substring(1);

        try {
            servicio.eliminar(dpi);
            res.setStatus(204);
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }





}