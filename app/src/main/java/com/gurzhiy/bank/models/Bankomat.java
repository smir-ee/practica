package com.gurzhiy.bank.models;

public class Bankomat {
    private String address;
    private String timings;
    private boolean status;

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
}
