package com.skxrb1ud.bank.entities;

public class Entity_Valute {
    private Integer NumCode;
    private String CharCode;
    private Integer Nominal;
    private String Name;
    private Double Value;

    public Double getValue() {
        return Value;
    }

    public Integer getNominal() {
        return Nominal;
    }

    public Integer getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public String getName() {
        return Name;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNominal(Integer nominal) {
        Nominal = nominal;
    }

    public void setNumCode(Integer numCode) {
        NumCode = numCode;
    }

    public void setValue(Double value) {
        Value = value;
    }
}