/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author nguye
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnecDB {

    public Connection getConnection() throws ClassNotFoundException {
        Connection conn = null;

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=ProjectSem2;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String pass = "sa";
            conn = DriverManager.getConnection(url, user, pass);
          
            if (conn != null) {

                System.out.println("Kết nối thành công!");
            }
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(ConnecDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;

    }
}


