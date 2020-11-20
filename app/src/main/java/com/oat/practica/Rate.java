package com.oat.practica;

public class Rate {
    public Float k = 1.01f;
    public String Name;
    public int Nominal;
    public float Price;
    public String Code;
    public Boolean isBuyUP = true;
    public Boolean isSellUP = true;
    public String getPriceBuy() {
        return String.format("%.2f", Price);
    }
    public String getPriceSell() {
        return String.format("%.2f", Price * k);
    }
    public String getNameText() {
        return Nominal + " " + Name;
    }
    public Rate () {}
    public Rate (String name, int nominal, float price, String code) {
        Name = name;
        Nominal = nominal;
        Price = price;
        Code = code;
    }
}