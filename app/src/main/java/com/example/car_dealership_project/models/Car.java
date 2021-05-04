package com.example.car_dealership_project.models;

import java.util.ArrayList;

public class Car {

    private String brand ;
    private String model ;
    private String distance ; // because it has "km" in response
    private int year ;
    private int price;
    private boolean accidents;
    private boolean offers;
    public static ArrayList<Car> cars = new ArrayList<>() ;

    public Car() {
    }

    public Car(String brand, String model, String distance, int year, int price, boolean accidents, boolean offers) {
        this.brand = brand;
        this.model = model;
        this.distance = distance;
        this.year = year;
        this.price = price;
        this.accidents = accidents;
        this.offers = offers;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDistance() {
        return distance;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAccidents() {
        return accidents;
    }

    public boolean isOffers() {
        return offers;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAccidents(boolean accidents) {
        this.accidents = accidents;
    }

    public void setOffers(boolean offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", distance='" + distance + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", accidents=" + accidents +
                ", offers=" + offers +
                '}';
    }
}
