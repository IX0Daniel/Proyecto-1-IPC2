package Servicios;

import BaseDatos.DestinoDB;
import Modelos.Destino;
import Modelos.Paquete;

import java.sql.SQLException;
import java.util.List;

public class DestinoServicios {

    private final DestinoDB destinoDB = new DestinoDB();

    public List<Destino> obtenerTodos() {
        try {
            return destinoDB.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener destinos", e);
        }
    }

    public Destino obtener(int id_destino) {
        try {
            return destinoDB.getByID(id_destino);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener destino", e);
        }
    }

    public Destino crear(Destino destino) {
        try {
            return destinoDB.create(
                    destino.getNombre(),
                    destino.getPais(),
                    destino.getDescripcion()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear destino", e);
        }
    }

    public Destino actualizar(Destino destino) throws SQLException {

        System.out.println("Se va a actualizar un destino: " + destino.getId_destino() + " - "+ destino.getNombre()+ " - " + destino.getPais() + " - " + destino.getDescripcion());

        return destinoDB.update(
                destino.getId_destino(),
                destino.getNombre(),
                destino.getPais(),
                destino.getDescripcion()
        );
    }

    public void eliminar(String dpi) throws SQLException {
        destinoDB.delete(dpi);
    }

}
