/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registropeliculas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author lcarl
 */
public class DB {
    //Aqui deberiamos realizar la conexion a la base de datos
    //y funciones que se requieran
    private static final String URL = "jdbc:mysql://localhost:3306/peliculas-tap";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "77pacman2004";
    private static Connection conexion;

    // Método para establecer la conexión
    public static Connection conectar() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void close() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
