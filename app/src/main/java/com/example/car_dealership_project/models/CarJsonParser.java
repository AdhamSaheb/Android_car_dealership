package com.example.car_dealership_project.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CarJsonParser {


        public static ArrayList<Car> getObjectFromJason(String jason) {
            ArrayList<Car> cars;
            try {
                JSONArray jsonArray = new JSONArray(jason);
                cars = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = (JSONObject) jsonArray.get(i);
                    Car car = new Car();
                    car.setId(i);
                    car.setBrand(jsonObject.getString("make"));
                    car.setModel(jsonObject.getString("model"));
                    car.setYear(jsonObject.getInt("year"));
                    car.setPrice(jsonObject.getInt("price"));
                    car.setDistance(jsonObject.getString("distance"));
                    car.setAccidents(jsonObject.getBoolean("accidents"));
                    car.setOffers(jsonObject.getBoolean("offers"));
                    cars.add(car);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return cars;
        }

}
