package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.mypage.MyAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BogiListActivity extends AppCompatActivity {

    ArrayList<SampleData> movieDataList;

    private Toolbar toolbar;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,cntTaxi,cntID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bogi_list);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);
        cntTaxi=getIntent().getIntExtra("cntTaxi",0);
        cntID=getIntent().getIntExtra("cntID",0);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this, movieDataList);

        listView.setAdapter(myAdapter);

        BogiDetailFragment BogiDetailFragment = new BogiDetailFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Bundle bundle = new Bundle();
                bundle.putString("depart", myAdapter.getItem(position).getDepart());
                bundle.putString("arrive", myAdapter.getItem(position).getArrive());
                bundle.putString("time", myAdapter.getItem(position).getTime());
                bundle.putInt("head", myAdapter.getItem(position).getHeadCount());
                bundle.putInt("price", myAdapter.getItem(position).getPrice());
                bundle.putString("idx",myAdapter.getItem(position).getIdx());
                bundle.putSerializable("TaxiList",TaxiList);
                bundle.putSerializable("IDList",IDList);
                bundle.putInt("IDindex",IDindex);
                bundle.putInt("cntTaxi",cntTaxi);
                bundle.putInt("cntID",cntID);
                openFragment(BogiDetailFragment, bundle);
            }
        });

    }

    public void openFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.to_right, R.anim.from_right).
                replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        for(int i=0; i<cntTaxi; i++){
            String depart= (String) TaxiList[0].get(i).get("From");
            String arrive= (String) TaxiList[0].get(i).get("To");
            String time=(String) TaxiList[0].get(i).get("Time");
            int headCount= ((ArrayList)TaxiList[0].get(i).get("User")).size()+1;
            long price= (long) TaxiList[0].get(i).get("Cost");
            String idx= String.valueOf(i);
            Log.d("Comment",idx+"");
            movieDataList.add(new SampleData(R.drawable.logo, depart,arrive, time, headCount, (int) price,idx));
        }
//        movieDataList.add(new SampleData(R.drawable.logo, "가천대학교","태평역", "2023/05/01 10:55", 2, 24000,"0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "잠실역","강남역", "2023/05/02 11:55", 1, 10000, "0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "건대입구역","홍대입구역", "2023/05/03 12:55", 3, 34000,"0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "성수역","사당역", "2023/05/04 13:55", 1, 30000,"0"));
//        movieDataList.add(new SampleData(R.drawable.logo, "석촌역","가천대역", "2023/05/05 14:55", 4, 12000,"0"));
    }

}