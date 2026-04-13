package Modelos;

import java.util.List;

public class ResultadoCarga {

    private int procesados;
    private List<String> errores;

    public ResultadoCarga(int procesados, List<String> errores) {
        this.procesados = procesados;
        this.errores = errores;
    }
}
