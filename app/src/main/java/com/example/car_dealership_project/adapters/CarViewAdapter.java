package com.example.car_dealership_project.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class CarViewAdapter extends RecyclerView.Adapter<CarViewAdapter.ViewHolder> implements Filterable {

    private List<Car> carsList;
    DatabaseHelper dataBaseHelper;
    Utility uti;
    Context context;

    public CarViewAdapter(Context context){
        this.carsList = new ArrayList<Car>(Car.cars);
        this.context = context;
    }

    /* <------------ Recycler View Stuff ------------> */
    @NonNull
    @Override
    public CarViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_item,parent,false);
        dataBaseHelper = new DatabaseHelper(parent.getContext(), "Project", null,1);
        uti = new Utility(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarViewAdapter.ViewHolder holder, final int position) {
        String carName = carsList.get(position).getBrand();
        String model = carsList.get(position).getModel();
        String price = carsList.get(position).getPriceFormated();
        long priceTag = carsList.get(position).getPrice();
        int year = carsList.get(position).getYear();
        boolean accident = carsList.get(position).isAccidents();
        String distance = carsList.get(position).getDistance();

        holder.setData(carName,model,year,price, priceTag, accident,distance);
        holder.fav_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean result = dataBaseHelper.addFavourite(uti.getEmail(), carsList.get(position));
                if(result){
                    Toast toast = Toast.makeText(view.getContext(), "Added to favourites", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(view.getContext(), "Failed to add", Toast.LENGTH_LONG);
                    toast.show();
                }
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
        private ImageButton fav_button;
        private Button reserve_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.car_name);
            price = itemView.findViewById(R.id.price);
            model = itemView.findViewById(R.id.model);
            accident = itemView.findViewById(R.id.accidents);
            year = itemView.findViewById(R.id.year);
            distance = itemView.findViewById(R.id.distance);
            fav_button = itemView.findViewById(R.id.favourite_button);
            reserve_button = itemView.findViewById(R.id.reserve_button);
        }
        public void setData(String carName, String model, int year, String price, long priceRaw, boolean accident, String distance){
            this.car_name.setText(carName);
            this.model.setText(model);
            this.year.setText(year + "");
            this.price.setText(price);
            this.price.setTag(priceRaw);
            this.accident.setText( accident ? "Yes" : "No");
            this.accident.setTextColor(accident ? Color.parseColor("#ff0000") : Color.parseColor("#00ff00"));
            this.distance.setText(distance);
        }
    }

    /* <------------ Filtering Stuff ------------> */
    //Empty filter will not be used
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            }
        };
    }

    public Filter getFilter(Spinner spinner){
        return new CustomFilter(spinner);
    }

    class CustomFilter extends Filter {

        private Spinner spinner;
        public CustomFilter(Spinner spinner) {
            this.spinner = spinner;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String field = this.spinner.getSelectedItem().toString();
            List<Car> filteredCars = new ArrayList<Car>();

//            List<Car> firstPass = new ArrayList<Car>();
            if( charSequence == null || charSequence.length() == 0){
                filteredCars.addAll(Car.cars);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for( Car car: carsList){
                    String toCompare = (field.contains("Model") ? car.getModel().toLowerCase() : car.getBrand().toLowerCase() );
                    if(toCompare.contains(filterPattern))
                        filteredCars.add(car);
                }
            }
            FilterResults res = new FilterResults();
            res.values = filteredCars;
            return res;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            carsList.clear();
            carsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    }

}
