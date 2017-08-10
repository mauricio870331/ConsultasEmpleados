/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Entities.Ciudad;
import Modelo.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author admin
 */
public class prueba {

    ArrayList<Ciudad> list_origen = new ArrayList();
    ArrayList<Ciudad> list_destino = new ArrayList();
    ArrayList<Ciudad> list_empresas = new ArrayList();

    static Connection cn;
    static PreparedStatement pstm;
    static ResultSet rs;
    static CallableStatement cstmt;

    public static void main(String[] juan) throws SQLException {
        Date d = new Date();
        prueba p = new prueba();
        p.cargarDatos();
String m = "mauricio";
        System.out.println("m = "+m.substring(0, 8));
    }

    public void cargarDatos() throws SQLException {
        try {
            cn = Conexion.conectar();
            pstm = cn.prepareStatement("select convert(datetime,fechaviaje) from tbl_usuarioRegistro");
            rs = pstm.executeQuery();
            ArrayList<Date> listD=new ArrayList();
            while (rs.next()) {
                System.out.println(":"+rs.getDate(1));
                listD.add(rs.getDate(1));                
            }
//            for (Date date : listD) {
//                System.out.println("-"+date);
//            }
        } catch (Exception ex) {
//
        } finally {
            cn.close();
            rs.close();
        }
    }

    public void cargarDatos2() throws SQLException {
        try {
            cn = Conexion.conectar();
            cstmt = cn.prepareCall("{call InsertObject (?,?)}");
            cstmt.setString(1, "insert into estados values('joseeeeee')");
            cstmt.setInt(2, 0);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("Error : " + ex.toString());
        } finally {
            cn.close();
            cstmt.close();
        }
    }

//    public void create(Object x) {
//        Estados estado = null;
//        String preparet = "";
//
//        if (x instanceof Estados) {
//            System.out.println("si es estados");
//            estado=(Estados)x;
//            preparet="insert into Estados values('"+estado.getDescripcion()+"','"++"')";
//            System.out.println("-" +preparet);
//        }
//    }
}
