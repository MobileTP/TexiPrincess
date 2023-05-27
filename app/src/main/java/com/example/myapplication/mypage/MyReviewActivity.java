package com.example.myapplication.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.NaviHeaderFragment;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

public class MyReviewActivity extends AppCompatActivity {

    private NaviHeaderFragment fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView reviewTop[];
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reivew);

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

//         final Review[] newReview = new Review[1];
//         //0 대신에 이메일 아이디로
//         database= FirebaseDatabase.getInstance().getReference().child("ID").child("0").child("Review");
//         database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//             @Override
//             public void onComplete(@NonNull Task<DataSnapshot> task) {
//                 if (task.isSuccessful()) {
//                     newReview[0] =task.getResult().getValue(Review.class);
//                 }
//             }
//         });
//         int[] review = newReview[0].getAll();
        int[] review={6,5,2,4,3,0};
        int[] top = {-1,-1,-1};
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
