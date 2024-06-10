/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author nguye
 */
public class Products {
    private int ProductID;
    private String Name;
    private String CategoryName;
    private String Img;
    private double Price;

    public Products(int Quantity) {
        this.Quantity = Quantity;
    }
    private String Descriptions;
    private int Quantity;
    public Products() {
    }

    public Products(int ProductID, String Name, String CategoryName, String Img, double Price, String Descriptions) {
        this.ProductID = ProductID;
        this.Name = Name;
        this.CategoryName = CategoryName;
        this.Img = Img;
        this.Price = Price;
        this.Descriptions = Descriptions;
    
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String Descriptions) {
        this.Descriptions = Descriptions;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }



   
    
    
    
}
