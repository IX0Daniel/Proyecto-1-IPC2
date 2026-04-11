package Servicios;

import BaseDatos.ClienteDB;
import BaseDatos.PaqueteDB;
import Modelos.Cliente;
import Modelos.Paquete;

import java.sql.SQLException;
import java.util.List;

public class PaqueteServicios {

    private final PaqueteDB paqueteDB = new PaqueteDB();

    public List<Paquete> obtenerTodos() {
        try {
            return paqueteDB.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener paquetes", e);
        }
    }

    public Paquete crear(Paquete paquete) {
        try {
            return paqueteDB.create(
                    paquete.getNombre(),
                    paquete.getId_destino(),
                    paquete.getDuracion(),
                    paquete.getPrecio(),
                    paquete.getCapacidad(),
                    paquete.getEstado()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear paquete", e);
        }
    }


    public Paquete obtener(int id_paquete) {
        try {
            return paqueteDB.getByID(id_paquete);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener paquete", e);
        }
    }

    public Paquete actualizar(Paquete paquete) throws SQLException {
        return paqueteDB.update(
                paquete.getId_paquete(),
                paquete.getNombre(),
                paquete.getId_destino(),
                paquete.getDuracion(),
                paquete.getPrecio(),
                paquete.getCapacidad(),
                paquete.getEstado()
        );
    }


    public void eliminar(int id_paquete) {
        try {
            paqueteDB.delete(id_paquete);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paquete", e);
        }
    }

}
