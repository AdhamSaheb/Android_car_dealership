package com.example.car_dealership_project.drawer.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.adapters.AdminReservationsAdapter;
import com.example.car_dealership_project.adapters.CarReservationsAdapter;
import com.example.car_dealership_project.utils.Utility;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_view_all_reserves#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_view_all_reserves extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_view_all_reserves() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AdminReservationsAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_view_all_reserves.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_view_all_reserves newInstance(String param1, String param2) {
        nav_view_all_reserves fragment = new nav_view_all_reserves();
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
        return inflater.inflate(R.layout.fragment_nav_view_all_reserves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.allReservesRecyclerView);
        layoutManager = new LinearLayoutManager( this.getContext() );
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper databaseHelper = new DatabaseHelper(getView().getContext(), "Project", null, 1);
        Utility uti = new Utility(getActivity());
        String email = uti.getEmail();
        adapter = new AdminReservationsAdapter(getView().getContext(), databaseHelper.getAllReservations());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
