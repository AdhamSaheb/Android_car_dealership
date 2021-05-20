package com.example.car_dealership_project.models;

import java.util.Date;

public class Reservation {

    Car car;
    String reservedOn;
    String email;

    public Reservation() {
    }

    public Reservation(Car car, String date, String email) {
        this.car = car;
        this.reservedOn = date;
        this.email = email;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getReservedOn() {
        return reservedOn;
    }

    public void setReservedOn(String reservedOn) {
        this.reservedOn = reservedOn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
