package Filtros;

import Modelos.Usuario;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class FiltroAutenticacion implements Filter {

    /*@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // no se
        if (path.contains("/login") || path.contains("/register") || path.contains("/css") || path.contains("/js")) {
            chain.doFilter(request, response);
            return;
        }


        ///

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            res.setStatus(401);
            //res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //res.getWriter().write("{\"error\":\"No autorizado\"}");
            return;
        }

        Usuario user = (Usuario) session.getAttribute("usuario");

        if (path.contains("/paquetes") && !user.getRol().equals("operaciones")) {
            res.setStatus(403);
            return;
        }
        // Usuario valido
        chain.doFilter(request, response);
    }*/





    private final Gson gson = new Gson();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();


        res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");


        // Preflight request (Angular lo usa)
        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (path.contains("/login") || path.contains("/css") || path.contains("/js") || path.contains("/imagenes")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write(gson.toJson(Map.of("error", "No autorizado")));
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        /* Area de operaciones - PAQUETES Y DESTINOS
        if (usuario.getRol().equals("operaciones")) {

            System.out.println("PATH: " + path);
            System.out.println("ROL: " + usuario.getRol());

            System.out.println("PATH COMPLETO: " + req.getRequestURI());
            System.out.println("Usuario completo: " + usuario);


            if (path.contains("/paquetes") || path.contains("/destinos")) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write(gson.toJson(
                        Map.of("error", "Acceso denegado")
                ));
                return;
            }
        }


        // Atención al cliente - CLIENTES Y RESERVACIONES
        if (usuario.getRol().equals("atencion")) {

            System.out.println("PATH: " + path);
            System.out.println("ROL: " + usuario.getRol());

            System.out.println("PATH COMPLETO: " + req.getRequestURI());
            System.out.println("Usuario completo: " + usuario);

            if (path.contains("/clientes") || path.contains("/reservaciones")) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write(gson.toJson(Map.of("error", "Acceso denegado")));
                return;
            }
        }*/


        String endpoint = req.getRequestURI().substring(req.getContextPath().length());
        String rol = usuario.getRol();



        if(rol.equals("admin")) {
            chain.doFilter(request, response);
            return;
        }

        // Bloquea el acceso del paquetes y destinos al Area de Atención al CLiente
        if (rol.equals("atencion")) {
            if (
                    endpoint.startsWith("/paquetes") ||
                            endpoint.startsWith("/destinos")
            ) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("{\"error\":\"Acceso denegado\"}");
                return;
            }
        }

        // Bloquea el acceso del clientes y reservaciones al Area de Operaciones
        if (rol.equals("operaciones")) {
            if (
                    endpoint.startsWith("/clientes") ||
                            endpoint.startsWith("/reservaciones")
            ) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("{\"error\":\"Acceso denegado\"}");
                return;
            }
        }


        chain.doFilter(request, response);
    }




}
