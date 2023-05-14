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

public class Mytaxi_detailFragment extends Fragment {

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_taxi_fragment, container, false);

        toolbar = getActivity().findViewById(R.id.toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        Toast.makeText(getActivity().getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        TextView name1 = rootView.findViewById(R.id.departure);
        name1.setText(name);
        return rootView;
    }

}
