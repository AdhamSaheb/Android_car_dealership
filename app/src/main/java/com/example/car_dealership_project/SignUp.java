package com.example.car_dealership_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car_dealership_project.models.User;


public class SignUp extends AppCompatActivity {

    private static final String TOAST_TEXT = "User Created Successfully";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


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

        final Validator validator = new Validator(this, zipCode,  emailText,firstName, lastName, passwordText, confirmPasswordText, phoneText, genderSpinner, countrySpinner, citySpinner);
        /*Buttons*/
        Button back = (Button)findViewById(R.id.backButton);
        Button registerButton = (Button)findViewById(R.id.registerButton);
        /*Init spinners*/
        validator.setupSpinners();

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
                validator.validateString(firstName, "\n First name must be longer than 3 characters",errors);
                validator.validateString(lastName, "\n Last name must be longer than 3 characters",errors);
                validator.isEmailValid(errors);
                validator.isPasswordValid(errors);
                validator.isPasswordMatch(errors);
                validator.isNotInDataBase(errors);

                //After validation,check length of errors, if 0, then ok
                if(errors.getText().toString().length()==0){
                    User user = new User(emailText.getText().toString() , passwordText.getText().toString() ,
                            countrySpinner.getSelectedItem().toString(),
                            citySpinner.getSelectedItem().toString(),
                            genderSpinner.getSelectedItem().toString() ,
                            zipCode.getText().toString().concat(phoneText.getText().toString()),
                            firstName.getText().toString() , lastName.getText().toString(), false );
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

}
