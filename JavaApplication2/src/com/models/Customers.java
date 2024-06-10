/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author nguye
 */
public class Customers {

    private String Name;
    private String Phone;
    private String Address;
    private String MSKH;

    public Customers(String Name, String Phone, String Address, String MSKH) {
        this.Name = Name;
        this.Phone = Phone;
        this.Address = Address;
        this.MSKH = MSKH;
    }

    public Customers() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getMSKH() {
        return MSKH;
    }

    public void setMSKH(String MSKH) {
        this.MSKH = MSKH;
    }

}
