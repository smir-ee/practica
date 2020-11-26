package com.skxrb1ud.bank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Banks {
    public String Address;
    public String FullAddress;
    public String Description;
    public String TimeOpen;
    public String TimeClose;
    public String Time() { return TimeOpen + "-" + TimeClose;}
    public double Lat;
    public double Lng;
    public boolean Status;
    public String getStatusText(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date o = df.parse(TimeOpen);
            Date c = df.parse(TimeClose);
            Date n = df.parse(df.format(new Date()));
            Status = c.equals(df.parse("00:00")) && n.after(o) || n.after(o) && n.before(c);
        } catch (Exception e) {}
        return Status ? "Работает" : "Закрыто";
    }
    public Banks(String address, String fullAddress, String description, String timeOpen, String timeClose, double lt, double lng) {
        Address = address;
        Status = false;
        TimeOpen = timeOpen;
        TimeClose = timeClose;
        Lat = lt;
        Lng = lng;
        FullAddress = fullAddress;
        Description = description;
    }
}
