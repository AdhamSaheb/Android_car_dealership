package com.example.car_dealership_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class AdminReservationsAdapter extends RecyclerView.Adapter<AdminReservationsAdapter.ViewHolder> {

    private List<Reservation> reservations;
    private DatabaseHelper databaseHelper;
    private Context context;

    public AdminReservationsAdapter(Context context, List<Reservation> reservations){
        this.reservations = new ArrayList<Reservation>(reservations);
        this.context = context;
    }

    @NonNull
    @Override
    public AdminReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_admin_reserve,parent,false);
        databaseHelper = new DatabaseHelper(parent.getContext(), "Project", null, 1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminReservationsAdapter.ViewHolder holder, final int position) {
        String carName = reservations.get(position).getCar().getBrand();
        String model = reservations.get(position).getCar().getModel();
        String date = reservations.get(position).getReservedOn();
        String email = reservations.get(position).getEmail();
        holder.setData(carName, model, date, email);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView reserveTitle;
        private TextView reserveModel;
        private TextView reserveDate;
        private TextView reserveEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reserveTitle = itemView.findViewById(R.id.reserveTitle);
            reserveModel = itemView.findViewById(R.id.reserveModel);
            reserveDate = itemView.findViewById(R.id.reserveDate);
            reserveEmail = itemView.findViewById(R.id.reserveEmail);
        }
        public void setData(String title, String model, String date, String email){
            this.reserveTitle.setText(title);
            this.reserveModel.setText(model);
            this.reserveDate.setText(date);
            this.reserveEmail.setText(email);
        }
    }
}
