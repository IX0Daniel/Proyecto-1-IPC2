package Servicios;

import BaseDatos.PaqueteDB;
import BaseDatos.ReservacionDB;
import Modelos.Paquete;
import Requests.ReservacionRequest;

import java.sql.SQLException;

public class ReservacionServicios {



    private ReservacionDB reservacionDB = new ReservacionDB();
    private PaqueteDB paqueteDB = new PaqueteDB();

    public void crearReservacion(ReservacionRequest req) throws SQLException {

        Paquete paquete = paqueteDB.getByID(req.getIdPaquete());

        if (paquete == null) {
            System.out.println("Paquete no encontrado");
            throw new RuntimeException("Paquete no existe");
        }

        // 2. Validar capacidad
        if (req.getClientes().size() > paquete.getCapacidad()) {
            System.out.println("Capacidad excedida");

            throw new RuntimeException("Capacidad excedida");
        }

        // 3. Calcular costo
        double costoTotal = paquete.getPrecio() * req.getClientes().size();

        // 4. Crear reservación
        int idReservacion = reservacionDB.crearReservacion(
                req.getFechaViaje(),
                req.getIdPaquete(),
                req.getIdUsuario(),
                req.getClientes().size(),
                costoTotal
        );

        // 5. Insertar clientes
        for (String dpi : req.getClientes()) {
            reservacionDB.insertarCliente(idReservacion, dpi);
        }
    }
}
