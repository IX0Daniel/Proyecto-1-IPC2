package Filtros;

import Modelos.Cliente;
import Modelos.Destino;
import Servicios.ClienteServicios;
import Servicios.DestinoServicios;
import Servicios.PaqueteServicios;
import Servicios.UsuarioServicios;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtrador {



    private final UsuarioServicios usuarioService = new UsuarioServicios();
    private final ClienteServicios clienteService = new ClienteServicios();
    private final DestinoServicios destinoService = new DestinoServicios();
    private final PaqueteServicios paqueteService = new PaqueteServicios();

    public void parseLinea(String line) {



        if (line.startsWith("USUARIO")) {
            procesarUsuario(line);
        } else if (line.startsWith("CLIENTE")) {
            procesarCliente(line);
        } else if (line.startsWith("DESTINO")) {
            procesarDestino(line);
        }else {
            throw new RuntimeException("Tipo desconocido");
        }
    }



    private List<String> extraerDatos(String line) {
        int start = line.indexOf("(");
        int end = line.lastIndexOf(")");

        String contenido = line.substring(start + 1, end);

        return Arrays.stream(contenido.split(","))
                .map(s -> s.trim().replace("\"", ""))
                .collect(Collectors.toList());
    }



    private void procesarCliente(String line) {
        List<String> datos = extraerDatos(line);

        clienteService.crear(new Cliente(
                datos.get(0),
                datos.get(1),
                datos.get(2),
                datos.get(3),
                datos.get(4),
                datos.get(5))
        );
    }


    private void procesarUsuario(String line) {
        List<String> datos = extraerDatos(line);

        usuarioService.crear(
                        datos.get(0),
                        datos.get(1),
                        Integer.parseInt(datos.get(2)
                )
        );
    }

    private void procesarDestino(String line) {
        List<String> datos = extraerDatos(line);

        destinoService.crear(new Destino(
                0,
                datos.get(0),
                datos.get(1),
                datos.get(2)
                )
        );
    }



}
