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
import com.example.myapplication.database.ID;
import com.example.myapplication.database.Review;
import com.example.myapplication.mypage.MyPageActivity;
import com.example.myapplication.mypage.MyReviewActivity;
import com.example.myapplication.mypage.MySangTaxiActivity;
import com.example.myapplication.NaviHeaderFragment;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BadReviewActivity extends AppCompatActivity {

    private NaviHeaderFragment fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Button prev, finish, btn4, btn5, btn6;
    DatabaseReference database;
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

                final Review[] newReview = new Review[1];
                //0 대신에 이메일 아이디로
                database= FirebaseDatabase.getInstance().getReference().child("ID").child("0").child("Review");
                database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            newReview[0] =task.getResult().getValue(Review.class);
                        }
                    }
                });
                Map<String,Object> newMap=new HashMap<String,Object>();
                int newarr[]=newReview[0].getAll();
                if(btn1==true)
                    newarr[0]+=1;
                if(btn2==true)
                    newarr[1]+=1;
                if(btn3==true)
                    newarr[2]+=1;
                if(btn4.isSelected()==true)
                    newarr[3]+=1;
                if(btn5.isSelected()==true)
                    newarr[4]+=1;
                if(btn6.isSelected()==true)
                    newarr[5]+=1;

                newMap.put("0",newarr[0]);
                newMap.put("1",newarr[1]);
                newMap.put("2",newarr[2]);
                newMap.put("3",newarr[3]);
                newMap.put("4",newarr[4]);
                newMap.put("5",newarr[5]);
                database.updateChildren(newMap);

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