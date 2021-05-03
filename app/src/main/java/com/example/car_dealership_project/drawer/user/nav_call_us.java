package com.example.car_dealership_project.drawer.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.car_dealership_project.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_call_us#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_call_us extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_call_us() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_call_us.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_call_us newInstance(String param1, String param2) {
        nav_call_us fragment = new nav_call_us();
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

        View view =  inflater.inflate(R.layout.fragment_nav_call_us, container, false);

        /* Real Code starts here */
        ImageButton dialButton = (ImageButton) view.findViewById(R.id.callButton);
        ImageButton gmailButton = (ImageButton) view.findViewById(R.id.mailButton);
        ImageButton mapsButton = (ImageButton) view.findViewById(R.id.mapButton);


        //Dial Button
        dialButton.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {
            Intent dialIntent =new Intent(); dialIntent.setAction(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:0599000000")); startActivity(dialIntent);
        } });

        //Mail Button
        gmailButton.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {
            Intent gmailIntent =new Intent();
            gmailIntent.setAction(Intent.ACTION_SENDTO);
            gmailIntent.setDataAndType(Uri.parse("mailto:"),"message/rfc822");
            gmailIntent.putExtra(Intent.EXTRA_EMAIL,"CarDealer@cars.com");
//            gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"My Subject");
//            gmailIntent.putExtra(Intent.EXTRA_TEXT,"Content of the message");
            startActivity(gmailIntent);
        } });
        //Map Button
        mapsButton.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {
            Intent mapsIntent =new Intent();
            mapsIntent.setAction(Intent.ACTION_VIEW);
            mapsIntent.setData(Uri.parse("geo:19.076,72.8777")); startActivity(mapsIntent);
        } });


        // Inflate the layout for this fragment
        return view ;
    }
}
