package com.example.bezmetnyybank.entities;

import java.nio.DoubleBuffer;

public class ValuteEntity {
    private Integer NumCode;
    private String CharCode;
    private Integer Nominal;
    private String Name;
    private Double Value;

    public Integer getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public Integer getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }

    public Double getValue() {
        return Value;
    }

    public void setNumCode(Integer numCode) {
        NumCode = numCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public void setNominal(Integer nominal) {
        Nominal = nominal;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setValue(Double value) {
        Value = value;
    }
}
