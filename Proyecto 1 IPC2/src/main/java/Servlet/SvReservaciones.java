package Servlet;

import Requests.ReservacionRequest;
import Servicios.ReservacionServicios;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;


@WebServlet("/reservaciones")
public class SvReservaciones extends HttpServlet {



    private final Gson gson = new Gson();
    private final ReservacionServicios servicio = new ReservacionServicios();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            ReservacionRequest request = gson.fromJson(body, ReservacionRequest.class);

            servicio.crearReservacion(request);

            res.setStatus(201);
            res.getWriter().write("{\"mensaje\":\"Reservación creada\"}");

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"Error al crear reservación\"}");
        }
    }
}
