package com.example.bank;

public class CourseParams {
    public String Name;
    public int Nominal;
    public Float Price;
    public String CharCode;
    public Float k = 1.01f;
    public boolean isBuyUp = true;
    public boolean isSellUp = true;

    public CourseParams() {}

    public CourseParams(String name, int nominal, float price, String charcode) {
        Name = name;
        Nominal = nominal;
        Price = price;
        CharCode = charcode;
    }
    }
