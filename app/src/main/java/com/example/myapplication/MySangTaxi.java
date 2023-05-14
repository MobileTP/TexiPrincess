package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MySangTaxi extends AppCompatActivity {

//    DB에서 가져온 데이터 저장할 객체
    ArrayList<SampleData> movieDataList;

//    툴바
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sang_taxi);

//        툴바
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

//        객체 생성
        this.InitializeMovieData();

//        리스트뷰 찾기
        ListView listView = (ListView)findViewById(R.id.listView);
//        어댑터로 연결
        final MyAdapter myAdapter = new MyAdapter(this,movieDataList);

//        리스트뷰에 띄우기
        listView.setAdapter(myAdapter);

        Mytaxi_detailFragment mytaxi_detailFragment = new Mytaxi_detailFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getMovieName(),
                        Toast.LENGTH_SHORT).show();
//                클릭시 넘어갈 창 + 번들에 데이터 담기
                Bundle bundle = new Bundle();
                bundle.putString("name", myAdapter.getItem(position).getGrade());
                openfragment(mytaxi_detailFragment, bundle);
            }
        });

    }

    public void openfragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.to_right, R.anim.from_right).
                replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        movieDataList.add(new SampleData(R.drawable.logo, "미션임파서블","15세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "아저씨","19세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
        movieDataList.add(new SampleData(R.drawable.logo, "어벤져스","12세 이상관람가"));
    }
}