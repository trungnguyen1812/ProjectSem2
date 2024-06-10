/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/Application.java to edit this template
 */
package com.views;

import com.models.Admin;
import javax.swing.*;
import java.awt.*;
import com.models.ConnecDB;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import com.DAO.DAO;
import com.models.Categorys;
import com.models.Customers;
import com.models.Employees;
import com.models.Order;
import com.models.OrderDetail;
import com.models.Products;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nguye
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    Color defaulcolor, clickcolor, white;

    DefaultTableModel model;
    DefaultTableModel modedCategory;
    DefaultTableModel modelChoseProduct;

    // hàm color
    class JPanelGradient extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Đảm bảo gọi phương thức của superclass trước

            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            Color color1 = new Color(52, 143, 80);
            Color color2 = new Color(86, 180, 211);
            GradientPaint gp = new GradientPaint(0, 0, color1, 180, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public void iconApp() {
        initComponents();
        Image icon = new ImageIcon(this.getClass().getResource("/resources/feker-high-resolution-logo.png")).getImage();
        this.setIconImage(icon);
        setLocationRelativeTo(null);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setResizable(false);
    }

    public Home() {
        iconApp();
        defaulcolor = new Color(255, 255, 255);
        clickcolor = new Color(0, 153, 153);
        white = new Color(255, 255, 255);

        this.setLocationRelativeTo(null);

        // funtion Employees
        employees = new DAO().getListEmployeees();
        model = (DefaultTableModel) jtableEmployees.getModel();
        model.setColumnIdentifiers(new Object[]{
            "ID", "MSNV", "Name", "Phone", "Address", "Img", "Password"
        });
        showTableEmployees();
        model.fireTableDataChanged();

        //funtion catogery
        categorys = new DAO().getListCategorys();
        modedCategory = (DefaultTableModel) jtableCategorys.getModel();
        modedCategory.setColumnIdentifiers(new Object[]{
            "CategoryName", "Description"
        });
        showTableCategorys();
        modedCategory.fireTableDataChanged();

        //funtion Products
        productses = new DAO().getListProducts();
        modedProduct = (DefaultTableModel) jTableProducts.getModel();
        modedProduct.setColumnIdentifiers(new Object[]{
            "ID", "Name", "Category", "Img", "Price", "Description", " Quantity"
        });
        showTableProducts();
        modedProduct.fireTableDataChanged();
        loadCategories();

        //funtion Customers
        customersList = new DAO().getListCustomers();
        modedCustomer = (DefaultTableModel) jTableCustomers.getModel();
        modedCustomer.setColumnIdentifiers(new Object[]{
            "MSKH", "Name", "Phone", "Address"
        });
        showTableCustomers(customersList);
        modedCustomer.fireTableDataChanged();

        //funtion oders
        MSKHGenerator mskhGenerator = new MSKHGenerator();
        String randomMSKH = mskhGenerator.generateRandomMSKH();
        txtMSKHCustomer.setText(randomMSKH);
        // show table product in order form 
        productses = new DAO().getListProducts();
        modedProduct = (DefaultTableModel) jTableShowProductFOder.getModel();
        modedProduct.setColumnIdentifiers(new Object[]{
            "ID", "Name", "Category", "Img", "Price", "Description", " Quantity"
        });
        showTableProInOde();
        modedProduct.fireTableDataChanged();

        modelOrder = (DefaultTableModel) jTableChoseProduct.getModel();
        modelOrder.setColumnIdentifiers(new Object[]{
            "ID", "Name", "Price", "Quantity", "Price"
        });
        loadCategories();
        loadCategories1();

        //show table order 
        orderList = new DAO().getListOrder();
        modelChoseProduct = (DefaultTableModel) jTableOrder.getModel();
        modelChoseProduct.setColumnIdentifiers(new Object[]{
            "ID", "MSKH", "Total Price"
        });
        showTableOrder(orderList);
        modelChoseProduct.fireTableDataChanged();
    }

    //********************************************************* MANAGER EMPLOYEES*********************************************************//
    private ArrayList<Employees> employees = new ArrayList<>();

    public void setEmployees(ArrayList<Employees> employees) {
        this.employees = employees;
    }

    public ArrayList<Employees> getEmployees() {
        return employees;
    }

    // SHOW TABLE EMPLOYEES
    public void showTableEmployees() {
        int rowCount = model.getRowCount();
        boolean found;

        for (Employees employees1 : employees) {
            found = false;
            for (int i = 0; i < rowCount; i++) {
                Object valueAt = model.getValueAt(i, 0);
                if (valueAt instanceof Integer) {
                    int id = (int) valueAt;
                    if (employees1.getEmployeeID() == id) {
                        found = true;
                        model.setValueAt(employees1.getEmployeeID(), i, 0);
                        model.setValueAt(employees1.getMSNV(), i, 1);
                        model.setValueAt(employees1.getName(), i, 2);
                        model.setValueAt(employees1.getPhone(), i, 3);
                        model.setValueAt(employees1.getAddress(), i, 4);
                        model.setValueAt(employees1.getImg(), i, 5);
                        model.setValueAt(employees1.getPassword(), i, 6);
                        break;
                    }
                }
            }
            if (!found) {
                model.addRow(new Object[]{
                    employees1.getEmployeeID(),
                    employees1.getMSNV(),
                    employees1.getName(),
                    employees1.getPhone(),
                    employees1.getAddress(),
                    employees1.getImg(),
                    employees1.getPassword()
                });
            }
        }
        model.fireTableDataChanged();
    }

    // SEARCH EMPLOYEES     
    private void searchEmployee() {
        String msnv = inputSearchEmp.getText().trim();

        if (msnv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter MSNV to search.");
            return;
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ConnecDB cn = new ConnecDB();
            conn = cn.getConnection();

            String query = "SELECT * FROM Employees WHERE MSNV LIKE ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + msnv + "%");
            resultSet = statement.executeQuery();

            model.setRowCount(0);

            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("EmployeeID"),
                    resultSet.getString("MSNV"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address"),
                    resultSet.getString("Img"),
                    resultSet.getString("Password")
                };
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No employees found with MSNV: " + msnv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng các tài nguyên trong khối finally
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // DELETE EMPLOYEES
    private void deleteEmployees() {
        // get jtable 
        DefaultTableModel tbModel = (DefaultTableModel) jtableEmployees.getModel();
        //delete row 

        // Kiểm tra xem có hàng được chọn hay không
        if (jtableEmployees.getSelectedRowCount() == 1) {
            // Lấy ID của hàng được chọn
            int selectedRowIndex = jtableEmployees.getSelectedRow();
            int id = (int) tbModel.getValueAt(selectedRowIndex, 0);

            // Gọi phương thức deleteAdmin trong lớp DAO
            DAO dao = new DAO();
            boolean success = dao.deleteEmployee(id);

            if (success) {
                // Xóa thành công
                // Xóa hàng từ bảng JTable
                tbModel.removeRow(selectedRowIndex);

                // Cập nhật lại dữ liệu từ cơ sở dữ liệu
                ArrayList<Employees> updatedEmployeees = dao.getListEmployeees();
                setEmployees(updatedEmployeees);
                showTableEmployees();
            } else {
                // Xóa thất bại
                JOptionPane.showMessageDialog(this, "Failed to delete data from the database.");
            }
        } else {
            if (jtableEmployees.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Table is empty.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a single row for deletion.");
            }
        }
    }

    //SHOW FORM UPDATE  EMPLOYEES
    private void showFormUpdate() {
        int row = jtableEmployees.getSelectedRow();

        jDialogUpdateEmployees.setName(jtableEmployees.getValueAt(row, 0).toString());

        txtMSNV1.setText(jtableEmployees.getValueAt(row, 1).toString());
        txtName1.setText(jtableEmployees.getValueAt(row, 2).toString());
        txtAddress1.setText(jtableEmployees.getValueAt(row, 4).toString());
        txtPhone1.setText(jtableEmployees.getValueAt(row, 3).toString());
        txtPassword1.setText(jtableEmployees.getValueAt(row, 6).toString());

        try {
            String imagePath = jtableEmployees.getValueAt(row, 5).toString();
            // Đọc hình ảnh từ đường dẫn
            BufferedImage originalImg = ImageIO.read(new File(imagePath));
            System.out.println(imagePath);
            // Kích thước của JLabel
            int labelWidth = 290;
            int labelHeight = 273;

            Image scaledImg = originalImg.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

            // Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
            ImageIcon icon = new ImageIcon(scaledImg);

            // Hiển thị hình ảnh trên JLabel
            imageLabel1.setIcon(icon); // imageLabel là JLabel trong mã của bạn
            jDialogUpdateEmployees.setVisible(true);
            imageLabel1.setName(imagePath);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //ADD EMPLOYEES
    private void addEmployee() {
        String MSNV = txtMSNV.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String password = new String(txtPassword.getPassword());
        String imagePath = imageLabel.getName(); // Đường dẫn mặc định là rỗng
        System.out.println(MSNV);

        if (MSNV.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || imagePath == null) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "INSERT INTO Employees (MSNV, Name, Phone, Address, Img, Password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, MSNV);
            pst.setString(2, name);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.setString(5, imagePath);
            pst.setString(6, password);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
//                ResultSet generatedKeys = pst.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    int id = generatedKeys.getInt(1);
//                    DefaultTableModel model = (DefaultTableModel) jtableEmployees.getModel();
//                    model.addRow(new Object[]{id, MSNV, name, phone, address, imagePath, password});
//                }
                txtMSNV.setText("");
                txtName.setText("");
                txtPhone.setText("");
                txtAddress.setText("");
                txtPassword.setText("");
                imageLabel.setIcon(null);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //UPLOAD IMG EMPLOYEES
    private void uploadImgEmployees() {
        JFileChooser jFileChooser = new JFileChooser("D:\\admin\\ProjectSem2\\JavaApplication2\\src\\resources\\avatar");
        jFileChooser.setDialogTitle("Open File");
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            // Kiểm tra xem đường dẫn hình ảnh có rỗng không
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Đọc hình ảnh từ đường dẫn
                    BufferedImage originalImg = ImageIO.read(new File(imagePath));
                    System.out.println(imagePath);
                    // Kích thước của JLabel
                    int labelWidth = imageLabel.getWidth();
                    int labelHeight = imageLabel.getHeight();

                    // Kích thước của hình ảnh gốc
                    int imgWidth = originalImg.getWidth();
                    int imgHeight = originalImg.getHeight();

                    // Tính toán tỉ lệ co giãn
                    double scaleX = (double) labelWidth / imgWidth;
                    double scaleY = (double) labelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY); // Chọn tỉ lệ nhỏ nhất

                    // Tạo hình ảnh mới với kích thước đã điều chỉnh
                    int scaledWidth = (int) (imgWidth * scale);
                    int scaledHeight = (int) (imgHeight * scale);
                    Image scaledImg = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Hiển thị hình ảnh trên JLabel
                    imageLabel.setIcon(icon); // imageLabel là JLabel trong mã của bạn
                    imageLabel.setName(imagePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Xử lý trường hợp không có đường dẫn hình ảnh được chọn
            }
        } else {
            // Xử lý trường hợp người dùng không chọn tệp
        }
    }

    // UPDATE EMPLOYEES
    private void editEmployee() {
        String MSNV = txtMSNV1.getText();
        String name = txtName1.getText();
        String phone = txtPhone1.getText();
        String address = txtAddress1.getText();
        String password = new String(txtPassword1.getPassword());
        String imagePath = imageLabel1.getName();

        System.out.println(imagePath);

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "UPDATE Employees SET  MSNV = ?, Name = ?, Phone = ?, Address = ?, Img = ?, Password = ? WHERE EmployeeID = ?";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, MSNV);
            pst.setString(2, name);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.setString(5, imagePath);
            pst.setString(6, password);
            pst.setInt(7, Integer.parseInt(jDialogUpdateEmployees.getName()));
            int rowsInserted = pst.executeUpdate();

            txtMSNV1.setText("");
            txtName1.setText("");
            txtPhone1.setText("");
            txtAddress1.setText("");
            imageLabel1.setIcon(null);
            txtPassword1.setText("");
            reset();

            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //UPLOAD IMG FORM UPDATE
    private void uploadImgFormUpdateEmployees() {
        JFileChooser jFileChooser = new JFileChooser("D:\\admin\\ProjectSem2\\JavaApplication2\\src\\resources\\avatar");
        jFileChooser.setDialogTitle("Open File");
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            // Kiểm tra xem đường dẫn hình ảnh có rỗng không
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Đọc hình ảnh từ đường dẫn
                    BufferedImage originalImg = ImageIO.read(new File(imagePath));
                    System.out.println(imagePath);
                    // Kích thước của JLabel
                    int labelWidth = imageLabel1.getWidth();
                    int labelHeight = imageLabel1.getHeight();

                    // Kích thước của hình ảnh gốc
                    int imgWidth = originalImg.getWidth();
                    int imgHeight = originalImg.getHeight();

                    // Tính toán tỉ lệ co giãn
                    double scaleX = (double) labelWidth / imgWidth;
                    double scaleY = (double) labelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY); // Chọn tỉ lệ nhỏ nhất

                    // Tạo hình ảnh mới với kích thước đã điều chỉnh
                    int scaledWidth = (int) (imgWidth * scale);
                    int scaledHeight = (int) (imgHeight * scale);
                    Image scaledImg = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Hiển thị hình ảnh trên JLabel
                    imageLabel1.setIcon(icon); // imageLabel là JLabel trong mã của bạn
                    imageLabel1.setName(imagePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Xử lý trường hợp không có đường dẫn hình ảnh được chọn
            }
        } else {
            // Xử lý trường hợp người dùng không chọn tệp
        }
    }

    //RESET TABLE EMPLOYEES
    private void reset() {

        try {
            // Kết nối đến cơ sở dữ liệu
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB

            // Tạo câu truy vấn SQL
            String query = "SELECT * FROM Employees";
            PreparedStatement statement = conn.prepareStatement(query);

            // Thực thi truy vấn và hiển thị kết quả
            ResultSet resultSet = statement.executeQuery();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("EmployeeID"),
                    resultSet.getString("MSNV"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address"),
                    resultSet.getString("Img"),
                    resultSet.getString("Password")
                };

                model.addRow(row);
            }
            // Đóng kết nối
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //********************************************************* MANAGER EMPLOYEES*********************************************************//
    /*
    

    
     */
    //********************************************************* MANAGER CATEGPRY*********************************************************//
    private ArrayList<Categorys> categorys = new ArrayList<>();

    public ArrayList<Categorys> getCategorys() {
        return categorys;
    }

    public void setCategorys(ArrayList<Categorys> categorys) {
        this.categorys = categorys;
    }

    // SHOW TABLE CATEGORY
    public void showTableCategorys() {
        int rowCount = modedCategory.getRowCount();
        boolean found;

        for (Categorys categorys1 : categorys) {
            found = false;
            for (int i = 0; i < rowCount; i++) {
                Object valueAt = modedCategory.getValueAt(i, 0);
                if (valueAt instanceof String) {
                    String Name = (String) valueAt;
                    if (categorys1.getCategoryName().equals(Name)) {
                        found = true;

                        modedCategory.setValueAt(categorys1.getCategoryName(), i, 0);
                        modedCategory.setValueAt(categorys1.getDescription(), i, 1);
                        break;
                    }
                }
            }
            if (!found) {
                modedCategory.addRow(new Object[]{
                    categorys1.getCategoryName(),
                    categorys1.getDescription()
                });
            }
        }
        modedCategory.fireTableDataChanged();
    }

    // SEARCH CATEGORY     
    private void searchCategorys() {
        String name = inputSearchCategory.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter category name to search.");
            return;
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ConnecDB cn = new ConnecDB();
            conn = cn.getConnection();

            String query = "SELECT * FROM Categorys WHERE CategoryName LIKE ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();

            modedCategory.setRowCount(0); // Xóa tất cả các hàng hiện có

            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("CategoryName"),
                    resultSet.getString("Description")
                };
                modedCategory.addRow(row);
            }

            if (modedCategory.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No categories found with name: " + name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng các tài nguyên trong khối finally
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // DELETE CATEGORY
    private void deleteCategorys() {
        // Lấy model của JTable
        modedCategory = (DefaultTableModel) jtableCategorys.getModel();

        // Kiểm tra xem có hàng được chọn hay không
        if (jtableCategorys.getSelectedRowCount() == 1) {
            // Lấy chỉ số của hàng được chọn
            int selectedRowIndex = jtableCategorys.getSelectedRow();
            Object valueAt = modedCategory.getValueAt(selectedRowIndex, 0);

            String name;
            if (valueAt instanceof String) {
                name = (String) valueAt;
            } else if (valueAt instanceof Integer) {
                name = valueAt.toString(); // Chuyển đổi từ Integer sang String
            } else {
                JOptionPane.showMessageDialog(this, "Unexpected data type for CategoryName.");
                return;
            }

            // Gọi phương thức deleteCategorys trong lớp DAO
            DAO dao = new DAO(); // Đảm bảo rằng kết nối đến cơ sở dữ liệu đã được thiết lập đúng cách
            boolean success = dao.deleteCategorys(name);

            if (success) {
                // Xóa thành công
                // Xóa hàng từ bảng JTable
                modedCategory.removeRow(selectedRowIndex);

                // Cập nhật lại dữ liệu từ cơ sở dữ liệu
                ArrayList<Categorys> categoryses = dao.getListCategorys();
                setCategorys(categoryses);
                showTableCategorys();
            } else {
                // Xóa thất bại
                JOptionPane.showMessageDialog(this, "Failed to delete data from the database.");
            }
        } else {
            if (jtableCategorys.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Table is empty.");
            }
        }
    }
    //SHOW FORM UPDATE  CATEGORY

    private void showFormUpdateCategorys() {
        int row = jtableCategorys.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category to update.");
            return;
        }

        if (jtableCategorys.getColumnCount() < 2) {
            JOptionPane.showMessageDialog(this, "Table does not have the correct number of columns.");
            return;
        }

        // Set name attribute of jDialogUpdateCategory for later use
        jDialogUpdateCategory.setName(jtableCategorys.getValueAt(row, 0).toString());

        // Populate the text fields with the selected row's data
        txtCategoryName1.setText(jtableCategorys.getValueAt(row, 0).toString());
        txtCategoryDescription1.setText(jtableCategorys.getValueAt(row, 1).toString());

        // Show the dialog
        jDialogUpdateCategory.setVisible(true);
    }

    //ADD CATEGORY
    private void addCategorys() {
        String CategoryName = txtCategoryName.getText();
        String Description = txtCategoryDescription.getText();

        if (CategoryName.isEmpty() || Description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "INSERT INTO Categorys (CategoryName, Description) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, CategoryName);
            pst.setString(2, Description);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Category added successfully!");
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // Lấy ID được tạo tự động
                    modedCategory = (DefaultTableModel) jtableCategorys.getModel();
                    modedCategory.addRow(new Object[]{CategoryName, Description}); // Thêm CategoryName và Description vào bảng
                }
                txtCategoryName.setText("");
                txtCategoryDescription.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding category!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // UPDATE EMPLOYEES
    private void editCategorys() {
        String newCategoryName = txtCategoryName1.getText();
        String newCategoryDescription = txtCategoryDescription1.getText();

        // Kiểm tra nếu các trường không trống
        if (newCategoryName.isEmpty() || newCategoryDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }

        // Lấy hàng được chọn trong JTable
        int selectedRowIndex = jtableCategorys.getSelectedRow();
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category to edit.");
            return;
        }

        // Kiểm tra cấu trúc bảng
        int columnCount = modedCategory.getColumnCount();
        if (columnCount < 2) {
            JOptionPane.showMessageDialog(this, "Table structure is incorrect.");
            return;
        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "UPDATE Categorys SET CategoryName = ?, Description = ? WHERE CategoryName = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newCategoryName);
            pst.setString(2, newCategoryDescription);

            // Lấy CategoryName từ cột đầu tiên của hàng được chọn
            String oldCategoryName = (String) modedCategory.getValueAt(selectedRowIndex, 0);
            pst.setString(3, oldCategoryName);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Category updated successfully!");
                // Cập nhật lại bảng JTable
                modedCategory.setValueAt(newCategoryName, selectedRowIndex, 0);
                modedCategory.setValueAt(newCategoryDescription, selectedRowIndex, 1);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update category!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            txtCategoryName1.setText("");
            txtCategoryDescription1.setText("");

            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating category!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //RESET TABLE EMPLOYEES
    private void resetTableCategorys() {

        try {
            // Kết nối đến cơ sở dữ liệu
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB

            // Tạo câu truy vấn SQL
            String query = "SELECT * FROM Categorys";
            PreparedStatement statement = conn.prepareStatement(query);

            // Thực thi truy vấn và hiển thị kết quả
            ResultSet resultSet = statement.executeQuery();
            modedCategory.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("CategoryName"),
                    resultSet.getString("Description"),};
                modedCategory.addRow(row);
            }
            // Đóng kết nối
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //********************************************************* MANAGER CATEGPRY*********************************************************//
    /*
    
    
    
     */
    //********************************************************* MANAGER PRODUCTS*********************************************************//
    DefaultTableModel modedProduct;
    private ArrayList<Products> productses = new ArrayList<>();

    public ArrayList<Products> getProductses() {
        return productses;
    }

    public void setProductses(ArrayList<Products> productses) {
        this.productses = productses;
    }

    // SHOW TABLE PRODUCTS
    public void showTableProducts() {
        int rowCount = modedProduct.getRowCount();
        boolean found;
        for (Products products1 : productses) {
            found = false;
            for (int i = 0; i < rowCount; i++) {
                Object valueAt = modedProduct.getValueAt(i, 0);
                if (valueAt instanceof Integer) {
                    int id = (int) valueAt;
                    if (products1.getProductID() == id) { // Replace getProductID() with getId()
                        found = true;
                        modedProduct.setValueAt(products1.getProductID(), i, 0); // Replace getProductID() with getId()
                        modedProduct.setValueAt(products1.getName(), i, 1);
                        modedProduct.setValueAt(products1.getCategoryName(), i, 2);
                        modedProduct.setValueAt(products1.getImg(), i, 3);
                        modedProduct.setValueAt(products1.getPrice(), i, 4);
                        modedProduct.setValueAt(products1.getDescriptions(), i, 5);
                        modedProduct.setValueAt(products1.getQuantity(), i, 6);
                        break;
                    }
                }
            }
            if (!found) {
                modedProduct.addRow(new Object[]{
                    products1.getProductID(), // Replace getProductID() with getId()
                    products1.getName(),
                    products1.getCategoryName(),
                    products1.getImg(),
                    products1.getPrice(),
                    products1.getDescriptions(),
                    products1.getQuantity()
                });
            }
        }
        modedProduct.fireTableDataChanged();
    }

    // SEARCH PRODUCTS     
    private void searchProducts() {
        String name = inputSearchProduct.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Name to search.");
            return;
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ConnecDB cn = new ConnecDB();
            conn = cn.getConnection();

            String query = "SELECT * FROM Products WHERE Name LIKE ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();

            ((DefaultTableModel) jTableProducts.getModel()).setRowCount(0);

            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("ProductID"),
                    resultSet.getString("Name"),
                    resultSet.getString("CategoryName"),
                    resultSet.getString("Img"),
                    resultSet.getInt("Price"),
                    resultSet.getString("Descriptions"),
                    resultSet.getInt("Quantity")
                };
                ((DefaultTableModel) jTableProducts.getModel()).addRow(row);
            }

            if (modedProduct.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No product found with Name: " + name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng các tài nguyên trong khối finally
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // DELETE PRODUCTS
    private void deleteProducts() {
        // get jtable 
        DefaultTableModel tbModel = (DefaultTableModel) jTableProducts.getModel();

        // Kiểm tra xem có hàng được chọn hay không
        if (jTableProducts.getSelectedRowCount() == 1) {
            // Lấy ID của hàng được chọn
            int selectedRowIndex = jTableProducts.getSelectedRow();
            int id = (int) tbModel.getValueAt(selectedRowIndex, 0);

            // Gọi phương thức deleteProduct trong lớp DAO
            DAO dao = new DAO();
            boolean success = dao.deleteProducts(id);

            if (success) {
                // Xóa thành công
                // Xóa hàng từ bảng JTable
                tbModel.removeRow(selectedRowIndex);

                // Cập nhật lại dữ liệu từ cơ sở dữ liệu
                ArrayList<Products> updatedProducts = dao.getListProducts();
                setProductses(updatedProducts);
                showTableProducts();
            } else {
                // Xóa thất bại
                JOptionPane.showMessageDialog(this, "Failed to delete data from the database.");
            }
        } else {
            if (jTableProducts.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Table is empty.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a single row for deletion.");
            }
        }
    }

    //SHOW FORM UPDATE  PRODUCTS
    private void showFormUpdateProducts() {
        int row = jTableProducts.getSelectedRow();

        jDialogFormEditProduct.setName(jTableProducts.getValueAt(row, 0).toString());

        txtNameProduct1.setText(jTableProducts.getValueAt(row, 1).toString());
        jComboBoxCatefory1.setSelectedItem(jTableProducts.getValueAt(row, 2).toString());
        txtPriceProduct1.setText(jTableProducts.getValueAt(row, 4).toString());
        txtDescriptionPro1.setText(jTableProducts.getValueAt(row, 5).toString());
        txtQuantityPro1.setText(jTableProducts.getValueAt(row, 6).toString());

        try {

            String imageProduct = jTableProducts.getValueAt(row, 3).toString();

            // Đọc hình ảnh từ đường dẫn
            BufferedImage originalImg = ImageIO.read(new File(imageProduct));

            // Lấy kích thước của JLabel
            int labelWidth = 150;
            int labelHeight = 146;

            // Tạo hình ảnh mới với kích thước phù hợp với JLabel
            Image scaledImg = originalImg.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

            ImageIcon icon = new ImageIcon(scaledImg);

            // Hiển thị hình ảnh trên JLabel
            jLabelImgProduct1.setIcon(icon); // imageLabel là JLabel trong mã của bạn
            jDialogFormEditProduct.setVisible(true);
            jLabelImgProduct1.setName(imageProduct);
            System.out.println(imageProduct);

        } catch (IOException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // LOAD PRODUCTS 
    private void loadCategories() {
        try {
            // Establish connection to the database
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection();
            // Execute SQL query
            String query = "SELECT CategoryName FROM Categorys";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // Clear the combo box before adding new items
            jComboBoxCatefory.removeAllItems();
            // Add category names to the combo box
            while (resultSet.next()) {
                jComboBoxCatefory.addItem(resultSet.getString("CategoryName"));
            }
            // Close resources
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories from database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategories1() {
        try {
            // Establish connection to the database
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection();
            // Execute SQL query
            String query = "SELECT CategoryName FROM Categorys";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // Clear the combo box before adding new items
            jComboBoxCatefory1.removeAllItems();
            // Add category names to the combo box
            while (resultSet.next()) {
                jComboBoxCatefory1.addItem(resultSet.getString("CategoryName"));
            }
            // Close resources
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories from database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //ADD PRODUCTS 
    private void addProduct() {
        String productName = txtNameProduct.getText().trim();
        String priceProduct = txtPriceProduct.getText().trim();
        String category = (String) jComboBoxCatefory.getSelectedItem();
        String description = txtDescriptionPro.getText().trim();
        String imageProduct = jLabelImgProduct.getName();
        int quantity = Integer.parseInt(txtQuantityPro.getText().trim());
        if (productName.isEmpty() || priceProduct.isEmpty() || category == null || description.isEmpty() || imageProduct == null || quantity == 0) {
            JOptionPane.showMessageDialog(this, "Please enter all data!");
            return;
        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection();
            String sql = "INSERT INTO Products (Name, CategoryName, Img, Price, Descriptions , Quantity) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, productName);
            pst.setString(2, category);
            pst.setString(3, imageProduct);
            pst.setFloat(4, Float.parseFloat(priceProduct)); // Sử dụng kiểu dữ liệu float
            pst.setString(5, description);
            pst.setInt(6, quantity);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                txtNameProduct.setText("");
                txtPriceProduct.setText("");
                jComboBoxCatefory.setSelectedIndex(0);
                txtDescriptionPro.setText("");
                jLabelImgProduct.setIcon(null);
                txtQuantityPro.setText("");
                resetTableProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //UPLOAD IMG PRODUCTS
    private void uploadImgProducts() {
        JFileChooser jFileChooser = new JFileChooser("D:\\admin\\ProjectSem2\\JavaApplication2\\src\\resources\\ProductsImg");
        jFileChooser.setDialogTitle("Open File");
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            String imagePathPro = selectedFile.getAbsolutePath();

            // Kiểm tra xem đường dẫn hình ảnh có rỗng không
            if (imagePathPro != null && !imagePathPro.isEmpty()) {
                try {
                    // Đọc hình ảnh từ đường dẫn
                    BufferedImage originalImg = ImageIO.read(new File(imagePathPro));
                    System.out.println(imagePathPro);
                    // Kích thước của JLabel
                    int labelWidth = jLabelImgProduct.getWidth();
                    int labelHeight = jLabelImgProduct.getHeight();

                    // Kích thước của hình ảnh gốc
                    int imgWidth = originalImg.getWidth();
                    int imgHeight = originalImg.getHeight();

                    // Tính toán tỉ lệ co giãn
                    double scaleX = (double) labelWidth / imgWidth;
                    double scaleY = (double) labelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY); // Chọn tỉ lệ nhỏ nhất

                    // Tạo hình ảnh mới với kích thước đã điều chỉnh
                    int scaledWidth = (int) (imgWidth * scale);
                    int scaledHeight = (int) (imgHeight * scale);
                    Image scaledImg = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Hiển thị hình ảnh trên JLabel
                    jLabelImgProduct.setIcon(icon); // imageLabel là JLabel trong mã của bạn
                    jLabelImgProduct.setName(imagePathPro);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Xử lý trường hợp không có đường dẫn hình ảnh được chọn
            }
        } else {
            // Xử lý trường hợp người dùng không chọn tệp
        }
    }

    // UPDATE PRODUCTS
    private void editProducts() {
        String productName = txtNameProduct1.getText().trim();
        String priceProduct = txtPriceProduct1.getText().trim();
        String category = (String) jComboBoxCatefory1.getSelectedItem();
        String description = txtDescriptionPro1.getText().trim();
        String imageProduct = jLabelImgProduct1.getName();
        int quantity = Integer.parseInt(txtQuantityPro1.getText().trim());
//        if (productName.isEmpty() || priceProduct.isEmpty() || category == null || description.isEmpty() || imageProduct == null || quantity == 0) {
//            JOptionPane.showMessageDialog(this, "Please enter all data!");
//            return;
//        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection();
            String sql = "UPDATE  Products SET  Name = ?, CategoryName =?, Img =? , Price = ?, Descriptions =? , Quantity =? WHERE ProductID =? ";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, productName);
            pst.setString(2, category);
            pst.setString(3, imageProduct);
            pst.setFloat(4, Float.parseFloat(priceProduct)); // Sử dụng kiểu dữ liệu float
            pst.setString(5, description);
            pst.setInt(6, quantity);
            pst.setInt(7, Integer.parseInt(jDialogFormEditProduct.getName()));
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                txtNameProduct1.setText("");
                txtPriceProduct1.setText("");
                jComboBoxCatefory1.setSelectedIndex(0);
                txtDescriptionPro1.setText("");
                jLabelImgProduct1.setIcon(null);
                txtQuantityPro1.setText("");
                resetTableProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //UPLOAD IMG FORM UPDATE PRODUCTS
    private void uploadImgFormUpdateProducts() {
        JFileChooser jFileChooser = new JFileChooser("D:\\admin\\ProjectSem2\\JavaApplication2\\src\\resources\\ProductsImg");
        jFileChooser.setDialogTitle("Open File");
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            // Kiểm tra xem đường dẫn hình ảnh có rỗng không
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Đọc hình ảnh từ đường dẫn
                    BufferedImage originalImg = ImageIO.read(new File(imagePath));
                    System.out.println(imagePath);
                    // Kích thước của JLabel
                    int labelWidth = jLabelImgProduct1.getWidth();
                    int labelHeight = jLabelImgProduct1.getHeight();

                    // Kích thước của hình ảnh gốc
                    int imgWidth = originalImg.getWidth();
                    int imgHeight = originalImg.getHeight();

                    // Tính toán tỉ lệ co giãn
                    double scaleX = (double) labelWidth / imgWidth;
                    double scaleY = (double) labelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY); // Chọn tỉ lệ nhỏ nhất

                    // Tạo hình ảnh mới với kích thước đã điều chỉnh
                    int scaledWidth = (int) (imgWidth * scale);
                    int scaledHeight = (int) (imgHeight * scale);
                    Image scaledImg = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Hiển thị hình ảnh trên JLabel
                    jLabelImgProduct1.setIcon(icon); // imageLabel là JLabel trong mã của bạn
                    jLabelImgProduct1.setName(imagePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Xử lý trường hợp không có đường dẫn hình ảnh được chọn
            }
        } else {
            // Xử lý trường hợp người dùng không chọn tệp
        }
    }

    //RESET TABLE PRODUCTS
    private void resetTableProducts() {
        try {
            // Kết nối đến cơ sở dữ liệu
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB

            // Tạo câu truy vấn SQL
            String query = "SELECT * FROM Products";
            PreparedStatement statement = conn.prepareStatement(query);

            // Thực thi truy vấn và hiển thị kết quả
            ResultSet resultSet = statement.executeQuery();

            // Xóa dữ liệu cũ trong bảng
            ((DefaultTableModel) jTableProducts.getModel()).setRowCount(0);

            // Thêm dữ liệu mới vào bảng
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("ProductID"),
                    resultSet.getString("Name"),
                    resultSet.getString("CategoryName"),
                    resultSet.getString("Img"),
                    resultSet.getInt("Price"),
                    resultSet.getString("Descriptions"),
                    resultSet.getInt("Quantity")
                };
                ((DefaultTableModel) jTableProducts.getModel()).addRow(row);
            }

            // Đóng kết nối
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //********************************************************* MANAGER PRODUCTS*********************************************************//
    //********************************************************* MANAGER CUSTOMER*********************************************************//
    DefaultTableModel modedCustomer;
    private ArrayList<Customers> customersList = new ArrayList<>();

    public ArrayList<Customers> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(ArrayList<Customers> customersList) {
        this.customersList = customersList;
    }

    // SHOW TABLE CUSTOMER
    public void showTableCustomers(ArrayList<Customers> customersList) {
        DefaultTableModel modedCustomer = (DefaultTableModel) jTableCustomers.getModel();
        modedCustomer.setRowCount(0); // Xóa tất cả dữ liệu cũ

        for (Customers customers : customersList) {
            modedCustomer.addRow(new Object[]{
                customers.getName(),
                customers.getPhone(),
                customers.getAddress(),
                customers.getMSKH()
            });
        }
    }

    // SEARCH CUSTOMER     
    private void searchCustomers() {
        String name = intputSearchCustomer.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter category name to search.");
            return;
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ConnecDB cn = new ConnecDB();
            conn = cn.getConnection();

            String query = "SELECT * FROM Customers WHERE Name LIKE ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();

            modedCustomer.setRowCount(0); // Xóa tất cả các hàng hiện có

            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("MSKH"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address"),};
                modedCustomer.addRow(row);
            }

            if (modedCustomer.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No categories found with name: " + name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng các tài nguyên trong khối finally
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // DELETE CUSTOMER
    private void deleteCustomers() {
        // Lấy model của JTable
        modedCustomer = (DefaultTableModel) jTableCustomers.getModel();

        // Kiểm tra xem có hàng được chọn hay không
        if (jTableCustomers.getSelectedRowCount() == 1) {
            // Lấy chỉ số của hàng được chọn
            int selectedRowIndex = jTableCustomers.getSelectedRow();
            Object valueAt = jTableCustomers.getValueAt(selectedRowIndex, 0);

            String name;
            if (valueAt instanceof String) {
                name = (String) valueAt;
            } else if (valueAt instanceof Integer) {
                name = valueAt.toString(); // Chuyển đổi từ Integer sang String
            } else {
                JOptionPane.showMessageDialog(this, "Unexpected data type for Customer name.");
                return;
            }

            // Gọi phương thức deleteCategorys trong lớp DAO
            DAO dao = new DAO(); // Đảm bảo rằng kết nối đến cơ sở dữ liệu đã được thiết lập đúng cách
            boolean success = dao.deleteCustomer(name);

            if (success) {
                // Xóa thành công
                // Xóa hàng từ bảng JTable
                modedCustomer.removeRow(selectedRowIndex);

                // Cập nhật lại dữ liệu từ cơ sở dữ liệu
                ArrayList<Customers> customerses = dao.getListCustomers();
                setCustomersList(customerses);
                showTableCustomers(customersList);
            } else {
                // Xóa thất bại
                JOptionPane.showMessageDialog(this, "Failed to delete data from the database.");
            }
        } else {
            if (jtableCategorys.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Table is empty.");
            }
        }
    }

    //SHOW FORM UPDATE  CUSTOMER
    private void showFormUpdateCustomers() {
        int row = jTableCustomers.getSelectedRow();
        if (row >= 0) { // Kiểm tra xem có dòng nào được chọn hay không
            jDialogFormEditCustomer.setName("FORM UPDATE"); // Đặt tiêu đề cho form

            String mskh = jTableCustomers.getValueAt(row, 0).toString();
            txtMSKHCustomerEdit.setText(mskh);

            String name = jTableCustomers.getValueAt(row, 1).toString();
            txtNameCustomerEdit.setText(name);

            String phone = jTableCustomers.getValueAt(row, 2).toString();
            txtPhoneCustomerEdit.setText(phone);

            String address = jTableCustomers.getValueAt(row, 3).toString();
            txtAddressCustomer1.setText(address);

            jDialogFormEditCustomer.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.");
        }
    }

    //RANDOM MSKH
    public class MSKHGenerator {

        private static final Set<String> existingMSKHs = new HashSet<>();
        private static final Random random = new Random();

        public static String generateRandomMSKH() {
            String mskh;
            do {
                mskh = generateMSKHCandidate();
            } while (!existingMSKHs.add(mskh));
            return mskh;
        }

        private static String generateMSKHCandidate() {
            StringBuilder sb = new StringBuilder();
            // Generate 2 uppercase letters
            for (int i = 0; i < 2; i++) {
                char randomChar = (char) (random.nextInt(26) + 'A');
                sb.append(randomChar);
            }
            // Generate 4 digits
            for (int i = 0; i < 4; i++) {
                int randomDigit = random.nextInt(10);
                sb.append(randomDigit);
            }
            return sb.toString();
        }
    }

    public void resetMSKH() {
        // Generate a new random MSKH and set it in the text field
        String randomMSKH = MSKHGenerator.generateRandomMSKH();
        txtMSKHCustomer.setText(randomMSKH);
    }

    //ADD CUSTOMER
    private void addCustomers() {
        String Name = txtNameCustomer.getText();
        String Phone = txtPhoneCustomer.getText();
        String Address = txtAddressCustomer.getText();
        String MSKH = txtMSKHCustomer.getText();
        if (Name.isEmpty() || Phone.isEmpty() || Address.isEmpty() || MSKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }

        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "INSERT INTO Customers (Name, Phone , Address , MSKH) VALUES (?, ?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, Name);
            pst.setString(2, Phone);
            pst.setString(3, Address);
            pst.setString(4, MSKH);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Customer  added successfully!");
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    modedCustomer = (DefaultTableModel) jTableCustomers.getModel();
                    modedCustomer.addRow(new Object[]{MSKH, Name, Phone, Address});
                }
                txtNameCustomer.setText("");
                txtPhoneCustomer.setText("");
                txtAddressCustomer.setText("");
                txtMSKHCustomer.setText("");

            } else {
                JOptionPane.showMessageDialog(this, "Failed to add customer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
            resetMSKH();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // UPDATE CUSTOMER
    private void editCustomers() {
        String Name = txtNameCustomer.getText();
        String Phone = txtPhoneCustomer.getText();
        String Address = txtAddressCustomer.getText();
        String MSKH = txtMSKHCustomer.getText();
        // Kiểm tra nếu các trường không trống
        if (Name.isEmpty() || Phone.isEmpty() || Address.isEmpty() || MSKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }
        // Lấy hàng được chọn trong JTable
        int selectedRowIndex = jTableCustomers.getSelectedRow();
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
            return;
        }
        // Kiểm tra cấu trúc bảng
        int columnCount = modedCustomer.getColumnCount();
        if (columnCount < 4) {
            JOptionPane.showMessageDialog(this, "Table structure is incorrect.");
            return;
        }
        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "UPDATE Customers SET Name = ?, Phone = ?, Address = ? WHERE MSKH = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, Name);
            pst.setString(2, Phone);
            pst.setString(3, Address);
            pst.setString(4, MSKH);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully!");
                // Cập nhật lại bảng JTable
                modedCustomer.setValueAt(Name, selectedRowIndex, 0);
                modedCustomer.setValueAt(Phone, selectedRowIndex, 1);
                modedCustomer.setValueAt(Address, selectedRowIndex, 2);
                modedCustomer.setValueAt(MSKH, selectedRowIndex, 3);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update customer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating customer!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //RESET TABLE CUSTOMER
    private void resetTableCustomers() {

        try {
            // Kết nối đến cơ sở dữ liệu
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB

            // Tạo câu truy vấn SQL
            String query = "SELECT * FROM Customers";
            PreparedStatement statement = conn.prepareStatement(query);

            // Thực thi truy vấn và hiển thị kết quả
            ResultSet resultSet = statement.executeQuery();
            modedCustomer.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("MSKH"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address")
                };
                modedCustomer.addRow(row);
            }
            // Đóng kết nối
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //SHOW FORM ODER FOR CUSTOMER
    private void ShowFormOder() {
        int row = jTableCustomers.getSelectedRow();
        if (row >= 0) { // Kiểm tra xem có dòng nào được chọn hay không
            jDialogFormEditCustomer.setName("FORM ODER"); // Đặt tiêu đề cho form

            mskh = jTableCustomers.getValueAt(row, 0).toString();
            txtShowMSKH.setText(mskh);

            String name = jTableCustomers.getValueAt(row, 1).toString();
            txtShowName.setText(name);

            // Load lại dữ liệu
            showTableProInOde();

            // Hiển thị JDialog
            jDialogShowOder.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.");
        }
    }

    //SHOW TABLE IN ODER FORM
    private void showTableProInOde() {
        int rowCount = modedProduct.getRowCount();
        boolean found;
        for (Products products1 : productses) {
            found = false;
            for (int i = 0; i < rowCount; i++) {
                Object valueAt = modedProduct.getValueAt(i, 0);
                if (valueAt instanceof Integer) {
                    int id = (int) valueAt;
                    if (products1.getProductID() == id) { // Replace getProductID() with getId()
                        found = true;
                        modedProduct.setValueAt(products1.getProductID(), i, 0); // Replace getProductID() with getId()
                        modedProduct.setValueAt(products1.getName(), i, 1);
                        modedProduct.setValueAt(products1.getCategoryName(), i, 2);
                        modedProduct.setValueAt(products1.getImg(), i, 3);
                        modedProduct.setValueAt(products1.getPrice(), i, 4);
                        modedProduct.setValueAt(products1.getDescriptions(), i, 5);
                        modedProduct.setValueAt(products1.getQuantity(), i, 6);
                        break;
                    }
                }
            }
            if (!found) {
                modedProduct.addRow(new Object[]{
                    products1.getProductID(), // Replace getProductID() with getId()
                    products1.getName(),
                    products1.getCategoryName(),
                    products1.getImg(),
                    products1.getPrice(),
                    products1.getDescriptions(),
                    products1.getQuantity()
                });
            }
        }
        modedProduct.fireTableDataChanged();
    }

    private void resetTableProInOde() {
        try {
            // Kết nối đến cơ sở dữ liệu
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB

            // Tạo câu truy vấn SQL
            String query = "SELECT * FROM Products";
            PreparedStatement statement = conn.prepareStatement(query);

            // Thực thi truy vấn và hiển thị kết quả
            ResultSet resultSet = statement.executeQuery();

            // Lấy mô hình của jTableProducts
            DefaultTableModel ResetTableProInOrder = (DefaultTableModel) jTableShowProductFOder.getModel();

            // Xóa dữ liệu cũ trong bảng
            ResetTableProInOrder.setRowCount(0);

            // Thêm dữ liệu mới vào bảng
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("ProductID"),
                    resultSet.getString("Name"),
                    resultSet.getString("CategoryName"),
                    resultSet.getString("Img"),
                    resultSet.getDouble("Price"), // Nếu cột Price là kiểu double
                    resultSet.getString("Descriptions"),
                    resultSet.getInt("Quantity")
                };
                ResetTableProInOrder.addRow(row);
            }

            // Đóng kết nối
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Class not found: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //********************************************************* MANAGER CUSTOMER*********************************************************//
    //********************************************************* MANAGER ORDER*********************************************************//
    DefaultTableModel modelOrder;
    private ArrayList<Order> orderList = new ArrayList<>();

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    //SHOW TABLE ORDER
    public void showTableOrder(ArrayList<Order> orderList) {
        modelChoseProduct = (DefaultTableModel) jTableOrder.getModel();
        modelChoseProduct.setRowCount(0); // Xóa tất cả dữ liệu cũ

        for (Order order : orderList) {
            modelChoseProduct.addRow(new Object[]{
                order.getOderID(),
                order.getMSKH(),
                order.getTotalPrice(),});
        }
    }
    //SHOW FORM  ORDERDETAIL

    private void showFormOrderDetail() {
        int row = jTableOrder.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order to view details.");
            return;
        }

        // Giả sử cột đầu tiên chứa ID đơn hàng
        int orderId = Integer.parseInt(jTableOrder.getValueAt(row, 0).toString());

        // Truy vấn chi tiết đơn hàng từ cơ sở dữ liệu
        ArrayList<OrderDetail> orderDetails = getOrderDetailsByOrderId(orderId);

        // Hiển thị chi tiết đơn hàng trong jTableOrderDetail
        showOrderDetailsInTable(orderDetails);

        //Hien thi Name MSKH 
        ShowTTOrderDetail();
        // Hiển thị JDialog
        jDialogShowOrderDetail.setVisible(true);
    }

    public ArrayList<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        // Kết nối cơ sở dữ liệu và truy vấn chi tiết đơn hàng
        try {
            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection();
            String sql = "SELECT * FROM OrderDetail WHERE OderID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOderID(rs.getInt("OderID"));
                orderDetail.setProductID(rs.getInt("ProductID"));
                orderDetail.setPrice(rs.getDouble("Price"));
                orderDetail.setQuantity(rs.getInt("Quantity"));
                orderDetails.add(orderDetail);  // Thêm đúng đối tượng vào danh sách
            }

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderDetails;
    }

    //SHOW TABLE ORDERDETAIL
    public void showOrderDetailsInTable(ArrayList<OrderDetail> orderDetails) {
        DefaultTableModel modelOrderDetail = (DefaultTableModel) showOrderDetai1.getModel();
        modelOrderDetail.setRowCount(0); // Xóa tất cả dữ liệu cũ

        for (OrderDetail detail : orderDetails) {
            modelOrderDetail.addRow(new Object[]{
                detail.getOderID(),
                detail.getProductID(),
                detail.getPrice(),
                detail.getQuantity()
            });
        }
    }

    //SHOW NAME MSKH FORM ORDERDETAIL
    private void ShowTTOrderDetail() {
        int row = jTableOrder.getSelectedRow();
        if (row >= 0) { // Kiểm tra xem có dòng nào được chọn hay không
            jDialogShowOrderDetail.setName("FORM ODERDETAIL"); // Đặt tiêu đề cho form

            mskh = jTableOrder.getValueAt(row, 0).toString();
            txtShowMSKH1.setText(mskh);

            String name = jTableCustomers.getValueAt(row, 1).toString();
            txtShowName1.setText(name);

        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.");
        }
    }

    //********************************************************* MANAGER ORDER*********************************************************//
    // chuc nang oders 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogAddEmployee = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMSNV = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        btnAddEmployees = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jButtonUploaImg = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jDialogUpdateEmployees = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtMSNV1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtName1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtAddress1 = new javax.swing.JTextField();
        btnEditEmployees1 = new javax.swing.JButton();
        btnClose1 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        imageLabel1 = new javax.swing.JLabel();
        txtPassword1 = new javax.swing.JPasswordField();
        jButtonUploaImg1 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtPhone1 = new javax.swing.JTextField();
        txtEmployeeID = new javax.swing.JLabel();
        jDialogAddCategory = new javax.swing.JDialog();
        jPanel24 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtCategoryName = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCategoryDescription = new javax.swing.JTextArea();
        jDialogUpdateCategory = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtCategoryName1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCategoryDescription1 = new javax.swing.JTextArea();
        jDialogFormAddProduct = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtNameProduct = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtPriceProduct = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jComboBoxCatefory = new javax.swing.JComboBox<>();
        jPanel28 = new javax.swing.JPanel();
        jLabelImgProduct = new javax.swing.JLabel();
        btnUploadProduct = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtDescriptionPro = new javax.swing.JTextArea();
        jLabel48 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        txtQuantityPro = new javax.swing.JTextField();
        jDialogFormEditProduct = new javax.swing.JDialog();
        jPanel31 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        txtNameProduct1 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        txtPriceProduct1 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jComboBoxCatefory1 = new javax.swing.JComboBox<>();
        jPanel32 = new javax.swing.JPanel();
        jLabelImgProduct1 = new javax.swing.JLabel();
        btnUploadProduct1 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtDescriptionPro1 = new javax.swing.JTextArea();
        jLabel71 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel75 = new javax.swing.JLabel();
        txtQuantityPro1 = new javax.swing.JTextField();
        jDialogFormAddCustomer = new javax.swing.JDialog();
        jPanel29 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtNameCustomer = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtPhoneCustomer = new javax.swing.JTextField();
        txtMSKHCustomer = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtAddressCustomer = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jDialogFormEditCustomer = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtMSKHCustomerEdit = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtNameCustomerEdit = new javax.swing.JTextField();
        txtPhoneCustomerEdit = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAddressCustomer1 = new javax.swing.JTextArea();
        jLabel61 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jDialogShowOder = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableShowProductFOder = new javax.swing.JTable();
        btnChoseProduct = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jpanel34 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        txtShowMSKH = new javax.swing.JLabel();
        txtShowName = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTableChoseProduct = new javax.swing.JTable();
        btnDeleteProductChose = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        btnBuy = new javax.swing.JButton();
        showTotalPrice = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jDialogQuantity = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        txtEnterQuantity = new javax.swing.JTextField();
        btnEnterQuantity = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jDialogShowOrderDetail = new javax.swing.JDialog();
        jPanel37 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        txtShowMSKH1 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        txtShowName1 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        showOrderDetai1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtHome = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtAdmin = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCategoy = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Home = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Employees = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        inputSearchEmp = new javax.swing.JTextField();
        btnSearchEmp = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableEmployees = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        Categorys = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        buttonDeleteCategory = new javax.swing.JButton();
        buttonAddCategory = new javax.swing.JButton();
        buttonEditCategory = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtableCategorys = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        inputSearchCategory = new javax.swing.JTextField();
        buttonResetCategory = new javax.swing.JButton();
        buttonSearchCategory = new javax.swing.JButton();
        Products = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableProducts = new javax.swing.JTable();
        btnAddProduct = new javax.swing.JButton();
        btnFormEditProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        inputSearchProduct = new javax.swing.JTextField();
        btnSearchProduct = new javax.swing.JButton();
        btnResetProduct = new javax.swing.JButton();
        Customers = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableCustomers = new javax.swing.JTable();
        btnAddCustomer = new javax.swing.JButton();
        btnFormEditCustomer = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        intputSearchCustomer = new javax.swing.JTextField();
        btnSearchCustomer = new javax.swing.JButton();
        btnResetCustomer = new javax.swing.JButton();
        btnOder = new javax.swing.JButton();
        Oders = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableOrder = new javax.swing.JTable();
        btnAddCustomer1 = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        intputSearchCustomer1 = new javax.swing.JTextField();
        btnSearchCustomer1 = new javax.swing.JButton();
        btnResetCustomer1 = new javax.swing.JButton();

        jDialogAddEmployee.setTitle("Form Add Employees");
        jDialogAddEmployee.setMinimumSize(new java.awt.Dimension(847, 735));
        jDialogAddEmployee.setModal(true);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("FORM");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Name:");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel18.setText("MSNV: ");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel20.setText("Address:");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel21.setText("Phone;");

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel22.setText("Password:");

        btnAddEmployees.setBackground(new java.awt.Color(51, 204, 0));
        btnAddEmployees.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAddEmployees.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmployees.setText("+Add");
        btnAddEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeesActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(102, 102, 102));
        btnClose.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        txtPassword.setPreferredSize(new java.awt.Dimension(74, 26));

        jButtonUploaImg.setBackground(new java.awt.Color(0, 0, 0));
        jButtonUploaImg.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonUploaImg.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUploaImg.setText("Upload Image ");
        jButtonUploaImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUploaImgActionPerformed(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(0, 204, 255));
        jLabel23.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 204, 204));
        jLabel23.setText("ADD");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMSNV)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName)
                    .addComponent(txtPhone)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(btnClose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddEmployees))
                            .addComponent(jButtonUploaImg, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105))))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(358, 358, 358)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel23))
                .addGap(50, 50, 50)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(txtMSNV, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20))
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUploaImg))
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClose)
                            .addComponent(btnAddEmployees))))
                .addContainerGap(184, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogAddEmployeeLayout = new javax.swing.GroupLayout(jDialogAddEmployee.getContentPane());
        jDialogAddEmployee.getContentPane().setLayout(jDialogAddEmployeeLayout);
        jDialogAddEmployeeLayout.setHorizontalGroup(
            jDialogAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogAddEmployeeLayout.setVerticalGroup(
            jDialogAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogUpdateEmployees.setTitle("Form Update Employee");
        jDialogUpdateEmployees.setMinimumSize(new java.awt.Dimension(847, 735));
        jDialogUpdateEmployees.setModal(true);
        jDialogUpdateEmployees.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jDialogUpdateEmployeesComponentShown(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel24.setText("FORM");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel25.setText("Name:");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel26.setText("MSNV: ");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel27.setText("Address:");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel28.setText("Phone;");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel29.setText("Password:");

        btnEditEmployees1.setBackground(new java.awt.Color(51, 204, 0));
        btnEditEmployees1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEditEmployees1.setForeground(new java.awt.Color(255, 255, 255));
        btnEditEmployees1.setText("Edit");
        btnEditEmployees1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEmployees1ActionPerformed(evt);
            }
        });

        btnClose1.setBackground(new java.awt.Color(102, 102, 102));
        btnClose1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnClose1.setForeground(new java.awt.Color(255, 255, 255));
        btnClose1.setText("Close");
        btnClose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose1ActionPerformed(evt);
            }
        });

        jPanel23.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        txtPassword1.setPreferredSize(new java.awt.Dimension(74, 26));
        txtPassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassword1ActionPerformed(evt);
            }
        });

        jButtonUploaImg1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonUploaImg1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonUploaImg1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUploaImg1.setText("Upload Image ");
        jButtonUploaImg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUploaImg1ActionPerformed(evt);
            }
        });

        jLabel30.setBackground(new java.awt.Color(0, 204, 255));
        jLabel30.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 204, 204));
        jLabel30.setText("UPDATE");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMSNV1)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName1)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress1)
                    .addComponent(txtPassword1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(txtPhone1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(btnClose1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditEmployees1))
                            .addComponent(jButtonUploaImg1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74))))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(358, 358, 358)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel30)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(txtEmployeeID)))
                .addGap(40, 40, 40)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(txtMSNV1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addGap(12, 12, 12)
                        .addComponent(txtPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27))
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUploaImg1))
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClose1)
                            .addComponent(btnEditEmployees1))))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogUpdateEmployeesLayout = new javax.swing.GroupLayout(jDialogUpdateEmployees.getContentPane());
        jDialogUpdateEmployees.getContentPane().setLayout(jDialogUpdateEmployeesLayout);
        jDialogUpdateEmployeesLayout.setHorizontalGroup(
            jDialogUpdateEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogUpdateEmployeesLayout.setVerticalGroup(
            jDialogUpdateEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogAddCategory.setTitle("Form Add Category");
        jDialogAddCategory.setMinimumSize(new java.awt.Dimension(422, 500));
        jDialogAddCategory.setModal(true);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 204, 255));
        jLabel33.setText("ADD");

        jLabel34.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel34.setText("FORM ");

        jLabel35.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel35.setText("Name: ");

        jLabel36.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel36.setText("Description:");

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 204, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtCategoryDescription.setColumns(20);
        txtCategoryDescription.setRows(5);
        jScrollPane1.setViewportView(txtCategoryDescription);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCategoryName)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addGap(72, 72, 72)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogAddCategoryLayout = new javax.swing.GroupLayout(jDialogAddCategory.getContentPane());
        jDialogAddCategory.getContentPane().setLayout(jDialogAddCategoryLayout);
        jDialogAddCategoryLayout.setHorizontalGroup(
            jDialogAddCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogAddCategoryLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialogAddCategoryLayout.setVerticalGroup(
            jDialogAddCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogUpdateCategory.setTitle("Form Update Categorys");
        jDialogUpdateCategory.setMinimumSize(new java.awt.Dimension(422, 500));
        jDialogUpdateCategory.setModal(true);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 204, 255));
        jLabel37.setText("UPDATE");

        jLabel38.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel38.setText("FORM ");

        jLabel39.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel39.setText("Name: ");

        jLabel40.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel40.setText("Description:");

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 204, 0));
        jButton5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Edit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtCategoryDescription1.setColumns(20);
        txtCategoryDescription1.setRows(5);
        jScrollPane4.setViewportView(txtCategoryDescription1);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCategoryName1)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38))
                .addGap(72, 72, 72)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCategoryName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogUpdateCategoryLayout = new javax.swing.GroupLayout(jDialogUpdateCategory.getContentPane());
        jDialogUpdateCategory.getContentPane().setLayout(jDialogUpdateCategoryLayout);
        jDialogUpdateCategoryLayout.setHorizontalGroup(
            jDialogUpdateCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogUpdateCategoryLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialogUpdateCategoryLayout.setVerticalGroup(
            jDialogUpdateCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogFormAddProduct.setTitle("Form Add Product");
        jDialogFormAddProduct.setMinimumSize(new java.awt.Dimension(696, 634));
        jDialogFormAddProduct.setModal(true);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setMinimumSize(new java.awt.Dimension(696, 634));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("FORM");

        jLabel43.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 204, 255));
        jLabel43.setText("ADD");

        txtNameProduct.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Name");

        jLabel45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Price");

        txtPriceProduct.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Category");

        jComboBoxCatefory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCateforyActionPerformed(evt);
            }
        });

        jPanel28.setBackground(new java.awt.Color(204, 204, 204));

        jLabelImgProduct.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImgProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImgProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );

        btnUploadProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnUploadProduct.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnUploadProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnUploadProduct.setText("Updload");
        btnUploadProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadProductActionPerformed(evt);
            }
        });

        txtDescriptionPro.setColumns(20);
        txtDescriptionPro.setRows(5);
        txtDescriptionPro.setPreferredSize(new java.awt.Dimension(100, 84));
        jScrollPane7.setViewportView(txtDescriptionPro);

        jLabel48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Description");

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(102, 255, 0));
        jButton11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Add");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setText("Quantity");

        txtQuantityPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNameProduct)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPriceProduct)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxCatefory, 0, 228, Short.MAX_VALUE)
                            .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantityPro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUploadProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel27Layout.createSequentialGroup()
                                        .addComponent(jButton7)
                                        .addGap(376, 376, 376)
                                        .addComponent(jButton11)))))
                        .addGap(77, 77, 77))))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel43))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnUploadProduct))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantityPro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxCatefory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton7))
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout jDialogFormAddProductLayout = new javax.swing.GroupLayout(jDialogFormAddProduct.getContentPane());
        jDialogFormAddProduct.getContentPane().setLayout(jDialogFormAddProductLayout);
        jDialogFormAddProductLayout.setHorizontalGroup(
            jDialogFormAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogFormAddProductLayout.setVerticalGroup(
            jDialogFormAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogFormEditProduct.setMinimumSize(new java.awt.Dimension(696, 634));
        jDialogFormEditProduct.setModal(true);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setMinimumSize(new java.awt.Dimension(696, 634));

        jLabel66.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("FORM");

        jLabel67.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 204, 255));
        jLabel67.setText("EDIT");

        txtNameProduct1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel68.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("Name");

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Price");

        txtPriceProduct1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel70.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Category");

        jComboBoxCatefory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCatefory1ActionPerformed(evt);
            }
        });

        jPanel32.setBackground(new java.awt.Color(204, 204, 204));

        jLabelImgProduct1.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImgProduct1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImgProduct1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );

        btnUploadProduct1.setBackground(new java.awt.Color(0, 0, 0));
        btnUploadProduct1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnUploadProduct1.setForeground(new java.awt.Color(255, 255, 255));
        btnUploadProduct1.setText("Updload");
        btnUploadProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadProduct1ActionPerformed(evt);
            }
        });

        txtDescriptionPro1.setColumns(20);
        txtDescriptionPro1.setRows(5);
        txtDescriptionPro1.setPreferredSize(new java.awt.Dimension(100, 84));
        jScrollPane11.setViewportView(txtDescriptionPro1);

        jLabel71.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText("Description");

        jButton12.setBackground(new java.awt.Color(102, 102, 102));
        jButton12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Close");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(102, 255, 0));
        jButton13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Edit");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Quantity");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(jButton12)
                                        .addGap(376, 376, 376)
                                        .addComponent(jButton13)))))
                        .addGap(77, 78, Short.MAX_VALUE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtQuantityPro1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNameProduct1)
                                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPriceProduct1)
                                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxCatefory1, 0, 228, Short.MAX_VALUE))
                                .addComponent(jLabel75)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUploadProduct1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jLabel67))
                .addGap(40, 40, 40)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPriceProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel75))
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnUploadProduct1)
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantityPro1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxCatefory1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton12))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogFormEditProductLayout = new javax.swing.GroupLayout(jDialogFormEditProduct.getContentPane());
        jDialogFormEditProduct.getContentPane().setLayout(jDialogFormEditProductLayout);
        jDialogFormEditProductLayout.setHorizontalGroup(
            jDialogFormEditProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogFormEditProductLayout.setVerticalGroup(
            jDialogFormEditProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogFormAddCustomer.setTitle("Form Add Customer");
        jDialogFormAddCustomer.setIconImage(null);
        jDialogFormAddCustomer.setMinimumSize(new java.awt.Dimension(511, 617));
        jDialogFormAddCustomer.setModal(true);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel50.setText("FORM");

        jLabel51.setBackground(new java.awt.Color(0, 204, 255));
        jLabel51.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 204, 204));
        jLabel51.setText("ADD");

        jLabel52.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel52.setText("Name");

        jLabel53.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel53.setText("Phone");

        txtMSKHCustomer.setEnabled(false);
        txtMSKHCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMSKHCustomerActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel54.setText("MSKH");

        txtAddressCustomer.setColumns(20);
        txtAddressCustomer.setRows(5);
        jScrollPane8.setViewportView(txtAddressCustomer);

        jLabel55.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel55.setText("Address");

        jButton6.setBackground(new java.awt.Color(102, 102, 102));
        jButton6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Close");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 255, 0));
        jButton8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Add");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51)
                        .addGap(190, 190, 190))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel29Layout.createSequentialGroup()
                                    .addComponent(jButton6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton8))
                                .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMSKHCustomer, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNameCustomer, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(60, 60, 60))))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51))
                .addGap(41, 41, 41)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMSKHCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton8))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogFormAddCustomerLayout = new javax.swing.GroupLayout(jDialogFormAddCustomer.getContentPane());
        jDialogFormAddCustomer.getContentPane().setLayout(jDialogFormAddCustomerLayout);
        jDialogFormAddCustomerLayout.setHorizontalGroup(
            jDialogFormAddCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogFormAddCustomerLayout.setVerticalGroup(
            jDialogFormAddCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogFormEditCustomer.setMinimumSize(new java.awt.Dimension(511, 617));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel56.setText("FORM");

        jLabel57.setBackground(new java.awt.Color(0, 204, 255));
        jLabel57.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 204, 204));
        jLabel57.setText("UPDATE");

        jLabel58.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel58.setText("MSKH");

        txtMSKHCustomerEdit.setEnabled(false);

        jLabel59.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel59.setText("Name");

        txtPhoneCustomerEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneCustomerEditActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel60.setText("Phone");

        txtAddressCustomer1.setColumns(20);
        txtAddressCustomer1.setRows(5);
        jScrollPane9.setViewportView(txtAddressCustomer1);

        jLabel61.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel61.setText("Address");

        jButton9.setBackground(new java.awt.Color(102, 102, 102));
        jButton9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Close");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 255, 0));
        jButton10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                                    .addComponent(jButton9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton10))
                                .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhoneCustomerEdit, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNameCustomerEdit, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMSKHCustomerEdit, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel57)
                        .addGap(165, 165, 165))))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(39, 39, 39)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMSKHCustomerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNameCustomerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhoneCustomerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogFormEditCustomerLayout = new javax.swing.GroupLayout(jDialogFormEditCustomer.getContentPane());
        jDialogFormEditCustomer.getContentPane().setLayout(jDialogFormEditCustomerLayout);
        jDialogFormEditCustomerLayout.setHorizontalGroup(
            jDialogFormEditCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogFormEditCustomerLayout.setVerticalGroup(
            jDialogFormEditCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogShowOder.setTitle("Form Oder");
        jDialogShowOder.setMinimumSize(new java.awt.Dimension(1230, 887));
        jDialogShowOder.setModal(true);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("NEW");

        jPanel33.setBackground(new java.awt.Color(0, 204, 204));
        jPanel33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTableShowProductFOder.setBackground(new java.awt.Color(255, 255, 255));
        jTableShowProductFOder.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableShowProductFOder.setForeground(new java.awt.Color(0, 0, 0));
        jTableShowProductFOder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane12.setViewportView(jTableShowProductFOder);

        btnChoseProduct.setBackground(new java.awt.Color(102, 204, 255));
        btnChoseProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/check-out.png"))); // NOI18N
        btnChoseProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoseProductActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(102, 204, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/loading.png"))); // NOI18N
        jButton14.setToolTipText("");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChoseProduct))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChoseProduct)
                    .addComponent(jButton14))
                .addContainerGap())
        );

        jpanel34.setBackground(new java.awt.Color(102, 102, 102));
        jpanel34.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel64.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("MSKH :");

        jLabel72.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Name :");

        txtShowMSKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowMSKH.setForeground(new java.awt.Color(255, 255, 255));
        txtShowMSKH.setToolTipText("");

        txtShowName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowName.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpanel34Layout = new javax.swing.GroupLayout(jpanel34);
        jpanel34.setLayout(jpanel34Layout);
        jpanel34Layout.setHorizontalGroup(
            jpanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel34Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jpanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpanel34Layout.createSequentialGroup()
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtShowName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpanel34Layout.createSequentialGroup()
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtShowMSKH, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanel34Layout.setVerticalGroup(
            jpanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel34Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jpanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtShowMSKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(txtShowName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel34.setBackground(new java.awt.Color(0, 204, 204));

        jTableChoseProduct.setBackground(new java.awt.Color(255, 255, 255));
        jTableChoseProduct.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableChoseProduct.setForeground(new java.awt.Color(0, 0, 0));
        jTableChoseProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(jTableChoseProduct);

        btnDeleteProductChose.setBackground(new java.awt.Color(255, 51, 0));
        btnDeleteProductChose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bin.png"))); // NOI18N
        btnDeleteProductChose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductChoseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDeleteProductChose)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteProductChose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel65.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Product Chose");

        jPanel35.setBackground(new java.awt.Color(102, 102, 102));

        jLabel73.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Total :");

        btnBuy.setBackground(new java.awt.Color(204, 204, 204));
        btnBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buy.png"))); // NOI18N
        btnBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyActionPerformed(evt);
            }
        });

        showTotalPrice.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        showTotalPrice.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuy)
                .addGap(19, 19, 19))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel73)
                            .addComponent(showTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBuy)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel76.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 204, 255));
        jLabel76.setText("ORDER");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(543, 543, 543)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel76))
                .addGap(18, 18, 18)
                .addComponent(jpanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogShowOderLayout = new javax.swing.GroupLayout(jDialogShowOder.getContentPane());
        jDialogShowOder.getContentPane().setLayout(jDialogShowOderLayout);
        jDialogShowOderLayout.setHorizontalGroup(
            jDialogShowOderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogShowOderLayout.setVerticalGroup(
            jDialogShowOderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogShowOderLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261))
        );

        jDialogQuantity.setMinimumSize(new java.awt.Dimension(400, 276));
        jDialogQuantity.setModal(true);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        jLabel77.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("ENTER");

        jLabel78.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 204, 255));
        jLabel78.setText("QUANTITY");

        jLabel79.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setText("Quantity :");

        txtEnterQuantity.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnEnterQuantity.setText("Ok");
        btnEnterQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterQuantityActionPerformed(evt);
            }
        });

        jButton15.setText("Close");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel78)
                .addGap(89, 89, 89))
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnterQuantity))
                    .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel79)
                        .addComponent(txtEnterQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(jLabel78))
                .addGap(43, 43, 43)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEnterQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnterQuantity)
                    .addComponent(jButton15))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogQuantityLayout = new javax.swing.GroupLayout(jDialogQuantity.getContentPane());
        jDialogQuantity.getContentPane().setLayout(jDialogQuantityLayout);
        jDialogQuantityLayout.setHorizontalGroup(
            jDialogQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogQuantityLayout.setVerticalGroup(
            jDialogQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialogShowOrderDetail.setMinimumSize(new java.awt.Dimension(765, 513));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jLabel80.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("MSKH :");

        txtShowMSKH1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowMSKH1.setForeground(new java.awt.Color(0, 0, 0));
        txtShowMSKH1.setToolTipText("");

        jLabel81.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Name :");

        txtShowName1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowName1.setForeground(new java.awt.Color(0, 0, 0));

        showOrderDetai1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OrderID", "ProductID", "Price", "Quantity"
            }
        ));
        jScrollPane14.setViewportView(showOrderDetai1);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtShowName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtShowMSKH1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtShowMSKH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel80))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(txtShowName1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogShowOrderDetailLayout = new javax.swing.GroupLayout(jDialogShowOrderDetail.getContentPane());
        jDialogShowOrderDetail.getContentPane().setLayout(jDialogShowOrderDetailLayout);
        jDialogShowOrderDetailLayout.setHorizontalGroup(
            jDialogShowOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogShowOrderDetailLayout.setVerticalGroup(
            jDialogShowOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MANAGER FEKER");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 202, 243));
        jPanel2.setPreferredSize(new java.awt.Dimension(206, 1080));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fekerMini.png"))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(0, 153, 204));

        jPanel4.setBackground(new java.awt.Color(0, 153, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel4MousePressed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home-button.png"))); // NOI18N

        txtHome.setBackground(new java.awt.Color(255, 255, 255));
        txtHome.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtHome.setForeground(new java.awt.Color(255, 255, 255));
        txtHome.setText("HOME");
        txtHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHomeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtHomeMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHome, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHome)
                    .addComponent(jLabel4))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 204));
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Employees.png"))); // NOI18N

        txtAdmin.setBackground(new java.awt.Color(255, 255, 255));
        txtAdmin.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtAdmin.setForeground(new java.awt.Color(255, 255, 255));
        txtAdmin.setText("EMPLOYEE");
        txtAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAdminMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtAdmin))
                .addGap(24, 24, 24))
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/categories.png"))); // NOI18N

        txtCategoy.setBackground(new java.awt.Color(255, 255, 255));
        txtCategoy.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCategoy.setForeground(new java.awt.Color(255, 255, 255));
        txtCategoy.setText("CATEGORY");
        txtCategoy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCategoyMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCategoy)
                .addGap(19, 19, 19))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txtCategoy))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 153, 204));
        jPanel8.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/products.png"))); // NOI18N

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("PRODUCT");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(35, 35, 35))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13)))
                .addGap(24, 24, 24))
        );

        jPanel11.setBackground(new java.awt.Color(0, 153, 204));
        jPanel11.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/supllier.png"))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CUSTOMER");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 153, 204));
        jPanel12.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/order.png"))); // NOI18N

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ORDER");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(71, 71, 71))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(45, 45, 45)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("designed by TN and created by team FEKER || @CUSC 2024");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1710, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jTabbedPane1.setBackground(new java.awt.Color(153, 255, 255));

        Home.setForeground(new java.awt.Color(0, 0, 0));
        Home.setPreferredSize(new java.awt.Dimension(170, 745));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(1300, 745));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Home");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1213, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5)
                .addContainerGap(693, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", Home);

        Employees.setForeground(new java.awt.Color(0, 0, 0));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(1300, 745));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("MANAGER EMPLOYEES");

        inputSearchEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputSearchEmpActionPerformed(evt);
            }
        });

        btnSearchEmp.setBackground(new java.awt.Color(255, 255, 255));
        btnSearchEmp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSearchEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        btnSearchEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchEmpActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Search MSNV");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel7.setPreferredSize(new java.awt.Dimension(600, 75));

        btnEdit.setBackground(new java.awt.Color(0, 102, 204));
        btnEdit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pen.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 204, 0));
        btnAdd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btndelete.setBackground(new java.awt.Color(204, 0, 0));
        btndelete.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bin.png"))); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        jtableEmployees.setBackground(new java.awt.Color(255, 255, 255));
        jtableEmployees.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "MSNV", "Name", "Phone", "Address", "Img", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtableEmployees);
        if (jtableEmployees.getColumnModel().getColumnCount() > 0) {
            jtableEmployees.getColumnModel().getColumn(0).setPreferredWidth(10);
            jtableEmployees.getColumnModel().getColumn(1).setPreferredWidth(15);
            jtableEmployees.getColumnModel().getColumn(2).setPreferredWidth(150);
            jtableEmployees.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exchange.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(543, 543, 543)
                .addComponent(jLabel7)
                .addContainerGap(499, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(547, 547, 547)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(inputSearchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchEmp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel7)
                .addGap(21, 21, 21)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputSearchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout EmployeesLayout = new javax.swing.GroupLayout(Employees);
        Employees.setLayout(EmployeesLayout);
        EmployeesLayout.setHorizontalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeesLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 750, Short.MAX_VALUE))
        );
        EmployeesLayout.setVerticalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", Employees);

        Categorys.setForeground(new java.awt.Color(0, 0, 0));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setPreferredSize(new java.awt.Dimension(1300, 745));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("MANAGER CATEGORIES");

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(600, 75));

        buttonDeleteCategory.setBackground(new java.awt.Color(204, 0, 0));
        buttonDeleteCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bin.png"))); // NOI18N
        buttonDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteCategoryActionPerformed(evt);
            }
        });

        buttonAddCategory.setBackground(new java.awt.Color(0, 204, 0));
        buttonAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus.png"))); // NOI18N
        buttonAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddCategoryActionPerformed(evt);
            }
        });

        buttonEditCategory.setBackground(new java.awt.Color(0, 102, 204));
        buttonEditCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pen.png"))); // NOI18N
        buttonEditCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(buttonAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonEditCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonEditCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jtableCategorys.setBackground(new java.awt.Color(255, 255, 255));
        jtableCategorys.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jtableCategorys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CategoryID", "CategoryName", "Description"
            }
        ));
        jScrollPane3.setViewportView(jtableCategorys);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Search Name");

        buttonResetCategory.setBackground(new java.awt.Color(255, 255, 255));
        buttonResetCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exchange.png"))); // NOI18N
        buttonResetCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetCategoryActionPerformed(evt);
            }
        });

        buttonSearchCategory.setBackground(new java.awt.Color(255, 255, 255));
        buttonSearchCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        buttonSearchCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel26Layout.createSequentialGroup()
                                        .addComponent(inputSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonSearchCategory)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonResetCategory))
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(529, 529, 529)
                        .addComponent(jLabel1)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonResetCategory)
                            .addComponent(buttonSearchCategory)
                            .addComponent(inputSearchCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout CategorysLayout = new javax.swing.GroupLayout(Categorys);
        Categorys.setLayout(CategorysLayout);
        CategorysLayout.setHorizontalGroup(
            CategorysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CategorysLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 1333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 737, Short.MAX_VALUE))
        );
        CategorysLayout.setVerticalGroup(
            CategorysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", Categorys);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("MANAGER PRODUCTS");

        jTableProducts.setBackground(new java.awt.Color(255, 255, 255));
        jTableProducts.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PeoductID", "Name", "CategoryID", "Price", "Description", "Img"
            }
        ));
        jScrollPane5.setViewportView(jTableProducts);
        if (jTableProducts.getColumnModel().getColumnCount() > 0) {
            jTableProducts.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTableProducts.getColumnModel().getColumn(5).setHeaderValue("Img");
        }

        btnAddProduct.setBackground(new java.awt.Color(0, 204, 0));
        btnAddProduct.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus.png"))); // NOI18N
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnFormEditProduct.setBackground(new java.awt.Color(0, 102, 204));
        btnFormEditProduct.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnFormEditProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pen.png"))); // NOI18N
        btnFormEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormEditProductActionPerformed(evt);
            }
        });

        btnDeleteProduct.setBackground(new java.awt.Color(204, 0, 0));
        btnDeleteProduct.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnDeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bin.png"))); // NOI18N
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Search Name");

        btnSearchProduct.setBackground(new java.awt.Color(255, 255, 255));
        btnSearchProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        btnSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductActionPerformed(evt);
            }
        });

        btnResetProduct.setBackground(new java.awt.Color(255, 255, 255));
        btnResetProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exchange.png"))); // NOI18N
        btnResetProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFormEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(554, 554, 554))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119)))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(inputSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetProduct)))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnResetProduct, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearchProduct, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputSearchProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFormEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout ProductsLayout = new javax.swing.GroupLayout(Products);
        Products.setLayout(ProductsLayout);
        ProductsLayout.setHorizontalGroup(
            ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductsLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 728, Short.MAX_VALUE))
        );
        ProductsLayout.setVerticalGroup(
            ProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", Products);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel47.setBackground(new java.awt.Color(255, 255, 255));
        jLabel47.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("MANAGER CUSTOMER");

        jTableCustomers.setBackground(new java.awt.Color(255, 255, 255));
        jTableCustomers.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableCustomers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CustomerID", "Name", "Phone", "Address", "MSKH"
            }
        ));
        jScrollPane6.setViewportView(jTableCustomers);
        if (jTableCustomers.getColumnModel().getColumnCount() > 0) {
            jTableCustomers.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        btnAddCustomer.setBackground(new java.awt.Color(0, 204, 0));
        btnAddCustomer.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAddCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus.png"))); // NOI18N
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        btnFormEditCustomer.setBackground(new java.awt.Color(0, 102, 204));
        btnFormEditCustomer.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnFormEditCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pen.png"))); // NOI18N
        btnFormEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormEditCustomerActionPerformed(evt);
            }
        });

        btnDeleteCustomer.setBackground(new java.awt.Color(204, 0, 0));
        btnDeleteCustomer.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnDeleteCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bin.png"))); // NOI18N
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Search Name");

        btnSearchCustomer.setBackground(new java.awt.Color(255, 255, 255));
        btnSearchCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        btnSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCustomerActionPerformed(evt);
            }
        });

        btnResetCustomer.setBackground(new java.awt.Color(255, 255, 255));
        btnResetCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exchange.png"))); // NOI18N
        btnResetCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetCustomerActionPerformed(evt);
            }
        });

        btnOder.setBackground(new java.awt.Color(102, 102, 102));
        btnOder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/check-out.png"))); // NOI18N
        btnOder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFormEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(266, 266, 266)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(btnOder, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(119, 119, 119)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(intputSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchCustomer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetCustomer)))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnResetCustomer, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearchCustomer, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(intputSearchCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFormEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout CustomersLayout = new javax.swing.GroupLayout(Customers);
        Customers.setLayout(CustomersLayout);
        CustomersLayout.setHorizontalGroup(
            CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomersLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 750, Short.MAX_VALUE))
        );
        CustomersLayout.setVerticalGroup(
            CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", Customers);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel62.setBackground(new java.awt.Color(255, 255, 255));
        jLabel62.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("MANAGER ODERS");

        jTableOrder.setBackground(new java.awt.Color(255, 255, 255));
        jTableOrder.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CustomerID", "Name", "Phone", "Address", "MSKH"
            }
        ));
        jScrollPane10.setViewportView(jTableOrder);
        if (jTableOrder.getColumnModel().getColumnCount() > 0) {
            jTableOrder.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        btnAddCustomer1.setBackground(new java.awt.Color(0, 0, 0));
        btnAddCustomer1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAddCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/info.png"))); // NOI18N
        btnAddCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomer1ActionPerformed(evt);
            }
        });

        jLabel63.setBackground(new java.awt.Color(255, 255, 255));
        jLabel63.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Search Name");

        btnSearchCustomer1.setBackground(new java.awt.Color(255, 255, 255));
        btnSearchCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        btnSearchCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCustomer1ActionPerformed(evt);
            }
        });

        btnResetCustomer1.setBackground(new java.awt.Color(255, 255, 255));
        btnResetCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exchange.png"))); // NOI18N
        btnResetCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetCustomer1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnAddCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(378, 378, 378)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(intputSearchCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchCustomer1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetCustomer1)))))
                .addContainerGap(802, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnResetCustomer1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearchCustomer1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(intputSearchCustomer1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnAddCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout OdersLayout = new javax.swing.GroupLayout(Oders);
        Oders.setLayout(OdersLayout);
        OdersLayout.setHorizontalGroup(
            OdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        OdersLayout.setVerticalGroup(
            OdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", Oders);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHomeMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_txtHomeMouseClicked

    private void txtAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAdminMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_txtAdminMouseClicked

    private void txtCategoyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCategoyMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_txtCategoyMouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel4MousePressed

    private void txtHomeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHomeMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtHomeMousePressed

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void inputSearchEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputSearchEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputSearchEmpActionPerformed


    private void btnSearchEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchEmpActionPerformed
        // TODO add your handling code here:
        searchEmployee();
    }//GEN-LAST:event_btnSearchEmpActionPerformed


    private void btnAddEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeesActionPerformed
        // TODO add your handling code here
        addEmployee();
    }//GEN-LAST:event_btnAddEmployeesActionPerformed


    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        jDialogAddEmployee.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    // btn upload image 
    private void jButtonUploaImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUploaImgActionPerformed
        // TODO add your handling code here:
        uploadImgEmployees();
    }//GEN-LAST:event_jButtonUploaImgActionPerformed


    private void btnEditEmployees1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEmployees1ActionPerformed
        editEmployee();
    }//GEN-LAST:event_btnEditEmployees1ActionPerformed


    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        // TODO add your handling code here:
        jDialogUpdateEmployees.setVisible(false);
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void jButtonUploaImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUploaImg1ActionPerformed
        // TODO add your handling code here:
        uploadImgFormUpdateEmployees();
    }//GEN-LAST:event_jButtonUploaImg1ActionPerformed

    private void jDialogUpdateEmployeesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDialogUpdateEmployeesComponentShown
        // TODO add your handling code here:

    }//GEN-LAST:event_jDialogUpdateEmployeesComponentShown

    private void txtPassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void buttonAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddCategoryActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogAddCategory.setLocation(dim.width / 2 - jDialogAddCategory.getSize().width / 2, dim.height / 2 - jDialogAddCategory.getSize().height / 2);

        jDialogAddCategory.setVisible(true);
    }//GEN-LAST:event_buttonAddCategoryActionPerformed

    private void buttonEditCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditCategoryActionPerformed
        // TODO add your handling code here:    
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogUpdateCategory.setLocation(dim.width / 2 - jDialogUpdateCategory.getSize().width / 2, dim.height / 2 - jDialogUpdateCategory.getSize().height / 2);

        showFormUpdateCategorys();
    }//GEN-LAST:event_buttonEditCategoryActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jDialogAddCategory.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        addCategorys();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buttonDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteCategoryActionPerformed
        // TODO add your handling code here:
        deleteCategorys();
    }//GEN-LAST:event_buttonDeleteCategoryActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jDialogUpdateCategory.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        editCategorys();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void buttonSearchCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchCategoryActionPerformed
        // TODO add your handling code here:
        searchCategorys();
    }//GEN-LAST:event_buttonSearchCategoryActionPerformed

    private void buttonResetCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetCategoryActionPerformed
        // TODO add your handling code here:
        resetTableCategorys();
    }//GEN-LAST:event_buttonResetCategoryActionPerformed

    private void btnResetProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetProductActionPerformed
        // TODO add your handling code here:
        resetTableProducts();
    }//GEN-LAST:event_btnResetProductActionPerformed

    // btn delete table employees 
    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        deleteEmployees();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogAddEmployee.setLocation(dim.width / 2 - jDialogAddEmployee.getSize().width / 2, dim.height / 2 - jDialogAddEmployee.getSize().height / 2);

        jDialogAddEmployee.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogUpdateEmployees.setLocation(dim.width / 2 - jDialogUpdateEmployees.getSize().width / 2, dim.height / 2 - jDialogUpdateEmployees.getSize().height / 2);

        showFormUpdate();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:\
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogFormAddProduct.setLocation(dim.width / 2 - jDialogFormAddProduct.getSize().width / 2, dim.height / 2 - jDialogFormAddProduct.getSize().height / 2);

        jDialogFormAddProduct.setVisible(true);
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jDialogFormAddProduct.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        addProduct();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogFormAddCustomer.setLocation(dim.width / 2 - jDialogFormAddCustomer.getSize().width / 2, dim.height / 2 - jDialogFormAddCustomer.getSize().height / 2);

        jDialogFormAddCustomer.setVisible(true);
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    private void btnResetCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetCustomerActionPerformed
        // TODO add your handling code here:
        resetTableCustomers();
    }//GEN-LAST:event_btnResetCustomerActionPerformed

    private void btnSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomerActionPerformed
        // TODO add your handling code here:
        searchCustomers();
    }//GEN-LAST:event_btnSearchCustomerActionPerformed

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        // TODO add your handling code here:
        deleteCustomers();
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void txtMSKHCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMSKHCustomerActionPerformed
//        String randomMSKH = generateRandomMSKH();
//        txtMSKHCustomer.setText(randomMSKH);
    }//GEN-LAST:event_txtMSKHCustomerActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        addCustomers();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtPhoneCustomerEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneCustomerEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneCustomerEditActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        editCustomers();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnFormEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormEditCustomerActionPerformed
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogFormEditCustomer.setLocation(dim.width / 2 - jDialogFormEditCustomer.getSize().width / 2, dim.height / 2 - jDialogFormEditCustomer.getSize().height / 2);

        showFormUpdateCustomers();
    }//GEN-LAST:event_btnFormEditCustomerActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        jDialogFormAddCustomer.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jDialogFormAddCustomer.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBoxCateforyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCateforyActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBoxCateforyActionPerformed

    private void btnOderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOderActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogShowOder.setLocation(dim.width / 2 - jDialogShowOder.getSize().width / 2, dim.height / 2 - jDialogShowOder.getSize().height / 2);

        ShowFormOder();
        showTableProInOde();

    }//GEN-LAST:event_btnOderActionPerformed

    private void btnAddCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomer1ActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogShowOrderDetail.setLocation(dim.width / 2 - jDialogShowOrderDetail.getSize().width / 2, dim.height / 2 - jDialogShowOrderDetail.getSize().height / 2);

        showFormOrderDetail();
    }//GEN-LAST:event_btnAddCustomer1ActionPerformed

    private void btnSearchCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchCustomer1ActionPerformed

    private void btnResetCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetCustomer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetCustomer1ActionPerformed

    private void btnUploadProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadProductActionPerformed
        // TODO add your handling code here:
        uploadImgProducts();
    }//GEN-LAST:event_btnUploadProductActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        // TODO add your handling code here:
        deleteProducts();
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void btnFormEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormEditProductActionPerformed
        // TODO add your handling code here:
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jDialogFormEditProduct.setLocation(dim.width / 2 - jDialogFormEditProduct.getSize().width / 2, dim.height / 2 - jDialogFormEditProduct.getSize().height / 2);

        showFormUpdateProducts();
        loadCategories();
    }//GEN-LAST:event_btnFormEditProductActionPerformed

    private void jComboBoxCatefory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCatefory1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCatefory1ActionPerformed

    private void btnUploadProduct1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadProduct1ActionPerformed
        // TODO add your handling code here:
        uploadImgFormUpdateProducts();
    }//GEN-LAST:event_btnUploadProduct1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        editProducts();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductActionPerformed
        // TODO add your handling code here:
        searchProducts();
    }//GEN-LAST:event_btnSearchProductActionPerformed

    private void txtQuantityProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityProActionPerformed

    }//GEN-LAST:event_txtQuantityProActionPerformed

    private void btnChoseProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoseProductActionPerformed
        if (jTableShowProductFOder.getSelectedRow() == -1) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Please choose a product in choose table", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            jDialogQuantity.setLocation(dim.width / 2 - jDialogQuantity.getSize().width / 2, dim.height / 2 - jDialogQuantity.getSize().height / 2);
            jDialogQuantity.setVisible(true);
        }
    }//GEN-LAST:event_btnChoseProductActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        jDialogQuantity.dispose();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnEnterQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterQuantityActionPerformed
        if (txtEnterQuantity.getText().isEmpty()) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Please enter a quantity of product! ", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(txtEnterQuantity.getText()).compareTo("0") == 0) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Quantity must be greater than 0!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            int index = jTableShowProductFOder.getSelectedRow();
            int id = Integer.parseInt(jTableShowProductFOder.getValueAt(index, 0).toString());
            String name = String.valueOf(jTableShowProductFOder.getValueAt(index, 1));
            int quantity = Integer.parseInt(String.valueOf(txtEnterQuantity.getText()));
            double unitPrice = Double.parseDouble(String.valueOf(jTableShowProductFOder.getValueAt(index, 4)));
            double price = unitPrice * quantity;

            // Update row if existing
            boolean updated = false;
            for (int rowIndex = 0; rowIndex < modelOrder.getRowCount(); rowIndex++) {
                Object idChecked = modelOrder.getValueAt(rowIndex, 0);
                if ((int) idChecked == id) {
                    int oldQuantity = (int) modelOrder.getValueAt(rowIndex, 3);
                    modelOrder.setValueAt(oldQuantity + quantity, rowIndex, 3);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                modelOrder.addRow(new Object[]{id, name, unitPrice, quantity, price});
            }

            int number = jTableChoseProduct.getRowCount();
            System.out.println(number);
            double total = 0;
            for (int i = 0; i < number; i++) {
                total += Double.parseDouble(String.valueOf(jTableChoseProduct.getValueAt(i, 4)));
            }
            showTotalPrice.setText(String.valueOf(total));
            jDialogQuantity.dispose();
        }
    }//GEN-LAST:event_btnEnterQuantityActionPerformed

    private void btnDeleteProductChoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductChoseActionPerformed
        if (jTableChoseProduct.getSelectedRow() == -1) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Please choose a product in product choose table", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame frame = new JFrame();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            dialogButton = JOptionPane.showConfirmDialog(frame, "Are you sure to deselect product?", "Warning", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                modelOrder.removeRow(jTableChoseProduct.getSelectedRow());
            }
        }
    }//GEN-LAST:event_btnDeleteProductChoseActionPerformed

    private String mskh;
    private void btnBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyActionPerformed

        try {
            // TODO add your handling code here:
            double totalPrice = Double.parseDouble(showTotalPrice.getText().trim());

            ConnecDB cn = new ConnecDB();
            Connection conn = cn.getConnection(); // Lấy kết nối từ ConnecDB
            String sql = "INSERT INTO Oders (MSKH, TotalPrice ) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, mskh);
            pst.setDouble(2, totalPrice);
            System.out.println(mskh);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Successfully ordered!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            ResultSet generatedKeys = pst.getGeneratedKeys();

            if (generatedKeys.next()) {
                int orderID = generatedKeys.getInt(1);

                //  Insert order details
                for (int rowIndex = 0; rowIndex < modelOrder.getRowCount(); rowIndex++) {
                    int productID = (int) modelOrder.getValueAt(rowIndex, 0);
                    double unitPrice = (double) modelOrder.getValueAt(rowIndex, 2);
                    int quantity = (int) modelOrder.getValueAt(rowIndex, 3);
                    String sqlOrderDetails = "INSERT INTO OrderDetail (OderID, ProductID, Price, Quantity) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstOrderDetail = conn.prepareStatement(sqlOrderDetails, Statement.RETURN_GENERATED_KEYS);
                    pstOrderDetail.setInt(1, orderID);
                    pstOrderDetail.setInt(2, productID);
                    pstOrderDetail.setDouble(3, unitPrice);
                    pstOrderDetail.setInt(4, quantity);

                    pstOrderDetail.executeUpdate();
                    pstOrderDetail.close();
                }
            }
            pst.close();
            conn.close();

            // Reset form
            resetForm();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuyActionPerformed

    private void resetForm() {
        // Xóa dữ liệu trong bảng modelOrder
        DefaultTableModel modelOrder = (DefaultTableModel) jTableChoseProduct.getModel();
        modelOrder.setRowCount(0);

        // Đặt lại các trường văn bản về giá trị mặc định
        showTotalPrice.setText("");

        // Nếu có các trường khác cần đặt lại, thêm chúng vào đây
    }

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        resetTableProInOde();
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     *
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Linux".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);

            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Categorys;
    private javax.swing.JPanel Customers;
    private javax.swing.JPanel Employees;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel Oders;
    private javax.swing.JPanel Products;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnAddCustomer1;
    private javax.swing.JButton btnAddEmployees;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBuy;
    private javax.swing.JButton btnChoseProduct;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnDeleteProductChose;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEditEmployees1;
    private javax.swing.JButton btnEnterQuantity;
    private javax.swing.JButton btnFormEditCustomer;
    private javax.swing.JButton btnFormEditProduct;
    private javax.swing.JButton btnOder;
    private javax.swing.JButton btnResetCustomer;
    private javax.swing.JButton btnResetCustomer1;
    private javax.swing.JButton btnResetProduct;
    private javax.swing.JButton btnSearchCustomer;
    private javax.swing.JButton btnSearchCustomer1;
    private javax.swing.JButton btnSearchEmp;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JButton btnUploadProduct;
    private javax.swing.JButton btnUploadProduct1;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton buttonAddCategory;
    private javax.swing.JButton buttonDeleteCategory;
    private javax.swing.JButton buttonEditCategory;
    private javax.swing.JButton buttonResetCategory;
    private javax.swing.JButton buttonSearchCategory;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JTextField inputSearchCategory;
    private javax.swing.JTextField inputSearchEmp;
    private javax.swing.JTextField inputSearchProduct;
    private javax.swing.JTextField intputSearchCustomer;
    private javax.swing.JTextField intputSearchCustomer1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonUploaImg;
    private javax.swing.JButton jButtonUploaImg1;
    private javax.swing.JComboBox<String> jComboBoxCatefory;
    private javax.swing.JComboBox<String> jComboBoxCatefory1;
    private javax.swing.JDialog jDialogAddCategory;
    private javax.swing.JDialog jDialogAddEmployee;
    private javax.swing.JDialog jDialogFormAddCustomer;
    private javax.swing.JDialog jDialogFormAddProduct;
    private javax.swing.JDialog jDialogFormEditCustomer;
    private javax.swing.JDialog jDialogFormEditProduct;
    private javax.swing.JDialog jDialogQuantity;
    private javax.swing.JDialog jDialogShowOder;
    private javax.swing.JDialog jDialogShowOrderDetail;
    private javax.swing.JDialog jDialogUpdateCategory;
    private javax.swing.JDialog jDialogUpdateEmployees;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelImgProduct;
    private javax.swing.JLabel jLabelImgProduct1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableChoseProduct;
    private javax.swing.JTable jTableCustomers;
    private javax.swing.JTable jTableOrder;
    private javax.swing.JTable jTableProducts;
    private javax.swing.JTable jTableShowProductFOder;
    private javax.swing.JPanel jpanel34;
    private javax.swing.JTable jtableCategorys;
    private javax.swing.JTable jtableEmployees;
    private javax.swing.JTable showOrderDetai1;
    private javax.swing.JLabel showTotalPrice;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JTextArea txtAddressCustomer;
    private javax.swing.JTextArea txtAddressCustomer1;
    private javax.swing.JLabel txtAdmin;
    private javax.swing.JTextArea txtCategoryDescription;
    private javax.swing.JTextArea txtCategoryDescription1;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtCategoryName1;
    private javax.swing.JLabel txtCategoy;
    private javax.swing.JTextArea txtDescriptionPro;
    private javax.swing.JTextArea txtDescriptionPro1;
    private javax.swing.JLabel txtEmployeeID;
    private javax.swing.JTextField txtEnterQuantity;
    private javax.swing.JLabel txtHome;
    private javax.swing.JTextField txtMSKHCustomer;
    private javax.swing.JTextField txtMSKHCustomerEdit;
    private javax.swing.JTextField txtMSNV;
    private javax.swing.JTextField txtMSNV1;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtName1;
    private javax.swing.JTextField txtNameCustomer;
    private javax.swing.JTextField txtNameCustomerEdit;
    private javax.swing.JTextField txtNameProduct;
    private javax.swing.JTextField txtNameProduct1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone1;
    private javax.swing.JTextField txtPhoneCustomer;
    private javax.swing.JTextField txtPhoneCustomerEdit;
    private javax.swing.JTextField txtPriceProduct;
    private javax.swing.JTextField txtPriceProduct1;
    private javax.swing.JTextField txtQuantityPro;
    private javax.swing.JTextField txtQuantityPro1;
    private javax.swing.JLabel txtShowMSKH;
    private javax.swing.JLabel txtShowMSKH1;
    private javax.swing.JLabel txtShowName;
    private javax.swing.JLabel txtShowName1;
    // End of variables declaration//GEN-END:variables

}
