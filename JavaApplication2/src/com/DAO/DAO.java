/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DAO;

import com.models.Employees;
import com.models.ConnecDB;
import com.views.Home;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class DAO {

    ConnecDB cn = new ConnecDB();
    Connection conn = null;

    public DAO() {
        try {
            conn = cn.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // DAO.java
 public ArrayList<Employees> getListEmployeees() {
    ArrayList<Employees> employeeses = new ArrayList<>();
    String sql = "SELECT EmployeeID, MSNV, Name, Phone, Address, Img, Password FROM Employees";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employees employees = new Employees();

            employees.setEmployeeID(rs.getInt("EmployeeID"));
            employees.setMSNV(rs.getString("MSNV"));
            employees.setName(rs.getString("Name"));
            employees.setPhone(rs.getInt("Phone"));
            employees.setAddress(rs.getString("Address"));
            employees.setImg(rs.getString("Img"));
            employees.setPassword(rs.getString("Password"));
            employeeses.add(employees);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
    }
    return employeeses;
}
    
 //   delete Employees
    
    
    public boolean deleteAdmin(int id) {
    String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsDeleted = ps.executeUpdate();
        return rowsDeleted > 0;
    } catch (SQLException ex) {
        Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

}
