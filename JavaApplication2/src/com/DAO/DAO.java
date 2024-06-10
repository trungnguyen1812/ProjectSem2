/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DAO;

import com.models.Categorys;
import com.models.Employees;
import com.models.ConnecDB;
import com.models.Customers;
import com.models.Order;
import com.models.OrderDetail;
import com.models.Products;
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

//*************************************************MANAGER EMPLOYEES***************************************************************//
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
                employees.setPhone(rs.getString("Phone"));
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

    //DELETE EMPLOYEES 
    public boolean deleteEmployee(int id) {
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
    //*************************************************MANAGER EMPLOYEES***************************************************************//

    //*************************************************MANAGER CATEGORYS***************************************************************//
    public ArrayList<Categorys> getListCategorys() {
        ArrayList<Categorys> categoryses = new ArrayList<>();
        String sql = "SELECT  CategoryName, Description FROM Categorys";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorys categorys = new Categorys();
                categorys.setCategoryName(rs.getString("CategoryName"));
                categorys.setDescription(rs.getString("Description"));
                categoryses.add(categorys);
            }
        } catch (SQLException ex) {

        }
        return categoryses;
    }

    //DELETE EMPLOYEES 
    public boolean deleteCategorys(String name) {
        String sql = "DELETE FROM Categorys WHERE CategoryName = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //*************************************************MANAGER CATEGORYS***************************************************************//
    //*************************************************MANAGER PRODUCTS***************************************************************//
    public ArrayList<Products> getListProducts() {
        ArrayList<Products> products = new ArrayList<>();
        String sql = "SELECT ProductID, Name, CategoryName ,  Price , Descriptions , Img , Quantity FROM Products";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Products pro = new Products();
                pro.setProductID(rs.getInt("ProductID"));
                pro.setName(rs.getString("Name"));
                pro.setCategoryName(rs.getString("CategoryName"));
                pro.setPrice(rs.getInt("Price"));
                pro.setDescriptions(rs.getString("Descriptions"));
                pro.setImg(rs.getString("Img"));
                pro.setQuantity(rs.getInt("Quantity"));
                products.add(pro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    //DELETE EMPLOYEES 
    public boolean deleteProducts(int id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
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

    //*************************************************MANAGER PRODUCTS***************************************************************//
    //*************************************************MANAGER CUSTOMERS***************************************************************//
    public ArrayList<Customers> getListCustomers() {
        ArrayList<Customers> customersList = new ArrayList<>();
        String sql = "SELECT  Name, Phone, Address, MSKH FROM Customers";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customers customers = new Customers();
                customers.setName(rs.getString("MSKH"));
                customers.setPhone(rs.getString("Name"));
                customers.setAddress(rs.getString("Phone"));
                customers.setMSKH(rs.getString("Address"));
                customersList.add(customers);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customersList;
    }

    //DELETE CUSTOMERS 
    public boolean deleteCustomer(String name) {
        String sql = "DELETE FROM Customers WHERE MSKH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //*************************************************MANAGER CUSTOMERS***************************************************************//

//*************************************************MANAGER ORDER***************************************************************//
    public ArrayList<Order> getListOrder() {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT  OderID, MSKH, TotalPrice  FROM Oders";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOderID(rs.getInt("OderID"));
                order.setMSKH(rs.getString("MSKH"));
                order.setTotalPrice(rs.getDouble("TotalPrice"));
                orderList.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orderList;
    }

    //*************************************************MANAGER ORDER***************************************************************//


}
