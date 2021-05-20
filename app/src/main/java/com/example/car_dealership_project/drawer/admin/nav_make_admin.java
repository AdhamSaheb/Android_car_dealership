package com.example.car_dealership_project.drawer.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.Login;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.SignUp;
import com.example.car_dealership_project.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_make_admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_make_admin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_make_admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_make_admin.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_make_admin newInstance(String param1, String param2) {
        nav_make_admin fragment = new nav_make_admin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private static final String TOAST_TEXT = "User Created Successfully";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
                DatabaseHelper(getContext(),"Project",null,1);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_nav_make_admin, container, false);
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



        /*Texts*/
        final TextView zipCode= (TextView) view.findViewById(R.id.zipCode);
        final TextView errors= (TextView) view.findViewById(R.id.errorsText);
        /* Edit Texts */
        final EditText emailText = (EditText) view.findViewById(R.id.emailText) ;
        final EditText firstName = (EditText) view.findViewById(R.id.firstName) ;
        final EditText lastName = (EditText) view.findViewById(R.id.lastName) ;
        final EditText passwordText = (EditText) view.findViewById(R.id.passwordText) ;
        final EditText confirmPasswordText = (EditText) view.findViewById(R.id.confirmPassowrdText) ;
        final EditText phoneText = (EditText) view.findViewById(R.id.phoneText) ;
        /*Spinners*/
        final Spinner genderSpinner =(Spinner) view.findViewById(R.id.genderSpinner);
        final Spinner countrySpinner =(Spinner) view.findViewById(R.id.countrySpinner);
        final Spinner citySpinner =(Spinner) view.findViewById(R.id.citySpinner);
        /*Buttons*/

        Button registerButton = (Button)view.findViewById(R.id.registerButton);
        /*Set options*/
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, genderOptions);
        genderSpinner.setAdapter(objGenderArr);
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, countryOptions);
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
                            firstName.getText().toString() , lastName.getText().toString(), true );
                    System.out.println("User Created : \n"+user.toString());
                    DatabaseHelper dataBaseHelper =new
                            DatabaseHelper(getContext(),"Project",null,1);
                    //call create user function from database helper
                    dataBaseHelper.AddUser(user);

                    //show toast and navigate back to login
                    Toast toast =Toast.makeText(getContext(),
                            TOAST_TEXT,Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

        return view ;
    }


}
