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

    private Connection connectDB() {
        try {
            conn = this.ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void disconnectDB(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//FUNCIONES PARA TODO LOS TIPOS DE CONSULTAS QUE TENDRA MI PROYECTO
    public boolean validarUsuario(String user) {

        conn = connectDB();
        String query = " Select Email from  Users;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;

        try {
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            while (resultadotabla.next()) {
                if (user.equals(resultadotabla.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

        return false;
    }

    public String retornarNombre(String user) {

        conn = connectDB();
        String query = "Select UserName from Users where Email = ?;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        String nombre = "";

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, user);
            resultadotabla = consulta.executeQuery();
            nombre = resultadotabla.getString(1);

        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

        return nombre.toUpperCase();
    }

    public int retornarID(String user) {

        conn = connectDB();
        String query = "Select UserID from Users where Email = ?;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        int id = 0;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, user);
            resultadotabla = consulta.executeQuery();
            id = resultadotabla.getInt(1);

        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

        return id;
    }

    public void insertarUsuario(String nombre, String correoElectronico) {

        conn = connectDB();
        String query = "Insert into Users(UserName, Email) Values(?, ?);";
        PreparedStatement consulta = null;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, nombre);
            consulta.setString(2, correoElectronico);

            boolean resultado = consulta.execute();

            if (resultado) {
                System.out.println(Console_Colors.ANSI_RED + "**NO SE PUDIERON GUARDAR LOS DATOS" + Console_Colors.ANSI_RESET);
            } else {
                System.out.println(Console_Colors.ANSI_GREEN + "Datos Guardados" + Console_Colors.ANSI_RESET);
            }

        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

    public void insertarEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String ubicacionEvento, String horaInicio, String horaFin, int userID) {

        conn = connectDB();
        String query = "Insert into Events(EventName, Description, Date, Location, StartTime, EndTime, UserID) Values(?,?,?,?,?,?,?)";
        PreparedStatement consulta = null;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, nombreEvento);
            consulta.setString(2, descripcionEvento);
            consulta.setString(3, fechaEvento);
            consulta.setString(4, ubicacionEvento);
            consulta.setString(5, horaInicio);
            consulta.setString(6, horaFin);
            consulta.setInt(7, userID);

            boolean resultado = consulta.execute();

            if (resultado) {
                System.out.println(Console_Colors.ANSI_RED + "**SE PRODUJO UN ERROR AL INTENTAR GUARDAR EL EVENTO" + Console_Colors.ANSI_RESET);
            } else {
                System.out.println(Console_Colors.ANSI_GREEN + "Evento ha sido guardado" + Console_Colors.ANSI_RESET);
            }

        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

    public void mostrarEventosRecientes() {
        conn = connectDB();
        String query = "Select a.EventName, a.Date, a.StartTime, a.EndTime, a.Description From Events a where a.date > date('now') order by Date asc Limit 3";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        try {
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            System.out.printf(Console_Colors.ANSI_BLUE + "%-16s%-15s%-8s%-8s%-100s%n", "NOMBRE", "FECHA", "INICIO", "FIN", "DESCRIPCION" + Console_Colors.ANSI_RESET);
            while (resultadotabla.next()) {
                System.out.printf("%-16s%-15s%-8s%-8s%-100s%n", resultadotabla.getString(1), resultadotabla.getString(2), resultadotabla.getString(3), resultadotabla.getString(4), resultadotabla.getString(5));
            }
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

    public void mostrarEventosPorDia(String diaConsulta) {
        conn = connectDB();
        String query = "Select a.EventID, a.EventName, a.Date, a.StartTime, a.EndTime, a.Description From Events a where a.date = ? order by EventID asc";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, diaConsulta);
            resultadotabla = consulta.executeQuery();
            System.out.printf(Console_Colors.ANSI_BLUE + "%-4s%-16s%-15s%-8s%-8s%-100s%n", "ID", "NOMBRE", "FECHA", "INICIO", "FIN", "DESCRIPCION" + Console_Colors.ANSI_RESET);
            while (resultadotabla.next()) {
                System.out.printf("%-4s%-16s%-15s%-8s%-8s%-100s%n", resultadotabla.getInt(1), resultadotabla.getString(2), resultadotabla.getString(3), resultadotabla.getString(4), resultadotabla.getString(5), resultadotabla.getString(6));
            }
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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

    public void mostrarEventosPorMes(String mesConsulta) {
        conn = connectDB();
        String query = "Select a.EventID, a.EventName, a.Date, a.StartTime, a.EndTime, a.Description From Events a where strftime('%Y-%m', a.Date)= ? order by a.date";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, mesConsulta);
            resultadotabla = consulta.executeQuery();
            System.out.printf(Console_Colors.ANSI_BLUE + "%-4s%-16s%-15s%-8s%-8s%-100s%n", "ID", "NOMBRE", "FECHA", "INICIO", "FIN", "DESCRIPCION" + Console_Colors.ANSI_RESET);
            while (resultadotabla.next()) {
                System.out.printf("%-4s%-16s%-15s%-8s%-8s%-100s%n", resultadotabla.getInt(1), resultadotabla.getString(2), resultadotabla.getString(3), resultadotabla.getString(4), resultadotabla.getString(5), resultadotabla.getString(6));
            }
        } catch (SQLException e) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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
