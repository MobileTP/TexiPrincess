package com.example.myapplication.boarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.review.GoodReviewActivity;
import com.example.myapplication.mypage.MyPageActivity;
import com.example.myapplication.mypage.MyReviewActivity;
import com.example.myapplication.mypage.MySangTaxiActivity;
import com.example.myapplication.NaviHeaderFragment;
import com.example.myapplication.payment.PayActivity;
import com.example.myapplication.R;
import com.example.myapplication.review.ReviewActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Map;

public class BoardingActivity extends AppCompatActivity {

    private NaviHeaderFragment fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Button pay, review;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,cntTaxi,cntID,idx;
    TextView profile_name,profile_info;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hambuger); //왼쪽 상단 버튼 아이콘 지정

        navigationView=findViewById(R.id.navigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        pay=findViewById(R.id.boarding_btn_payment);
        review=findViewById(R.id.boarding_btn_review);

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
                        intent.putExtra("cntTaxi",cntTaxi);
                        intent.putExtra("cntID",cntID);
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
                        intent.putExtra("cntTaxi",cntTaxi);
                        intent.putExtra("cntID",cntID);
                        startActivity(intent);
                        return true;

                    case R.id.menu_myreview:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                        intent.putExtra("TaxiList",TaxiList);
                        intent.putExtra("IDList",IDList);
                        intent.putExtra("IDindex",IDindex);
                        intent.putExtra("cntTaxi",cntTaxi);
                        intent.putExtra("cntID",cntID);
                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //추가 결제 클래스로 연결
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                intent.putExtra("cntTaxi",cntTaxi);
                intent.putExtra("cntID",cntID);
                startActivity(intent);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                intent.putExtra("cntTaxi",cntTaxi);
                intent.putExtra("cntID",cntID);
                intent.putExtra("idx",idx);
                startActivity(intent);
            }
        });

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