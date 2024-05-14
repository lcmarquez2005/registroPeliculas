/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registropeliculas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author lcarl
 */
public class CRUD {
    // deberiamos importar la conexion creada o algo asi, 
//    luego aqui van todas las funciones principales, create,read,update,delete
//    Connection conn = DB.conectar();
    private static PreparedStatement statement;
    
    // Método para crear una nueva película en la base de datos//*DONE */
    public static void create(String name, Date dateLaunch, String director, char category, int duration) throws SQLException {
        Connection conexion = DB.conectar(); // Utiliza el método estático conectar de la clase DB
        
        PreparedStatement preparedStatement = conexion.prepareStatement("INSERT INTO peliculas ( name, dateLaunch, director, category, duration) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, name);


        preparedStatement.setDate(2, new java.sql.Date(dateLaunch.getTime())); // Convierte java.util.Date a java.sql.Date
        preparedStatement.setString(3, director);
        preparedStatement.setString(4, String.valueOf(category)); // Convierte char a String
        preparedStatement.setInt(5, duration);
        
        int filasInsertadas = preparedStatement.executeUpdate();

        if (filasInsertadas > 0) {
            System.out.println("Se creó la película correctamente.");
        } else {
            System.out.println("No se pudo crear la película.");
        }

        preparedStatement.close();
        DB.close(); // Utiliza el método estático cerrar de la clase DB
    }
    



    //*update DONEEEEEEEEEEEEEEEEE */
    //just left to change the parsing, to not be that bad
    public static void update(int id, String name, Date date, String director, char category, int duration) throws SQLException {
        Connection conexion = DB.conectar();

        PreparedStatement st = conexion.prepareStatement("UPDATE peliculas SET name=?, datelaunch=?, director=?, category=?, duration=? WHERE id=?;");
        
        st.setString(1,name);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = sdf.format(date);
        try {
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateFormat);
            st.setDate(2,sqlDate);

        } catch(IllegalArgumentException e){
            System.out.println("ha habido un error con el formato de fecha\n\n");
            System.out.println(e);
        }
        
        st.setString(3,director);
        st.setString(4, String.valueOf(category) );//no existe el metodo tipo setChar, asi que parseamos nuestro Char a un String para ser insertado en la DB
        st.setInt(5,duration);
        st.setInt(6,id);

        int filasActualizadas = st.executeUpdate();
        if( filasActualizadas > 0) {
            System.out.println("Se ha actualizado correctamente" + filasActualizadas + "registros..");
        } else {
            System.out.println("No se logro actualizar ninguna pelicula con el ID" + id);
        }

        // st.close();
        DB.close();

    }
    



    // Método para eliminar una película por su ID
    public static void delete(int id) throws SQLException {
        Connection conexion = DB.conectar();
        String query = "DELETE FROM peliculas WHERE id=?";
        
        PreparedStatement st = conexion.prepareStatement(query);
        st.setInt(1, id);
        
        int filasEliminadas = st.executeUpdate();

        if (filasEliminadas > 0) {
            System.out.println("Se eliminó la película con el ID " + id + " correctamente.");
        } else {
            System.out.println("No se encontró ninguna película con el ID " + id + ".");
        }

        st.close();
        DB.close();
    }

    
    




    public static void consultarDatos() throws SQLException {
        Connection conexion = DB.conectar();
        String consulta = "SELECT * FROM peliculas";
        statement = conexion.prepareStatement(consulta);
        
        
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            JOptionPane.showMessageDialog(null,  resultado.getString("name")+" su id es: "+ resultado.getInt("id"));
        }
        
        resultado.close();
        DB.close();
    }
    






// Método para leer los datos de una película por su ID
    public static void read(int id) throws SQLException {
        Connection conexion = DB.conectar();
        String query = "SELECT * FROM peliculas WHERE id=?";
        
        statement = conexion.prepareStatement(query);
        statement.setInt(1, id);
        
        ResultSet resultado = statement.executeQuery();

        // Verificar si se encontraron resultados
        if (resultado.next()) {
            // Leer los datos de la película
            int idPelicula = resultado.getInt("id");
            String name = resultado.getString("name");
            String director = resultado.getString("director");
            // Leer más columnas si es necesario

            // Imprimir los datos de la película
            System.out.println("ID: " + idPelicula);
            System.out.println("Título: " + name);
            System.out.println("Director: " + director);
            // Imprimir más datos si es necesario
        } else {
            System.out.println("No se encontró ninguna película con el ID especificado.");
        }

        resultado.close();
        DB.close();
    }






}
