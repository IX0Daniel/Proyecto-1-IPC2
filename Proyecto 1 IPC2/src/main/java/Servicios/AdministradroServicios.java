package Servicios;

import Filtros.Filtrador;
import Modelos.ResultadoCarga;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradroServicios {

    private final Filtrador parser = new Filtrador();

    public ResultadoCarga procesar(BufferedReader reader) throws IOException {

        String line;
        int procesados = 0;
        List<String> errores = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            try {
                parser.parseLinea(line);
                procesados++;
            } catch (Exception e) {
                errores.add("Error en línea: " + line);
            }
        }

        return new ResultadoCarga(procesados, errores);
    }


}


