package com.skxrb1ud.bank.models;

public class Bankomat {
    private String address;
    private String timings;
    private boolean status;
    private double lt;
    private double lng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public boolean getStatus() {
        return status;
    }
    public String getStatusText() {
        return status ? "Работает" : "Закрыто";
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getLt() {
        return lt;
    }
    public void setLt(double lt) {
        this.lt = lt;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
}
