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
import com.example.car_dealership_project.models.Car;
import com.example.car_dealership_project.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class CarReservationsAdapter extends RecyclerView.Adapter<CarReservationsAdapter.ViewHolder> {

    private List<Reservation> reservations;
    private DatabaseHelper databaseHelper;
    private Context context;

    public CarReservationsAdapter(Context context, List<Reservation> reservations){
        this.reservations = new ArrayList<Reservation>(reservations);
        this.context = context;
    }

    @NonNull
    @Override
    public CarReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_reserve,parent,false);
        databaseHelper = new DatabaseHelper(parent.getContext(), "Project", null, 1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarReservationsAdapter.ViewHolder holder, final int position) {
        String carName = reservations.get(position).getCar().getBrand();
        String model = reservations.get(position).getCar().getModel();
        String date = reservations.get(position).getReservedOn().toString();
        holder.setData(carName, model, date );
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView reserveTitle;
        private TextView reserveModel;
        private TextView reserveDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reserveTitle = itemView.findViewById(R.id.reserveTitle);
            reserveModel = itemView.findViewById(R.id.reserveModel);
            reserveDate = itemView.findViewById(R.id.reserveDate);
        }
        public void setData(String title, String model, String date){
            this.reserveTitle.setText(title);
            this.reserveModel.setText(model);
            this.reserveDate.setText(date);
        }
    }
}
