/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author nguye
 */
public class OrderDetail {
    private int OderID;
    private int ProductID;
    private double Price;
    private int Quantity;

    public OrderDetail() {
    }

    public OrderDetail(int OderID, int ProductID, double Price, int Quantity) {
        this.OderID = OderID;
        this.ProductID = ProductID;
        this.Price = Price;
        this.Quantity = Quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getOderID() {
        return OderID;
    }

    public void setOderID(int OderID) {
        this.OderID = OderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
    
    
}
