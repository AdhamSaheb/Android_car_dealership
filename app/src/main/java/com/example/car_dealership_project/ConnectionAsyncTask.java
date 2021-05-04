package com.example.car_dealership_project;

import android.app.Activity; import android.os.AsyncTask;

import com.example.car_dealership_project.models.Car;
import com.example.car_dealership_project.models.CarJsonParser;

import java.util.ArrayList;
import java.util.List;
public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String data = HttpManager.getData(params[0]);
            return data;
        }catch (Exception e) {
            throw new ArithmeticException("no Connection");
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            Car.cars = CarJsonParser.getObjectFromJason(s);
            //testing if the response is correct
            for (int i = 0; i < Car.cars.size(); i++) {
                System.out.println(Car.cars.get(i).toString());
            }
        }catch (Exception e ) {
            throw new ArithmeticException("no Connection");
        }
    }
}
