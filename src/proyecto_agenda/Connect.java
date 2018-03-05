/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;
/**
 *
 * @author pc HP
 */
public class Connect {
    private DataSource ds;
    private Connection conn;

    public Connect() {
        ds = SQLDatasource.getSQLLiteDataSource();
        conn = null;
    }
    
    private Connection connectDB(){
        try {
            conn = this.ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    private void disconnectDB(Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//FUNCIONES PARA TODO LOS TIPOS DE CONSULTAS QUE TENDRA MI PROYECTO
    
    
    public boolean validarUsuario(String user){
        
        conn = connectDB();
        String query = " Select Email from  Users";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        
        try{
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            while (resultadotabla.next()){
                if (user.equals(resultadotabla.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return false;
    }
    
    public String retornarNombre(String user){
        
        conn = connectDB();
        String query = "Select UserID, UserName from Users where Email = ?";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        String nombre = "";
        int id = 0;
        
        try{
            consulta = conn.prepareStatement(query);
            consulta.setString(1, user);
            resultadotabla = consulta.executeQuery();
            id = resultadotabla.getInt(1);
            nombre = resultadotabla.getString(2);
            
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return id + ". " + nombre.toUpperCase();
    }
    
    public int retornarID(String user){
        
        conn = connectDB();
        String query = "Select UserID from Users where Email = ?";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        int id = 0;
        
        try{
            consulta = conn.prepareStatement(query);
            consulta.setString(1, user);
            resultadotabla = consulta.executeQuery();
            id = resultadotabla.getInt(1);
                        
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return id;
    }
    
    public void insertarUsuario( String nombre, String correoElectronico){
        
        conn = connectDB();
        String query = "Insert into Users(UserName, Email) Values(?, ?)";
        PreparedStatement consulta = null;
        
        try{
            consulta = conn.prepareStatement(query);
            consulta.setString(1, nombre);
            consulta.setString(2, correoElectronico);
            
            boolean resultado = consulta.execute();
            
            if (resultado) {
                System.out.println(Console_Colors.ANSI_RED + "**NO SE PUDIERON GUARDAR LOS DATOS" + Console_Colors.ANSI_RESET);
            }
            else{
                System.out.println(Console_Colors.ANSI_GREEN + "Datos Guardados" + Console_Colors.ANSI_RESET);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
