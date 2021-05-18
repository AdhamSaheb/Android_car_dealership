package com.example.car_dealership_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utility {
    private static Context context;
    public final static String USER_PREFS = "SignedIn";
    public final static String PREFS = "CAR_DEALER";
    public Utility(Context context){
       this.context = context;
    }

    public static void setStr(String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS,0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        return prefs.getString(key,"DNF");
    }

    public static boolean isSignedIn(){
        //loads email from local preferences
        SharedPreferences prefs = context.getSharedPreferences(
                "SignedIn", Context.MODE_PRIVATE);
        if( prefs.getString("Email" , "Default").equals("Default") ) return false  ;
        return  true;
    }

    public static String getEmail(){
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        return prefs.getString("Email" , "Default");
    }
}
