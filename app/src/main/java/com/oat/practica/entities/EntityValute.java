package com.oat.practica.entities;

public class EntityValute{
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private double Value;

    public String getName() {
        return Name;
    }

    public int getNominal() {
        return Nominal;
    }

    public String getCharCode() {
        return CharCode;
    }

    public double getValue() {
        return Value;
    }

    public int getNumCode() {
        return NumCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setValue(double value) {
        Value = value;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public void setNumCode(int numCode) {
        NumCode = numCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }
}
