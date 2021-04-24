package com.example.car_dealership_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final String TOAST_TEXT = "User Created Successfully";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*Spinner Options*/
        final String[] genderOptions = { "Male", "Female" };
        final String[] countryOptions = { "Palestine", "Jordan","France","Greece" };
        final String[] countryCodeOptions = { "+970", "+975" ,"+0022","+0011" };
        final String[][] cities = {
                { "Jerusalem", "Ramallah","Nablus","Hebron" },
                { "Amman", "Zarqa","Jerash","Irbid" },
                { "Paris", "lel","leon","Nantes" },
                { "Athens", "Patras","Rhodes","Sparti" },
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        /*Texts*/
        final TextView zipCode= (TextView) findViewById(R.id.zipCode);
        final TextView errors= (TextView) findViewById(R.id.errorsText);
        /* Edit Texts */
        final EditText emailText = (EditText) findViewById(R.id.emailText) ;
        final EditText firstName = (EditText) findViewById(R.id.firstName) ;
        final EditText lastName = (EditText) findViewById(R.id.lastName) ;
        final EditText passwordText = (EditText) findViewById(R.id.passwordText) ;
        final EditText confirmPasswordText = (EditText) findViewById(R.id.confirmPassowrdText) ;
        final EditText phoneText = (EditText) findViewById(R.id.phoneText) ;
        /*Spinners*/
        final Spinner genderSpinner =(Spinner) findViewById(R.id.genderSpinner);
        final Spinner countrySpinner =(Spinner) findViewById(R.id.countrySpinner);
        final Spinner citySpinner =(Spinner) findViewById(R.id.citySpinner);
        /*Buttons*/
        Button back = (Button)findViewById(R.id.backButton);
        Button registerButton = (Button)findViewById(R.id.registerButton);
        /*Set options*/
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, genderOptions);
        genderSpinner.setAdapter(objGenderArr);
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, countryOptions);
        countrySpinner.setAdapter(objCountryArr);
        /*Spinners on changed*/
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // change zip code
                zipCode.setText(countryCodeOptions[position]);
                final ArrayAdapter<String> objCityArr = new ArrayAdapter<>( parentView.getContext() ,R.layout.support_simple_spinner_dropdown_item, cities[position]);
                citySpinner.setAdapter(objCityArr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /* ----------- Actions --------- */
        //Back button Action
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Login.class);
                view.getContext().startActivity(intent);}
        });
        //Register button Action
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errors.setText("");
                //validate form
                if(firstName.getText().toString().length() < 3 ) errors.append("\n* First Name must Be longer than 3 characters");
                if(lastName.getText().toString().length() < 3 ) errors.append("\n* Last Name must Be longer than 3 characters");
                if(!isEmailValid(emailText.getText().toString()))  errors.append("\n* Email Not valid");
                if(!isPasswordValid(passwordText.getText().toString())) errors.append("\n* Password Not valid");
                if( !passwordText.getText().toString().equals(confirmPasswordText.getText().toString())  ) errors.append("\n* Passwords Do not Match");
                if( !isNotInDataBase(emailText.getText().toString())) errors.append("\n* Email already exists");
                //After validation,check length of errors, if 0, then ok
                if(errors.getText().toString().length()==0){
                    User user = new User(emailText.getText().toString() , passwordText.getText().toString() ,
                            countrySpinner.getSelectedItem().toString(),
                            citySpinner.getSelectedItem().toString(),
                            genderSpinner.getSelectedItem().toString() ,
                            zipCode.getText().toString().concat(phoneText.getText().toString()),
                            firstName.getText().toString() , lastName.getText().toString(), false );
                    System.out.println("User Created : \n"+user.toString());
                    DatabaseHelper dataBaseHelper =new
                            DatabaseHelper(getApplicationContext(),"Project",null,1);
                    //call create user function from database helper
                    dataBaseHelper.AddUser(user);

                    //show toast and navigate back to login
                    Toast toast =Toast.makeText(SignUp.this,
                            TOAST_TEXT,Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext() , Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }


    boolean isEmailValid(String email){
        final String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) return false ;
        return true;
    }

    boolean isPasswordValid(String password){
        if(!password.matches((".*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz].*"))) return false ;
        if(!password.matches((".*[0123456789].*"))) return false ;
        if(!password.matches((".*[!@#$%^&*()_+].*"))) return false ;
        if(password.length() < 5 ) return false ;
        return true;
    }

    public boolean isNotInDataBase(String Email) {
        DatabaseHelper dataBaseHelper =new
                DatabaseHelper(this,"Project",null,1);
        String ed = "";
        final Cursor allUserseEmails = dataBaseHelper.getAllUsers();
        while (allUserseEmails.moveToNext()) {
            ed = allUserseEmails.getString(0);
            if(ed.equals(Email)) {
                return false;
            }
        }
        return true;
    }


}
