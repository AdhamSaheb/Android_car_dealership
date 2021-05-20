package com.example.car_dealership_project.adapters;

import android.content.Context;
import android.graphics.Paint;
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

public class CarOfferAdapter extends RecyclerView.Adapter<CarOfferAdapter.ViewHolder> {

    private List<Car> carsList;
    private DatabaseHelper databaseHelper;
    private Context context;

    public CarOfferAdapter(Context context, List<Car> cars){
        this.carsList = new ArrayList<Car>(cars);
        this.context = context;
    }

    @NonNull
    @Override
    public CarOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_offer,parent,false);
        databaseHelper = new DatabaseHelper(parent.getContext(), "Project", null, 1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarOfferAdapter.ViewHolder holder, final int position) {
        String carName = carsList.get(position).getBrand();
        String model = carsList.get(position).getModel();
        String prev = carsList.get(position).getPriceFormated();
        String after = "";

        // Generate random discount price
        int prevNum = carsList.get(position).getPrice();
        float discount = (int) (Math.random() * 35);
        String carTitle = carName + " " + (int)discount + "%";
        discount /= 100;
        int afterNum = prevNum - (int) (prevNum*discount);
        Log.e("PREV AFTER", prevNum + " " + afterNum);
        after = Utility.getFormatedNumber(afterNum);

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
        holder.setData(carTitle, model, prev, after);
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView offerTitle;
        private TextView offerModel;
        private TextView offerPrev;
        private TextView offerAfter;
        private Button reserve_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerTitle = itemView.findViewById(R.id.offerTitle);
            offerModel = itemView.findViewById(R.id.offerModel);
            offerPrev = itemView.findViewById(R.id.offerPrev);
            offerAfter = itemView.findViewById(R.id.offerAfter);
            reserve_button = itemView.findViewById(R.id.offerReserveButton);
        }
        public void setData(String title, String model, String prev, String after){
            this.offerTitle.setText(title);
            this.offerModel.setText(model);
            this.offerPrev.setText(prev);
            this.offerPrev.setPaintFlags(this.offerPrev.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            this.offerAfter.setText(after);
        }
    }
}
