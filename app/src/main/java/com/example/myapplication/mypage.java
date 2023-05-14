package com.example.myapplication;

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

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class mypage extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    TextView usingCount, saveCost, userName, userSex;
    RadioGroup userSeatGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        //DB에서 받아와야 함
        usingCount=findViewById(R.id.mypage_use_count);
        saveCost=findViewById(R.id.mypage_save_cost);
        userName=findViewById(R.id.User_name);
        userSex=findViewById(R.id.User_sex);
        //선호좌석 radio group
        userSeatGroup=findViewById(R.id.User_seat);

        //DB에서 정보를 받아오기
        /*
        usingCount.setText();
        saveCost.setText();
        userName.setText();
        userSex.setText();

        userSeatGroup.check();
         */

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
                        Intent intent = new Intent(getApplicationContext(), mypage.class);
                        startActivity(intent);
                        return true;

                    case R.id.menu_mytaxi:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        //내생택 리스트 생기면 바꿔주기~~~~~~~~
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.menu_myreview:
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        intent = new Intent(getApplicationContext(), my_review.class);
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
                        //선호좌석이 앞이라면 DB업데이트
                        break;
                    case R.id.User_seat_back: //1
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
}