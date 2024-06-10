/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author nguye
 */
public class Order {
   private int OderID;
   private String MSKH;
   private double TotalPrice;

    public Order(int OderID, String MSKH, double TotalPrice) {
        this.OderID = OderID;
        this.MSKH = MSKH;
        this.TotalPrice = TotalPrice;
    }

    public Order() {
    }

    public int getOderID() {
        return OderID;
    }

    public void setOderID(int OderID) {
        this.OderID = OderID;
    }

    public String getMSKH() {
        return MSKH;
    }

    public void setMSKH(String MSKH) {
        this.MSKH = MSKH;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }
   
   
}
