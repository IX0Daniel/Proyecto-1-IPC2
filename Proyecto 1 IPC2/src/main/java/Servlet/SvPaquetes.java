package Servlet;


import Modelos.Cliente;
import Modelos.Paquete;
import Servicios.PaqueteServicios;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/paquetes")
public class SvPaquetes extends HttpServlet {

    private final PaqueteServicios servicio = new PaqueteServicios();
    private final Gson gson = new Gson();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null) {
                List<Paquete> paquetes = servicio.obtenerTodos();
                response.getWriter().write(new Gson().toJson(paquetes));
            }else{
                int id = Integer.parseInt( pathInfo.substring(1));
                Paquete paquete = servicio.obtener(id);

                if(paquete ==null){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                response.getWriter().write(new Gson().toJson(paquete));
            }

        } catch (Exception e) {

            System.out.println("No se pudo conectar :VV:V.vV.v");
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        try {

            String body = req.getReader().lines().collect(Collectors.joining());
            Paquete paquete = gson.fromJson(body, Paquete.class);
            Paquete creado = servicio.crear(paquete);

            res.setStatus(201);
            res.getWriter().write(gson.toJson(creado));
        } catch (Exception e) {
            e.printStackTrace();
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

        int id_paquete = Integer.parseInt(pathInfo.substring(1));

        try {
            servicio.eliminar(id_paquete);
            res.setStatus(204);
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        try {
            Paquete paquete = gson.fromJson(request.getReader(), Paquete.class);
            Paquete actualizado = servicio.actualizar(paquete);

            res.getWriter().write(gson.toJson(actualizado));
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }


}