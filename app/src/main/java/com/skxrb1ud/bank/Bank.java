package com.skxrb1ud.bank;

public class Bank {
    public String Address;
    public String Time;
    public String Type;
    public Boolean Status;
    public String getStatusText() {
        return Status ? "Работает" : "Закрыто";
    }
    public Bank () {}
    public  Bank (String address, String time, String type, Boolean status) {
        Address = address;
        Time = time;
        Type = type;
        Status = status;
    }
}
