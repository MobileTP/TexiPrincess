package com.example.myapplication.review;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.mypage.MyPageActivity;
import com.example.myapplication.mypage.MyReviewActivity;
import com.example.myapplication.mypage.MySangTaxiActivity;
import com.example.myapplication.NaviHeaderFragment;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public class BadReviewActivity extends AppCompatActivity {

    private NaviHeaderFragment fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Button prev, finish, btn4, btn5, btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_review);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hambuger); //왼쪽 상단 버튼 아이콘 지정

        navigationView=findViewById(R.id.navigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        prev=findViewById(R.id.btn_review_prev);
        finish=findViewById(R.id.btn_review_finish);
        btn4=findViewById(R.id.review_btn_4);
        btn5=findViewById(R.id.review_btn_5);
        btn6=findViewById(R.id.review_btn_6);

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

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn4.setSelected(!btn4.isSelected());
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn5.setSelected(!btn5.isSelected());
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn6.setSelected(!btn6.isSelected());
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoodReviewActivity.class);
                startActivity(intent);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //추가 리뷰테이블 업데이트
                Boolean btn1 = getIntent().getBooleanExtra("btn1",false);
                Boolean btn2 = getIntent().getBooleanExtra("btn2",false);
                Boolean btn3 = getIntent().getBooleanExtra("btn3",false);
                //리뷰 테이블에 1개씩 증가
                if(btn4.isSelected())
                    ;
                if(btn5.isSelected())
                    ;
                if(btn6.isSelected())
                    ;
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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