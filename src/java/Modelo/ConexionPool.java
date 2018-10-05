/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author clopez
 */
public class ConexionPool {

    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String user = "sa";
    public static String password = "EPpal2003";//ya esta listo
    public static String url = "jdbc:sqlserver://192.168.10.1:1433;databaseName=NodumEP";

    public static String query;
    public static Statement stat;
    public static ResultSet rs;
    public Connection con;
    public DataSource dataSource;

    public ConexionPool() {
        conectar();
    }

    public final void conectar() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(50);
        dataSource = basicDataSource;
    }

    public Connection getconecion() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

}