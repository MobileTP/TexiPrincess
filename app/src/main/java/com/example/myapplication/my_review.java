package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class my_review extends AppCompatActivity {

    private navi_header fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView reviewTop[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reivew);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hambuger); //왼쪽 상단 버튼 아이콘 지정

        navigationView=findViewById(R.id.navigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        reviewTop= new TextView[]{findViewById(R.id.Myreview_top_1), findViewById(R.id.Myreview_top_2), findViewById(R.id.Myreview_top_3)};

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
                        intent = new Intent(getApplicationContext(), MySangTaxi.class);
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

        ////사용자의 리뷰 중 top3를 찾기
        //추가 리뷰테이블에서 리뷰 별 개수 받아오기
        int[] review={6,5,3,1,0,0};
        int[] top={-1,-1,-1};
        int tmp = -1;
        int ord = 0;
        while(ord!=3) {
            for (int i = 0; i < 6; i++) {
                if (i == 0) {
                    tmp = -1;
                }

                if (i == top[0] || i == top[1])
                    continue;
                if (tmp < review[i]) {
                    tmp = review[i];
                    top[ord] = i;
                }
            }
            switch (top[ord]){
                case 0:
                    reviewTop[ord].setText("친절해요 "+review[top[ord]]);
                    break;
                case 1:
                    reviewTop[ord].setText("시간을 잘 지켜요 "+review[top[ord]]);
                    break;
                case 2:
                    reviewTop[ord].setText("말을 안 걸어요 "+review[top[ord]]);
                    break;
                case 3:
                    reviewTop[ord].setText("불친절해요 "+review[top[ord]]);
                    break;
                case 4:
                    reviewTop[ord].setText("시간을 안 지켜요 "+review[top[ord]]);
                    break;
                case 5:
                    reviewTop[ord].setText("말을 자꾸 걸어요 "+review[top[ord]]);
                    break;
            }

            ord++;
        }



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