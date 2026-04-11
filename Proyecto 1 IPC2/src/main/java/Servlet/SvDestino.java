package Servlet;

import BaseDatos.ClienteDB;
import BaseDatos.ConexionDB;
import BaseDatos.DestinoDB;
import Modelos.Cliente;
import Modelos.Destino;
import Servicios.ClienteServicios;
import Servicios.DestinoServicios;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


@WebServlet("/destinos/*")
public class SvDestino extends HttpServlet {

    private final DestinoServicios servicio = new DestinoServicios();
    private final Gson gson = new Gson();
    private DestinoDB destinoDB = new DestinoDB();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null) {
                List<Destino> destinos = destinoDB.getAll();
                resp.getWriter().write(new Gson().toJson(destinos));
            }else{
                int id_destino = Integer.parseInt(pathInfo.substring(1));
                Destino destino = destinoDB.getByID(id_destino);

                if(destino ==null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                resp.getWriter().write(new Gson().toJson(destino));
            }

        } catch (Exception e) {

            System.out.println("No se pudo conectar");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try {
            Destino destino = gson.fromJson(request.getReader(), Destino.class);
            Destino creado = servicio.crear(destino);


            response.setStatus(201);
            response.getWriter().write(gson.toJson(creado));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        try {
            Destino destino = gson.fromJson(req.getReader(), Destino.class);
            Destino actualizado = servicio.actualizar(destino);
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
        String id_destino = pathInfo.substring(1);

        try {
            servicio.eliminar(id_destino);
            res.setStatus(204);
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }




}
