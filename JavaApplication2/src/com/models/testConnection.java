package com.models;

import java.sql.Connection;
import java.sql.SQLException;

public class testConnection {
    public static void main(String[] args) {
        ConnecDB connector = new ConnecDB();
        
        try {
            Connection connection = connector.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection successful!");
                
                // Perform additional testing or operations here if needed
                
                // Don't forget to close the connection when done
                connection.close();
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Exception occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
