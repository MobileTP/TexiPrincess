package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class bogi_detailFragment extends Fragment {

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bogi_detail, container, false);

        toolbar = getActivity().findViewById(R.id.toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        Bundle bundle = getArguments();
        String depart = bundle.getString("depart");
        String arrive = bundle.getString("arrive");
        String time = bundle.getString("time");
        int head = bundle.getInt("head");
        int price = bundle.getInt("price");
        TextView departtxt = rootView.findViewById(R.id.depart);
        TextView arrivetxt = rootView.findViewById(R.id.arrive);
        TextView timetxt = rootView.findViewById(R.id.time);
        TextView headtxt = rootView.findViewById(R.id.head);
        TextView pricetxt = rootView.findViewById(R.id.price);
        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
        pricetxt.setText(price + "원");


        return rootView;
    }

}
