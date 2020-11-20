package com.oat.practica.entities;

import android.print.PrinterId;

public class EntityBanks {
    private String city;
    private String address;
    private String place;
    private String timeWork;
    private String type;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPlace() {
        return place;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public String getType() {
        return type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public void setType(String type) {
        this.type = type;
    }
}
