package com.example.myapplication.review;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.BogiDetailFragment;
import com.example.myapplication.BogiListActivity;
import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.SampleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    ArrayList<SampleData> movieDataList;
    ArrayList<Button> btnList;
    private Toolbar toolbar;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex;
    TextView profile_name,profile_info;
    ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final ReviewAdapter myAdapter = new ReviewAdapter(this, movieDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Intent intent = new Intent(ReviewActivity.this, HomeActivity.class); // 여기 이용해서 리뷰 페이지로 이동하면 되고
                intent.putExtra("IDindex", myAdapter.getItem(position).getDepart()); // 여기 고객 정보 변수 담으면 되고
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id){
//                Toast.makeText(getApplicationContext(), myAdapter.getItem(position).getArrive(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //리뷰리스트에서 리뷰버튼 누르면, 그 눌린 사람 IDindex를 "reviewID"로 GoodReviewActivity로 보내줘야함
        //intent.putExtra("reviewID",reviewID); <이런 식으로
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

