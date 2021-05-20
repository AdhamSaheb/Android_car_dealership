package com.example.car_dealership_project.models;

import java.util.Date;

public class Reservation {

    Car car;
    String reservedOn;

    public Reservation() {
    }

    public Reservation(Car car, String date) {
        this.car = car;
        this.reservedOn = date;
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
}
