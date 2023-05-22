package com.example.myapplication.review;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.SampleData;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ArrayList<SampleData> movieDataList;
    ArrayList<Button> btnList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final ReviewAdapter myAdapter = new ReviewAdapter(this, movieDataList);

        listView.setAdapter(myAdapter);

    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        movieDataList.add(new SampleData(R.drawable.photo, "리뷰1","박현서", "2023/05/01 10:55", 2, 24000));
        movieDataList.add(new SampleData(R.drawable.logo, "리뷰2","이승원", "2023/05/02 11:55", 1, 10000));
        movieDataList.add(new SampleData(R.drawable.logo, "건대입구역","문재인", "2023/05/03 12:55", 3, 34000));
        movieDataList.add(new SampleData(R.drawable.logo, "성수역","유인재", "2023/05/04 13:55", 1, 30000));
    }
}

