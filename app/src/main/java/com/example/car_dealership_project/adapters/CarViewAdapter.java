package com.example.car_dealership_project.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_dealership_project.R;
import com.example.car_dealership_project.models.Car;

import org.w3c.dom.Text;

import java.util.List;

public class CarViewAdapter extends RecyclerView.Adapter<CarViewAdapter.ViewHolder> {

    private List<Car> carsList;

    public CarViewAdapter(){
        this.carsList = Car.cars;
    }

    @NonNull
    @Override
    public CarViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewAdapter.ViewHolder holder, int position) {
        String carName = carsList.get(position).getBrand();
        String model = carsList.get(position).getModel();
        String price = carsList.get(position).getPriceFormated();
        int year = carsList.get(position).getYear();
        boolean accident = carsList.get(position).isAccidents();
        String distance = carsList.get(position).getDistance();
        holder.setData(carName,model,year,price,accident,distance);
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView car_name;
        private TextView price;
        private TextView model;
        private TextView accident;
        private TextView year;
        private TextView distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.car_name);
            price = itemView.findViewById(R.id.price);
            model = itemView.findViewById(R.id.model);
            accident = itemView.findViewById(R.id.accidents);
            year = itemView.findViewById(R.id.year);
            distance = itemView.findViewById(R.id.distance);
        }
        public void setData(String carName, String model, int year, String price, boolean accident, String distance){
            this.car_name.setText(carName);
            this.model.setText(model);
            this.year.setText(year + "");
            this.price.setText(price);
            this.accident.setText( accident ? "Yes" : "No");
            this.accident.setTextColor(accident ? Color.parseColor("#ff0000") : Color.parseColor("#00ff00"));
            this.distance.setText(distance);
        }
    }
}
