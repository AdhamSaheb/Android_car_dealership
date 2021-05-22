package com.example.car_dealership_project.drawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.Validator;
import com.example.car_dealership_project.models.User;
import com.example.car_dealership_project.utils.BitmapService;
import com.example.car_dealership_project.utils.Utility;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Validator validator;
    private TextView email;
    private TextView firstName;
    private TextView lastName;
    private TextView country;
    private TextView city;
    private TextView phone;
    private TextView gender;

    private ImageView profile_image;

    private TextView zipCode;
    private TextView updateErrors ;
    private TextView passwordErrors ;
    /* Edit Texts */
    private EditText emailEdit ;
    private EditText firstNameEdit ;
    private EditText lastNameEdit ;
    private EditText passwordText ;
    private EditText confirmPasswordText ;
    private EditText phoneText ;
    /* Spinners */
    private Spinner countrySpinner ;
    private Spinner citySpinner ;

    private Button updateProfileButton;
    private Button updatePasswordButton;
    public nav_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_profile newInstance(String param1, String param2) {
        nav_profile fragment = new nav_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_nav_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile Page");
        final FragmentManager mngr = getFragmentManager();
        this.email = rootView.findViewById(R.id.name);
        this.firstName = rootView.findViewById(R.id.firstName);
        this.lastName = rootView.findViewById(R.id.lastName);
        this.city = rootView.findViewById(R.id.city);
        this.country = rootView.findViewById(R.id.country);
        this.phone = rootView.findViewById(R.id.phoneNumber);
        this.gender = rootView.findViewById(R.id.gender);

        profile_image = rootView.findViewById(R.id.profile_image);

        zipCode = rootView.findViewById(R.id.profileZipCode);
        /* Edit Texts */
        firstNameEdit = rootView.findViewById(R.id.firstNameEdit);
        lastNameEdit = rootView.findViewById(R.id.lastNameEdit);
        passwordText = rootView.findViewById(R.id.profileNewPassword);
        confirmPasswordText = rootView.findViewById(R.id.profileConfirmPassword);
        phoneText = rootView.findViewById(R.id.phoneNumberEdit);
        updateErrors = rootView.findViewById(R.id.updateProfileErrors);
        passwordErrors = rootView.findViewById(R.id.updatePasswordErrors);
        /* Spinners */
        countrySpinner = rootView.findViewById(R.id.profileCountryEdit);
        citySpinner = rootView.findViewById(R.id.profileCityEdit);
        /* Buttons */
        updateProfileButton = rootView.findViewById(R.id.updateProfileButton);
        updatePasswordButton = rootView.findViewById(R.id.updatePasswordButton);

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),"Project", null, 1);
        Utility uti = new Utility(getActivity());
        final String userEmail = uti.getEmail();
        User currUser = databaseHelper.getUser(userEmail);

        /* Init validator */
        validator = new Validator(getContext(),
                zipCode,
                null,
                firstNameEdit,
                lastNameEdit,
                passwordText,
                confirmPasswordText,
                phoneText,
                null,
                countrySpinner,
                citySpinner
                );

        validator.setupSpinners();

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateErrors.setText("");
                validator.validateString(firstNameEdit, "First name must be atleast 3 letters",updateErrors);
                validator.validateString(lastNameEdit, "First name must be atleast 3 letters", updateErrors);
                String city = citySpinner.getSelectedItem().toString();
                String country = countrySpinner.getSelectedItem().toString();
                String phone = phoneText.getText().toString();
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();
                if(updateErrors.getText().toString().length() == 0){
                    databaseHelper.updateUser(userEmail, city, country, phone, firstName, lastName);
                    if(mngr != null){
                       mngr.beginTransaction().detach(nav_profile.this).attach(nav_profile.this).commit();
                    }
                    Toast toast = Toast.makeText(view.getContext(), "Successfully updated info", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordErrors.setText("");
                validator.isPasswordValid(passwordErrors);
                validator.isPasswordMatch(passwordErrors);
                if(passwordErrors.getText().toString().length() == 0){
                    databaseHelper.updatePassword(userEmail, passwordText.getText().toString().trim() );
                    Toast toast = Toast.makeText(view.getContext(), "Successfully updated password", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


        //TODO :  do this
        profile_image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                 photoPickerIntent.setType("image/*");
                 startActivityForResult(photoPickerIntent, 100);
             }
       });


        String firstName = currUser.getFirstName();
        String lastName = currUser.getLastName();

        this.email.setText(currUser.getEmail());
        this.firstName.setText(firstName);
        this.lastName.setText(lastName);
        this.city.setText(currUser.getCity());
        this.country.setText(currUser.getCountry());
        this.phone.setText(currUser.getPhone());
        this.gender.setText(currUser.getGender());

        this.firstNameEdit.setText(firstName);
        this.lastNameEdit.setText(lastName);
        this.phoneText.setText(currUser.getPhone().substring(3));
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 100:
                if(resultCode == -1){
                    Uri selectedImage = imageReturnedIntent.getData();
                    BitmapService service = new BitmapService();
                    Bitmap map = null;
                    try {
                        map = service.decodeUri(selectedImage,getContext());
                        profile_image.setImageBitmap(map);
                        System.out.println("Here");
                    } catch (FileNotFoundException e) {
                        System.out.println("Here But Error");
                        e.printStackTrace();
                    }

                }
        }
    }


}


