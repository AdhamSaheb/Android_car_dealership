package com.example.car_dealership_project.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.models.Car;
import com.example.car_dealership_project.utils.Utility;

import java.util.List;

public class CarFavAdapter extends RecyclerView.Adapter<CarFavAdapter.ViewHolder> {

    private List<Car> carsList;
    private DatabaseHelper databaseHelper;

    public CarFavAdapter(List<Car> cars){this.carsList = cars;}

    @NonNull
    @Override
    public CarFavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_fav_grid,parent,false);
        databaseHelper = new DatabaseHelper(parent.getContext(), "Project", null, 1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarFavAdapter.ViewHolder holder, final int position) {
        String carName = carsList.get(position).getBrand();
        String model = carsList.get(position).getModel();
        holder.remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               databaseHelper.removeFavourite(Utility.getEmail(), carsList.get(position)) ;
               carsList.remove(carsList.get(position));
               notifyItemRemoved(position);
               notifyItemRangeChanged(position, getItemCount() - position);
            }
        });
        holder.setData(carName,model);
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView car_name;
        private TextView model;
        private Button reserve_button;
        private ImageButton remove_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.fav_car_brand);
            model = itemView.findViewById(R.id.fav_car_model);
            remove_button = itemView.findViewById(R.id.fav_remove_button);
            reserve_button = itemView.findViewById(R.id.fav_reserve_button);
        }
        public void setData(String carName, String model){
            this.car_name.setText(carName);
            this.model.setText(model);
        }
    }
}
