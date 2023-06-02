package com.example.myapplication.review;

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

import com.example.myapplication.HomeActivity;
import com.example.myapplication.mypage.MyPageActivity;
import com.example.myapplication.mypage.MyReviewActivity;
import com.example.myapplication.mypage.MySangTaxiActivity;
import com.example.myapplication.NaviHeaderFragment;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadReviewActivity extends AppCompatActivity {

    private NaviHeaderFragment fragmentNavi;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Button prev, finish, btn4, btn5, btn6;
    DatabaseReference database;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,reviewID,cntTaxi,cntID,idx;
    TextView profile_name,profile_info;
    ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_review);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);
        reviewID=getIntent().getIntExtra("reviewID",0);
        cntTaxi=getIntent().getIntExtra("cntTaxi",0);
        cntID=getIntent().getIntExtra("cntID",0);

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

        NavigationView navi=(NavigationView)findViewById(R.id.navigationView);
        View view=navi.getHeaderView(0);

        profile_image=view.findViewById(R.id.profile_image);
        profile_name=view.findViewById(R.id.profile_name);
        profile_info=view.findViewById(R.id.profile_info);

        NaviHeaderFragment naviHeaderFragment= (NaviHeaderFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_profile);
//        ImageView frag_image=naviHeaderFragment.getView().findViewById(R.id.profile_image);
        TextView frag_name=naviHeaderFragment.getView().findViewById(R.id.profile_name);
        TextView frag_sex=naviHeaderFragment.getView().findViewById(R.id.profile_info);

        String userName = "TestName";
        String userSex = "TestSex";
        String reviewName = "TestName";
        String reviewSex = "TestSex";
        //DB 에서 읽고 네비바 내용 변경
        //Arraylist에서 null이라고 값 못읽음;
        if(IDList!=null)
            if(IDList[0].size()!=0){
                userName= (String) IDList[0].get(IDindex).get("Name");
                userSex= IDList[0].get(IDindex).get("Sex").equals("0")? "남자":"여자";
                reviewName = (String) IDList[0].get(reviewID).get("Name");
                reviewSex = IDList[0].get(reviewID).get("Sex").equals("0")? "남자":"여자";
            }


//        profile_image.setImageResource(IDList[0].get(0).get("Image").toString());
        profile_name.setText(userName);
        profile_info.setText(userSex);
//        frag_image.setImageResource();
        if(frag_name!=null)
            frag_name.setText(reviewName);
        if(frag_sex!=null)
            frag_sex.setText(reviewSex);
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

                Boolean[] btn= {btn1,btn2,btn3,btn4.isSelected(),btn5.isSelected(),btn6.isSelected()};

                database= FirebaseDatabase.getInstance().getReference("ID");
                DatabaseReference IDReview=database.child(String.valueOf(reviewID));
                Map<String, Object> IDUpdate=new HashMap<>();
                List<Integer> reviewList = new ArrayList<>();
                for (int i=0; i<6; i++) {
                    int reviewValue = (int) ((ArrayList)IDList[0].get(reviewID).get("Review")).get(i);
                    if(btn[i]==true)
                        reviewValue+=1;
                    reviewList.add(reviewValue);
                }
                IDUpdate.put("Review",reviewList);
                IDReview.updateChildren(IDUpdate);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                intent.putExtra("cntTaxi",cntTaxi);
                intent.putExtra("cntID",cntID);
                startActivity(intent);
                finish();
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
