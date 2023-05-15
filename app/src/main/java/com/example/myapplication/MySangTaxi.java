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
                Bundle bundle = new Bundle();
                bundle.putString("depart", myAdapter.getItem(position).getDepart());
                bundle.putString("arrive", myAdapter.getItem(position).getArrive());
                bundle.putString("time", myAdapter.getItem(position).getTime());
                bundle.putInt("head", myAdapter.getItem(position).getHeadCount());
                bundle.putInt("price", myAdapter.getItem(position).getPrice());
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

        movieDataList.add(new SampleData(R.drawable.logo, "가천대학교","태평역", "2023/05/01 10:55", 2, 24000));
        movieDataList.add(new SampleData(R.drawable.logo, "잠실역","강남역", "2023/05/02 11:55", 1, 10000));
        movieDataList.add(new SampleData(R.drawable.logo, "건대입구역","홍대입구역", "2023/05/03 12:55", 3, 34000));
        movieDataList.add(new SampleData(R.drawable.logo, "성수역","사당역", "2023/05/04 13:55", 1, 30000));
        movieDataList.add(new SampleData(R.drawable.logo, "석촌역","가천대역", "2023/05/05 14:55", 4, 12000));
    }
}