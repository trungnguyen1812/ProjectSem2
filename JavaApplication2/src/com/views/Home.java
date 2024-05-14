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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.DAO.DAO;
import com.models.Employees;
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

/**
 *
 * @author nguye
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    Color defaulcolor, clickcolor, white;
    private ArrayList<Employees> employees = new ArrayList<>();
    DefaultTableModel model;


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

    public Home() {
        initComponents();
        Image icon = new ImageIcon(this.getClass().getResource("/resources/feker-high-resolution-logo.png")).getImage();
        this.setIconImage(icon);
        setLocationRelativeTo(null);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setResizable(false);

        defaulcolor = new Color(255, 255, 255);
        clickcolor = new Color(0, 153, 153);
        white = new Color(255, 255, 255);

        this.setLocationRelativeTo(null);
        employees = new DAO().getListEmployeees();

        model = (DefaultTableModel) jtableEmployees.getModel();

        // funtion Employees
        model.setColumnIdentifiers(new Object[]{
            "EmployeeID", "MSNV", "Name", "Phone", "Address", "Img", "Password"
        });
        showTableEmployees();
        model.fireTableDataChanged();

        //funtion catogery
        //funtion Products
        //funtion Customers
        //funtion oders
    }

    // chuc nang employees 
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

        // Notify the table model that the data has changed
        model.fireTableDataChanged();
    }
    
      // Hàm editEmployee để mở EditEmployeeDialog
  private void editEmployee() {
        int selectedRow = jtableEmployees.getSelectedRow();
        if (selectedRow != -1) {
            int employeeID = (int) model.getValueAt(selectedRow, 0);
            Employees employee = findEmployeeByID(employeeID);
            if (employee != null) {
                // Mở dialog chỉnh sửa và truyền thông tin nhân viên cần chỉnh sửa vào dialog
                EditEmployeeDialog dialog = new EditEmployeeDialog(this, true, employee);
                dialog.setVisible(true);
                // Sau khi dialog đóng lại, cập nhật bảng hiển thị
                showTableEmployees();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chỉnh sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Employees findEmployeeByID(int id) {
        for (Employees employee : employees) {
            if (employee.getEmployeeID() == id) {
                return employee;
            }
        }
        return null;
    }


    public void setEmployees(ArrayList<Employees> employees) {
        this.employees = employees;
    }

    public ArrayList<Employees> getEmployees() {
        return employees;
    }


    
   

    // chuc nang employees 
    // chuc nang categorys
    // chuc nang products 
    // chuc nang customers 
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
        jPanel1 = new JPanelGradient();
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
        txtLoginName1 = new javax.swing.JTextField();
        btnDelete1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableEmployees = new javax.swing.JTable();
        Categorys = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        products = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        Customers = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        Oders = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        jDialogAddEmployee.setMinimumSize(new java.awt.Dimension(847, 735));
        jDialogAddEmployee.setModal(true);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel10.setText("From ");

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
        jLabel23.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 204, 204));
        jLabel23.setText("Add");

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

        jDialogUpdateEmployees.setMinimumSize(new java.awt.Dimension(847, 735));
        jDialogUpdateEmployees.setModal(true);
        jDialogUpdateEmployees.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jDialogUpdateEmployeesComponentShown(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel24.setText("From ");

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
        jLabel30.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 204, 204));
        jLabel30.setText("Edit");

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
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(btnClose1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditEmployees1))
                            .addComponent(jButtonUploaImg1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105))))
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
                .addGap(41, 41, 41)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel30))
                .addGap(50, 50, 50)
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
                .addContainerGap(67, Short.MAX_VALUE))
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HOME");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(206, 1080));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fekerMini.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
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
        txtHome.setForeground(new java.awt.Color(0, 0, 0));
        txtHome.setText("Home");
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
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHome, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtHome)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/people.png"))); // NOI18N

        txtAdmin.setBackground(new java.awt.Color(255, 255, 255));
        txtAdmin.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtAdmin.setForeground(new java.awt.Color(0, 0, 0));
        txtAdmin.setText("Employees");
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
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtAdmin))
                    .addComponent(jLabel6))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/categories.png"))); // NOI18N

        txtCategoy.setBackground(new java.awt.Color(255, 255, 255));
        txtCategoy.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCategoy.setForeground(new java.awt.Color(0, 0, 0));
        txtCategoy.setText("Category");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCategoy, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtCategoy)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/products.png"))); // NOI18N

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Products");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel13)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/supllier.png"))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Customers");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel15))
                    .addComponent(jLabel14))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(0, 80));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/order.png"))); // NOI18N

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Oders");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(65, 65, 65))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel17))
                    .addComponent(jLabel16))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
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
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(419, Short.MAX_VALUE))
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
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Employees");

        txtLoginName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginName1ActionPerformed(evt);
            }
        });

        btnDelete1.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plus (1).png"))); // NOI18N
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        jLabel19.setText("Search Name");

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
                .addGap(40, 40, 40)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jtableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "MSNV", "Name", "Phone", "Address", "Img", "Password"
            }
        ));
        jScrollPane2.setViewportView(jtableEmployees);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(600, 600, 600)
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 511, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(txtLoginName1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel7)
                .addGap(23, 23, 23)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(txtLoginName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout EmployeesLayout = new javax.swing.GroupLayout(Employees);
        Employees.setLayout(EmployeesLayout);
        EmployeesLayout.setHorizontalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeesLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
        );
        EmployeesLayout.setVerticalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", Employees);

        Categorys.setForeground(new java.awt.Color(0, 0, 0));

        jPanel18.setBackground(new java.awt.Color(204, 0, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Category");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(461, 461, 461)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(739, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(437, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(342, 342, 342))
        );

        javax.swing.GroupLayout CategorysLayout = new javax.swing.GroupLayout(Categorys);
        Categorys.setLayout(CategorysLayout);
        CategorysLayout.setHorizontalGroup(
            CategorysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CategorysLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
        );
        CategorysLayout.setVerticalGroup(
            CategorysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CategorysLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("tab3", Categorys);

        jPanel13.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1320, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout productsLayout = new javax.swing.GroupLayout(products);
        products.setLayout(productsLayout);
        productsLayout.setHorizontalGroup(
            productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
        );
        productsLayout.setVerticalGroup(
            productsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", products);

        jPanel14.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1320, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout CustomersLayout = new javax.swing.GroupLayout(Customers);
        Customers.setLayout(CustomersLayout);
        CustomersLayout.setHorizontalGroup(
            CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomersLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
        );
        CustomersLayout.setVerticalGroup(
            CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", Customers);

        jPanel19.setBackground(new java.awt.Color(204, 204, 255));

        jLabel9.setText("Oders");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1033, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel9)
                .addContainerGap(640, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void txtLoginName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginName1ActionPerformed

    // btn delete table employees 
    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
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
            boolean success = dao.deleteAdmin(id);

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
    }//GEN-LAST:event_btndeleteActionPerformed


    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        jDialogUpdateEmployees.setVisible(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        jDialogAddEmployee.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeesActionPerformed
        // TODO add your handling code here
        addEmployee();
    }//GEN-LAST:event_btnAddEmployeesActionPerformed

    private void addEmployee() {
        String MSNV = txtMSNV.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String password = new String(txtPassword.getPassword());
        String imagePath = ""; // Đường dẫn mặc định là rỗng

        if (MSNV.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter All Data!");
            return;
        }

        // Lấy đường dẫn từ JFileChooser
        JFileChooser jFileChooser = new JFileChooser("D:\\admin\\ProjectSem2\\JavaApplication2\\src\\resources\\avatar");
        jFileChooser.setDialogTitle("Open File");
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();
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
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    DefaultTableModel model = (DefaultTableModel) jtableEmployees.getModel();
                    model.addRow(new Object[]{id, MSNV, name, phone, address, imagePath, password});
                }
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

// hàm edit employees
    private void updateEmployee() throws ClassNotFoundException {

    }


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
        // TODO add your handling code here:
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

    }//GEN-LAST:event_jButtonUploaImgActionPerformed


    private void btnEditEmployees1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEmployees1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnEditEmployees1ActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        // TODO add your handling code here:
        jDialogUpdateEmployees.setVisible(false);
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void jButtonUploaImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUploaImg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUploaImg1ActionPerformed

    private void jDialogUpdateEmployeesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDialogUpdateEmployeesComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialogUpdateEmployeesComponentShown

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
                if ("Nimbus".equals(info.getName())) {
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddEmployees;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEditEmployees1;
    private javax.swing.JButton btndelete;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JButton jButtonUploaImg;
    private javax.swing.JButton jButtonUploaImg1;
    private javax.swing.JDialog jDialogAddEmployee;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jtableEmployees;
    private javax.swing.JPanel products;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JLabel txtAdmin;
    private javax.swing.JLabel txtCategoy;
    private javax.swing.JLabel txtHome;
    private javax.swing.JTextField txtLoginName1;
    private javax.swing.JTextField txtMSNV;
    private javax.swing.JTextField txtMSNV1;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtName1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone1;
    // End of variables declaration//GEN-END:variables

}
