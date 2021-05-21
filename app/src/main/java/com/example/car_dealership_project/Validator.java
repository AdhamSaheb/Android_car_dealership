package com.example.car_dealership_project;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

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

    Context context;

    /*Texts*/
    private TextView zipCode;
    /* Edit Texts */
    private EditText emailText ;
    private EditText firstName ;
    private EditText lastName ;
    private EditText passwordText ;
    private EditText confirmPasswordText ;
    private EditText phoneText ;
    /*Spinners*/
    private Spinner genderSpinner ;
    private Spinner countrySpinner ;
    private Spinner citySpinner ;

    public Validator(Context context, TextView zipCode, EditText emailText, EditText firstName, EditText lastName, EditText passwordText, EditText confirmPasswordText, EditText phoneText, Spinner genderSpinner, Spinner countrySpinner, Spinner citySpinner) {
        this.context = context;
        this.zipCode = zipCode;
        this.emailText = emailText;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordText = passwordText;
        this.confirmPasswordText = confirmPasswordText;
        this.phoneText = phoneText;
        this.genderSpinner = genderSpinner;
        this.countrySpinner = countrySpinner;
        this.citySpinner = citySpinner;
    }

    public void setupSpinners(){
        if(this.genderSpinner != null){
            ArrayAdapter<String> objGenderArr = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item, genderOptions);
            genderSpinner.setAdapter(objGenderArr);
        }
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item, countryOptions);
        countrySpinner.setAdapter(objCountryArr);
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
    }

    public void validateString(TextView tv, String msg, TextView errors){
        if(tv.getText().toString().length() < 3)
            errors.append(msg);
    }

    public void isEmailValid(TextView errors){
        final String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        //initialize the Pattern object
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.emailText.getText().toString());
        if (!matcher.matches())
             errors.append("\nInvalid email") ;
    }

    public void isPasswordValid(TextView errors){
        String password = this.passwordText.getText().toString();
        if(!password.matches((".*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz].*")) ||
                !password.matches((".*[0123456789].*"))  ||
                !password.matches((".*[!@#$%^&*()_+].*"))  ||
                password.length() < 5 ){
            errors.append("\nInvalid password");
        }
    }

    public void isPasswordMatch(TextView errors){
        if( !passwordText.getText().toString().equals(confirmPasswordText.getText().toString())  ) errors.append("\n* Passwords Do not Match");
    }



    public void isNotInDataBase(TextView errors) {
        String Email = emailText.getText().toString();
        DatabaseHelper dataBaseHelper =new
                DatabaseHelper(context,"Project",null,1);
        String ed = "";
        final Cursor allUserseEmails = dataBaseHelper.getAllUsers();
        while (allUserseEmails.moveToNext()) {
            ed = allUserseEmails.getString(0);
            if(ed.equals(Email)) {
                errors.append("\nEmail already exists");
            }
        }
    }
}
