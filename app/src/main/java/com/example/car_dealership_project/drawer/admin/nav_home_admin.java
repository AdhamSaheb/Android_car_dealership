package com.example.car_dealership_project.drawer.admin;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.models.Reservation;
import com.example.car_dealership_project.utils.Utility;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_home_admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_home_admin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView usersNum;
    TextView resrvNum;
    TextView priceNum;
    DatabaseHelper databaseHelper;

    public nav_home_admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_home_admin.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_home_admin newInstance(String param1, String param2) {
        nav_home_admin fragment = new nav_home_admin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        View rootView = inflater.inflate(R.layout.fragment_nav_home_admin, container, false);
        this.usersNum = rootView.findViewById(R.id.userNum);
        this.priceNum = rootView.findViewById(R.id.priceNum);
        this.resrvNum = rootView.findViewById(R.id.resrvNum);
        databaseHelper = new DatabaseHelper(getContext(), "Project", null, 1);

        Cursor users = databaseHelper.getAllUsers();
        this.usersNum.setText(" Registered users: " + users.getCount());
        // Inflate the layout for this fragment

        List<Reservation> resvList = databaseHelper.getAllReservations();
        this.resrvNum.setText("Reservations: " + resvList.size());

        int sum = 0;
        for(Reservation resv : resvList) {
            sum += resv.getCar().getPrice();
        }
        this.priceNum.setText("Total earned: " + Utility.getFormatedNumber(sum));
        return rootView;
    }
}
