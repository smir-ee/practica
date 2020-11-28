package com.morlag.wsbank.models;

public class Valute {
    public static final Double COEFFICIENT = 1.1;
    // Текущий коэффициент, пока разбираемся с api будет константой.

    private String NumCode;
    private String CharCode;
    private String Nominal;
    private String Name;
    private String Value;
    private String ID;

    public String getNumCode() { return NumCode; }
    public String getCharCode() { return CharCode; }
    public String getNominal() { return Nominal; }
    public String getName() { return Name; }
    public String getValue() { return Value; }
    public String getID() { return ID; }
    public void setNumCode(String NumCode) { this.NumCode = NumCode; }
    public void setCharCode(String CharCode) { this.CharCode = CharCode; }
    public void setNominal(String Nominal) { this.Nominal = Nominal; }
    public void setName(String Name) { this.Name = Name; }
    public void setValue(String Value) { this.Value = Value; }
    public void setID(String ID) { this.ID = ID; }
}