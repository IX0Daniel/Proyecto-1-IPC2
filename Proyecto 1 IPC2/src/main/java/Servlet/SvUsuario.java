package Servlet;

import Modelos.Usuario;
import Requests.LoginRequest;
import Servicios.UsuarioServicios;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.stream.Collectors;


@WebServlet("/login")
public class SvUsuario extends HttpServlet {

    private final UsuarioServicios servicio = new UsuarioServicios();
    private final Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            LoginRequest request = gson.fromJson(body, LoginRequest.class);
            Usuario usuario = servicio.login(request.username, request.password);


            if (usuario == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("{\"error\":\"Credenciales incorrectas\"}");
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("usuario", usuario);

            res.setStatus(200);
            res.getWriter().write(gson.toJson(usuario));

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"Error en login\"}");
        }
    }

}
