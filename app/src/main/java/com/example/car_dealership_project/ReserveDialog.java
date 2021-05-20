package com.example.car_dealership_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.car_dealership_project.models.Car;

public class ReserveDialog extends DialogFragment {

    private TextView dialogBrand;
    private TextView  dialogModel;
    private TextView dialogPrice;
    private Car car;

    public ReserveDialog(Car car){
        super();
        this.car = car;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reserve_dialog,null);

        dialogBrand = view.findViewById(R.id.dialogBrand);
        dialogBrand.setText(this.car.getBrand());

        dialogModel = view.findViewById(R.id.dialogModel);
        dialogModel.setText(this.car.getModel());

        dialogPrice = view.findViewById(R.id.dialogPrice);
        dialogPrice.setText(this.car.getPriceFormated());

        builder.setView(view)
            .setTitle("Reserve car?")
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        return builder.create();
    }
}
