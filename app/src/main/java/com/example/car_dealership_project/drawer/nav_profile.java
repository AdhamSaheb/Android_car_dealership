package com.example.car_dealership_project.drawer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.User;
import com.example.car_dealership_project.utils.Utility;


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

    private TextView name;
    private TextView firstName;
    private TextView lastName;
    private TextView country;
    private TextView city;
    private TextView phone;
    private TextView gender;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_nav_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile Page");
        this.name = rootView.findViewById(R.id.name);
        this.firstName = rootView.findViewById(R.id.firstName);
        this.lastName = rootView.findViewById(R.id.lastName);
        this.city = rootView.findViewById(R.id.city);
        this.country = rootView.findViewById(R.id.country);
        this.phone = rootView.findViewById(R.id.phoneNumber);
        this.gender = rootView.findViewById(R.id.gender);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),"Project", null, 1);

        Utility uti = new Utility(getActivity());
        String email = uti.getEmail();
        User currUser = databaseHelper.getUser(email);
        String firstName = currUser.getFirstName();
        String lastName = currUser.getLastName();
        String name = firstName.substring(0,1).toUpperCase() + firstName.substring(1) + " "
                    + lastName.substring(0,1).toUpperCase() + lastName.substring(1);
        this.name.setText(name);
        this.firstName.setText(firstName);
        this.lastName.setText(lastName);
        this.city.setText(currUser.getCity());
        this.country.setText(currUser.getCountry());
        this.phone.setText(currUser.getPhone());
        this.gender.setText(currUser.getGender());
        return rootView;
    }



}
