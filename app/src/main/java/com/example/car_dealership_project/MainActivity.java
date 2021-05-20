package com.example.car_dealership_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.car_dealership_project.models.User;

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
                try{
                    /* Connect to api and load data */
                    ConnectionAsyncTask connectionAsyncTask = new
                            ConnectionAsyncTask(MainActivity.this);
                    connectionAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
                    //Navigate
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
                }
                    //show Successful tost
                    Toast toast =Toast.makeText(MainActivity.this,
                            "Connection Successful, Data Loaded",Toast.LENGTH_SHORT);
                    toast.show();

                }catch (Exception e ) {
                    //show error tost
                    Toast toast =Toast.makeText(MainActivity.this,
                            "Connection Was Not Successful",Toast.LENGTH_SHORT);
                    toast.show();
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


