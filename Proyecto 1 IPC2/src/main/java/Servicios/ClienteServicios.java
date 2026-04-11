package Servicios;

import BaseDatos.ClienteDB;
import Modelos.Cliente;
import Modelos.Paquete;

import java.sql.SQLException;
import java.util.List;

public class ClienteServicios {

    private final ClienteDB clienteDB = new ClienteDB();

    public List<Cliente> obtenerTodos() {
        try {
            return clienteDB.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener clientes", e);
        }
    }


    public Cliente obtener(String dpi) {
        try {
            return clienteDB.getByDpi(dpi);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener paquete", e);
        }
    }

    public Cliente crear(Cliente cliente) {
        try {
            return clienteDB.create(
                    cliente.getDpi(),
                    cliente.getNombre(),
                    cliente.getFechaNacimiento(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.getNacionalidad()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear cliente", e);
        }
    }

    public Cliente actualizar(Cliente cliente) throws SQLException {
        return clienteDB.update(
                cliente.getDpi(),
                cliente.getTelefono(),
                cliente.getEmail()
        );
    }

    public void eliminar(String dpi) throws SQLException {
        clienteDB.delete(dpi);
    }

}