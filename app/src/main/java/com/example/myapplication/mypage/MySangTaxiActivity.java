package com.example.myapplication.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SampleData;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySangTaxiActivity extends AppCompatActivity {

//    DB에서 가져온 데이터 저장할 객체
    ArrayList<SampleData> movieDataList;

//    툴바
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex;
    TextView profile_name,profile_info;
    ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sang_taxi);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hambuger); //왼쪽 상단 버튼 아이콘 지정

        navigationView=findViewById(R.id.navigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        NavigationView navi=(NavigationView)findViewById(R.id.navigationView);
        View view=navi.getHeaderView(0);

        profile_image=view.findViewById(R.id.profile_image);
        profile_name=view.findViewById(R.id.profile_name);
        profile_info=view.findViewById(R.id.profile_info);

        String userName = "TestName";
        String userSex = "TestSex";
        //DB 에서 읽고 네비바 내용 변경
        //Arraylist에서 null이라고 값 못읽음;
        if(IDList!=null)
            if(IDList[0].size()!=0){
                userName= (String) IDList[0].get(IDindex).get("Name");
                userSex= IDList[0].get(IDindex).get("Sex").equals("0")? "남자":"여자";
            }


//        profile_image.setImageResource(IDList[0].get(0).get("Image").toString());
        profile_name.setText(userName);
        profile_info.setText(userSex);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_mypage:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        intent.putExtra("TaxiList",TaxiList);
                        intent.putExtra("IDList",IDList);
                        intent.putExtra("IDindex",IDindex);
                        startActivity(intent);
                        return true;

                    case R.id.menu_mytaxi:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        //내생택 리스트 생기면 바꿔주기~~~~~~~~
                        intent = new Intent(getApplicationContext(), MySangTaxiActivity.class);
                        intent.putExtra("TaxiList",TaxiList);
                        intent.putExtra("IDList",IDList);
                        intent.putExtra("IDindex",IDindex);
                        startActivity(intent);
                        return true;

                    case R.id.menu_myreview:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                        intent.putExtra("TaxiList",TaxiList);
                        intent.putExtra("IDList",IDList);
                        intent.putExtra("IDindex",IDindex);
                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

//        객체 생성
        this.InitializeMovieData();

//        리스트뷰 찾기
        ListView listView = (ListView)findViewById(R.id.listView);
//        어댑터로 연결
        final MyAdapter myAdapter = new MyAdapter(this,movieDataList);

//        리스트뷰에 띄우기
        listView.setAdapter(myAdapter);

        MyTaxiDetailFragment myTaxiDetailFragment = new MyTaxiDetailFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Bundle bundle = new Bundle();
                bundle.putString("depart", myAdapter.getItem(position).getDepart());
                bundle.putString("arrive", myAdapter.getItem(position).getArrive());
                bundle.putString("time", myAdapter.getItem(position).getTime());
                bundle.putInt("head", myAdapter.getItem(position).getHeadCount());
                bundle.putInt("price", myAdapter.getItem(position).getPrice());
                openfragment(myTaxiDetailFragment, bundle);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(androidx.core.view.GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}