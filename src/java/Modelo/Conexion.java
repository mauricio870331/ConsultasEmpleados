package Modelo;

import java.sql.*;

public class Conexion {

    static Connection con = null;
    static Connection con2 = null;
    static Connection con3 = null;
    public static String query;
    public static Statement stat;
    public static ResultSet rs;    

    public static Connection conectar() {
//        System.out.println("inicia1");
//                String connectionUrl = "jdbc:sqlserver://192.168.20.55:1433;"
//                 + "databaseName=expresop_convenios;user=sa;password=EPpal2003;";
        
        String connectionUrl = "jdbc:sqlserver://192.168.10.1:1433;"
                + "databaseName=expresop_convenios;user=expresop;password=s1st3m45;";
        
//        
//        String connectionUrl = "jdbc:sqlserver://192.168.10.7:1433;" 
//                + "databaseName=expresop_convenios;user=SA;password=EPpal2003;"; //pruebas
        
        // Declaramos los sioguientes objetos
        try {
            //Establecemos la conexión
//            System.out.println("inicia");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
//            System.out.println("Conectar");
            // Create and execute an SQL statement that returns some data.
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error " + e);
        }
        return con;
    }

    public static void cerrarConexion() {
        try {
            stat.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error en cerrar la base de datos" + e.toString());
        }
    }

    //Conexion Rutas
    public static Connection conectar2() {
        String connectionUrl = "jdbc:sqlserver://192.168.20.55:1433;"
                + "databaseName=NodumEP;user=sa;password=EPpal2003;";
        // Declaramos los sioguientes objetos
        try {
            //Establecemos la conexión           
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con2 = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error " + e);
        }
        return con2;
    }

    //conexion old app
    public static Connection getConexionOldApp() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (con3 == null) {
                con3 = DriverManager.getConnection("jdbc:mysql://199.189.248.130:3306/expresop_convenios", "expresop_user", "S1st3m4s");
            }
            //            System.out.println("conectado 1");
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            System.out.println("error " + e);
        }
        return con3;
    }

    public static void cerrarConexion2() {
        try {
            con2.close();
        } catch (SQLException e) {
            System.out.println("Error en cerrar la base de datos" + e.toString());
        }
    }

    public static void cerrarConexion3() {
        try {
            con3.close();
            con3 = null;
        } catch (SQLException e) {
            System.out.println("Error en cerrar la base de datos" + e.toString());
        }
    }

    

//    public static void main(String[] p) throws SQLException {
////        Conexion.conectar();
//        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        l=(ArrayList)CrudObject.getSelectSql("login", 1, "1144,1234");        
//        for (ConsultaGeneral consultaGeneral : l) {
//            System.out.println("- " + consultaGeneral.getNum1()+"-"+consultaGeneral.getStr1());
//        }
//    }
}
