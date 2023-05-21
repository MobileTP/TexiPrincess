package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ArrayList<SampleData> movieDataList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this, movieDataList);

        listView.setAdapter(myAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id){
//                Toast.makeText(getApplicationContext(), myAdapter.getItem(position).getArrive(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        movieDataList.add(new SampleData(R.drawable.logo, "리뷰1","태평역", "2023/05/01 10:55", 2, 24000));
        movieDataList.add(new SampleData(R.drawable.logo, "리뷰2","강남역", "2023/05/02 11:55", 1, 10000));
        movieDataList.add(new SampleData(R.drawable.logo, "건대입구역","홍대입구역", "2023/05/03 12:55", 3, 34000));
        movieDataList.add(new SampleData(R.drawable.logo, "성수역","사당역", "2023/05/04 13:55", 1, 30000));
        movieDataList.add(new SampleData(R.drawable.logo, "석촌역","가천대역", "2023/05/05 14:55", 4, 12000));
    }
}

