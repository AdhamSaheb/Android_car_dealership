package com.example.car_dealership_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utility {
    private static Context context;
    public Utility(Context context){
       this.context = context;
    }

    public static boolean isSignedIn(){
        //loads email from local preferences
        SharedPreferences prefs = context.getSharedPreferences(
                "SignedIn", Context.MODE_PRIVATE);
        if( prefs.getString("Email" , "Default").equals("Default") ) return false  ;
        return  true;
    }

    public static String getEmail(){
        SharedPreferences prefs = context.getSharedPreferences(
                "SignedIn", Context.MODE_PRIVATE);
        return prefs.getString("Email" , "Default");
    }
}
