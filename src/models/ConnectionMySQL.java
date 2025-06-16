
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name = "verduleria";
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;
    
    
    //Método para conectar Java con MySQL
    
    public Connection getConnection() {
       try{
           //Obtener valor del Driver
           Class.forName("com.mysql.cj.jdbc.Driver");
           //Obterner la conexión
           conn = DriverManager.getConnection(url, user, password);
       
       }catch(ClassNotFoundException e){
           System.err.println("Ha ocurrido un ClassNotFounException" + e.getMessage());
       }catch (SQLException e){
           System.err.println("Ha ocurrido un SQLException" + e.getMessage());
       }
       return conn;
    }
    
}
