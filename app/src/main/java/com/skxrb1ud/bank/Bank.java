package com.skxrb1ud.bank;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Bank {
    public String Address;
    public String TimeOpen;
    public String TimeClose;
    public String Time () {
        return TimeOpen + "-" + TimeClose;
    }
    public String Type;
    public Boolean Status;
    public String getStatusText() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        SimpleDateFormat db = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Date o = df.parse(TimeOpen);
            Date c = df.parse(TimeClose);
            Date n = df.parse(df.format(new Date()));
            Status = c.equals(df.parse("00:00")) && n.after(o) || n.after(o) && n.before(c);
        } catch (Exception e) {}
        return Status  ? "Работает" : "Закрыто";
    }
    public Bank () {}
    public  Bank (String address, String type, String openTime, String closeTime) {
        Address = address;
        Status = false;
        Type = type;
        TimeOpen = openTime;
        TimeClose = closeTime;
    }
}
