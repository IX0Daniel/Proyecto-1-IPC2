package Servlet;

import Modelos.ResultadoCarga;
import Servicios.AdministradroServicios;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@WebServlet("/administrador")
@MultipartConfig
public class SvAdministrador extends HttpServlet {



    private final AdministradroServicios service = new AdministradroServicios();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {

        Part filePart = req.getPart("file");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(filePart.getInputStream())
        );

        ResultadoCarga resultado = service.procesar(reader);

        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(resultado));
    }

}
