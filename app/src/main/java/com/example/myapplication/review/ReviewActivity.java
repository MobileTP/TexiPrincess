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
    int IDindex,cntTaxi,cntID,idx;
    TextView profile_name,profile_info;
    ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);
        cntTaxi=getIntent().getIntExtra("cntTaxi",0);
        cntID=getIntent().getIntExtra("cntID",0);
        idx=getIntent().getIntExtra("idx",0);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final ReviewAdapter myAdapter = new ReviewAdapter(this, movieDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Intent intent = new Intent(ReviewActivity.this, GoodReviewActivity.class); // 여기 이용해서 리뷰 페이지로 이동하면 되고
                intent.putExtra("reviewID", myAdapter.getItem(position).getIdx()); // 여기 고객 정보 변수 담으면 되고
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                intent.putExtra("cntTaxi",cntTaxi);
                intent.putExtra("cntID",cntID);
                startActivity(intent);
            }
        });

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

        for(int i=0; i<((ArrayList)TaxiList[0].get(idx).get("User")).size(); i++){
            int ID= (int) ((ArrayList<?>) TaxiList[0].get(i).get("User")).get(i);
            String depart= (String) IDList[0].get(ID).get("Name");
            String arrive= depart;
            String time=(String) TaxiList[0].get(i).get("Time");
            int headCount= ((ArrayList)TaxiList[0].get(i).get("User")).size()+1;
            long price= (long) TaxiList[0].get(i).get("Cost");
            String idx= ID+"";
            if(!idx.equals(IDindex+""))
                movieDataList.add(new SampleData(R.drawable.profile, depart,arrive, time, headCount, (int) price,idx));

        }
//        movieDataList.add(new SampleData(R.drawable.photo, "리뷰1","박현서", "2023/05/01 10:55", 2, 24000, "0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "리뷰2","이승원", "2023/05/02 11:55", 1, 10000, "0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "건대입구역","문재인", "2023/05/03 12:55", 3, 34000, "0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "성수역","유인재", "2023/05/04 13:55", 1, 30000, "0"));
    }
}

