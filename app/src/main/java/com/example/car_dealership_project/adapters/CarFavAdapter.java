package com.example.car_dealership_project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.ReserveDialog;
import com.example.car_dealership_project.models.Car;
import com.example.car_dealership_project.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class CarFavAdapter extends RecyclerView.Adapter<CarFavAdapter.ViewHolder> {

    private List<Car> carsList;
    private DatabaseHelper databaseHelper;
    private Context context;

    public CarFavAdapter(Context context, List<Car> cars){
        this.carsList = new ArrayList<Car>(cars);
        this.context = context;
    }

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
        holder.reserve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ReserveDialog dialog = new ReserveDialog(carsList.get(position));
                    FragmentManager mngr = ((AppCompatActivity) context).getSupportFragmentManager();
                    dialog.show(mngr, "Reserve dialog");
                }catch(Exception e){
                    Log.e("RESERVE", e.toString());
                }
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
            car_name = itemView.findViewById(R.id.user_email);
            model = itemView.findViewById(R.id.user_firstname);
            reserve_button = itemView.findViewById(R.id.fav_reserve_button);
            remove_button = itemView.findViewById(R.id.fav_remove_button);
        }
        public void setData(String carName, String model){
            this.car_name.setText(carName);
            this.model.setText(model);
        }
    }
}
