package com.example.car_dealership_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private static final String TOAST_TEXT = "Email Remembered Successfully";


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Components */
        final EditText Email = (EditText) findViewById(R.id.emailText);
        final EditText Password = (EditText)findViewById(R.id.passwordText);
        final TextView errorText= (TextView) findViewById(R.id.errorText);

        /*Check if email saved in local prefs */
        String savedEmail =loadEmailFromLocal();
        if (savedEmail != "Default")  Email.setText(savedEmail);


        /* Set Register Text as underlined and make it navigate to sign up activity */
        TextView textView= findViewById(R.id.signup);
        textView.setText(Html.fromHtml("<u>No Account ? Register</u>"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }

        });
        /* ------------------------------------------------------------------------------ */
        /* Login Button Action */
        //Back  button
        Button btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : still need to hash the password
                if(Email.getText()!=null && Password.getText()!=null  && isInDataBase(Email.getText().toString())) {
                    DatabaseHelper dataBaseHelper =new
                            DatabaseHelper(getApplicationContext(),"Project",null,1);
                    final Cursor EmailPassword = dataBaseHelper.getPasswordForEmail(Email.getText().toString());
                    while (EmailPassword.moveToNext()) {
                        //check the has of the given password to the hash in db
                        if(HashHelper.md5(Password.getText().toString()).equals(EmailPassword.getString(0))) {
                            /* save log in stats */
                            SharedPreferences.Editor editor = getSharedPreferences("SignedIn" , MODE_PRIVATE ).edit();
                            editor.putString("Email", Email.getText().toString()); // This will laterindicate that user is still logged in
                            editor.apply();
                            /*Navigate based on user  type */
                            final Cursor isAdmin = dataBaseHelper.isAdmin(Email.getText().toString());
                            if (isAdmin.moveToFirst()) {
                                System.out.println(isAdmin.getInt(0));
                                if(isAdmin.getInt(0)  == 0 ) {//user
                                    Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                                    startActivity(intent);
                                }
                                else{//admin
                                    //TODO : CHANGE TO HOME ADMIN
                                    Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                                    startActivity(intent);
                                }
                            }


                            //if remember me checked, save the email to local preferences
                            CheckBox isRemembered = findViewById(R.id.rememberBox);
                            if(isRemembered.isChecked()) {
                                saveEmailtoLocal(Email.getText().toString());
                                Toast toast =Toast.makeText(Login.this,
                                        TOAST_TEXT,Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else errorText.setText("Invalid Credentials");
                    }
                } else errorText.setText("Email Not Found");


                }
        });
    }

    /* ------------------------------------------------------------ Helper Functions ------------------------------------------------------------*/
    //saves email to local preferences
    void saveEmailtoLocal(String email){
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("savedEmail" , email );
        editor.apply();
    }

    //loads email from local preferences
    String loadEmailFromLocal(){
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("savedEmail" , "Default");
    }

    //checks if user exists
    public Boolean isInDataBase(String Email) {
        DatabaseHelper dataBaseHelper =new
                DatabaseHelper(this,"Project",null,1);
        String ed = "";
        final Cursor allUserseEmails = dataBaseHelper.getAllUsers();
        while (allUserseEmails.moveToNext()) {
            ed = allUserseEmails.getString(0);
            if(ed.equals(Email)) {
                return true;
            }
        }
        return false;
    }
}

