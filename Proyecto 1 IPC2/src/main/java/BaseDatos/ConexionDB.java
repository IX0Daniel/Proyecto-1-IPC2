package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/Aeropuerto";
    private static final String USERNAME = "DBAdminAeropuerto";
    private static final String PASSWORD = "PSWRDAdmin";

    public static Connection getConexion() throws SQLException{

        //return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar con la base de datos"+e);
        }

        return null;
    }
}
