package com.example.myapplication.mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public class MyPageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    TextView usingCount, saveCost, userName, userSex;
    RadioGroup userSeatGroup;
    RadioButton userSeatFront, userSeatBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        usingCount=findViewById(R.id.mypage_use_count);
        saveCost=findViewById(R.id.mypage_save_cost);
        userName=findViewById(R.id.User_name);
        userSex=findViewById(R.id.User_sex);

        userSeatGroup=findViewById(R.id.User_seat);
        userSeatFront=findViewById(R.id.User_seat_front);
        userSeatBack=findViewById(R.id.User_seat_back);

        getDB();

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hambuger); //왼쪽 상단 버튼 아이콘 지정

        navigationView=findViewById(R.id.navigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_mypage:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.menu_mytaxi:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        //내생택 리스트 생기면 바꿔주기~~~~~~~~
                        intent = new Intent(getApplicationContext(), MySangTaxiActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.menu_myreview:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

        //선화좌석 선택변경 시
        userSeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId){
                    case R.id.User_seat_front: //0
                        //추가
                        //선호좌석이 앞이라면 DB업데이트
                        break;
                    case R.id.User_seat_back: //1
                        //추가
                        //선호좌석이 뒤라면 DB업데이트
                        break;
                }
            }
        });

    }

    //injae
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
    //injae

    public void getDB(){
        usingCount=findViewById(R.id.mypage_use_count);
        saveCost=findViewById(R.id.mypage_save_cost);
        userName=findViewById(R.id.User_name);
        userSex=findViewById(R.id.User_sex);

        userSeatGroup=findViewById(R.id.User_seat);
        userSeatFront=findViewById(R.id.User_seat_front);
        userSeatBack=findViewById(R.id.User_seat_back);

        //추가
        //유저테이블에서 정보들 받아오기
        String DBusingCount="1";
        String DBsaveCost="3000";
        String DBuserName="유인재";
        String DBuserSex="남자";
        int DBuserSeatGroup=0;
        //연결

        usingCount.setText(DBusingCount);
        saveCost.setText(DBsaveCost);
        userName.setText(DBuserName);
        userSex.setText(DBuserSex);

        if(DBuserSeatGroup == 0){
            userSeatFront.setChecked(true);
            userSeatBack.setChecked(false);
        }else{
            userSeatFront.setChecked(false);
            userSeatBack.setChecked(true);
        }



    }
}