package com.example.car_dealership_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get started button
        Button button = (Button)findViewById(R.id.getStarted);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : CONNECT TO REST API
                DatabaseHelper dataBaseHelper =new
                        DatabaseHelper(getApplicationContext(),"Project",null,1);
                //check if there is a user logged in
                if(!isSignedIn()) {
                    Intent intent = new Intent(view.getContext(), Login.class);
                    view.getContext().startActivity(intent);
                }else{ // navigate based on type
                    /*Navigate*/
                    Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                    startActivity(intent);
                    /*
                    String email = loadEmailFromLocal() ;
                    final Cursor isAdmin = dataBaseHelper.isAdmin(email);
                    if (isAdmin.moveToFirst()) {
                        //System.out.println(isAdmin.getInt(0));
                        if(isAdmin.getInt(0)  == 0 ) {//user
                            Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                            startActivity(intent);
                        }
                        else{//admin
                            //TODO: CHANGE TO HOME ADMIN
                            Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                            startActivity(intent);
                        }
                    }*/
                }

            }
            });
        //Set Animation for car image
        ImageView imageView = (ImageView) findViewById(R.id.carImage);
        imageView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate));

        //Create Admin User manually and create database called Project
        User user = new User("Admin@cars.com" , "Admin","Jerusalem", "Palestine", "male"
                , "0546218681" , "Adham" ,"Saheb" ,  true);

        DatabaseHelper dataBaseHelper =new
                DatabaseHelper(this,"Project",null,1);
        dataBaseHelper.AddUser(user);
    }

    //checks local preferences to see if already logged in
    boolean isSignedIn(){
        //loads email from local preferences
            SharedPreferences prefs = this.getSharedPreferences(
                    "SignedIn", Context.MODE_PRIVATE);
            if( prefs.getString("Email" , "Default").equals("Default") ) return false  ;
            return  true;
    }
    //loads email from local preferences
    String loadEmailFromLocal(){
        SharedPreferences prefs = this.getSharedPreferences(
                "SignedIn", Context.MODE_PRIVATE);
        return prefs.getString("Email" , "Default");
    }
}


