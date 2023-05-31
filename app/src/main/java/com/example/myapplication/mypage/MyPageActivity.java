package com.example.myapplication.mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    TextView usingCount, saveCost, userName, userSex;
    RadioGroup userSeatGroup;
    RadioButton userSeatFront, userSeatBack;
    DatabaseReference database;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex;
    TextView profile_name,profile_info;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);

        usingCount=findViewById(R.id.mypage_use_count);
        saveCost=findViewById(R.id.mypage_inbox_cost);
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

        //선화좌석 선택변경 시
        userSeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                database=FirebaseDatabase.getInstance().getReference("ID");
                DatabaseReference IDSeat=database.child(String.valueOf(IDindex));
                Map<String, Object> IDSeatUpdate=new HashMap<>();

                switch(checkedId){
                    case R.id.User_seat_front: //0
                        //선호좌석이 앞이라면 DB업데이트
                        IDSeatUpdate.put("Seat",0);
                        IDSeat.updateChildren(IDSeatUpdate);
                        break;
                    case R.id.User_seat_back: //1
                        //선호좌석이 뒤라면 DB업데이트
                        IDSeatUpdate.put("Seat",1);
                        IDSeat.updateChildren(IDSeatUpdate);
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
        saveCost=findViewById(R.id.mypage_inbox_cost);
        userName=findViewById(R.id.User_name);
        userSex=findViewById(R.id.User_sex);

        userSeatGroup=findViewById(R.id.User_seat);
        userSeatFront=findViewById(R.id.User_seat_front);
        userSeatBack=findViewById(R.id.User_seat_back);

         Long DBusingCount= (Long) IDList[0].get(IDindex).get("Count");
         Long DBsaveCost= (Long) IDList[0].get(IDindex).get("Cost");
         String DBuserName= (String) IDList[0].get(IDindex).get("Name");
         String DBuserSex= IDList[0].get(IDindex).get("Sex").equals("0")? "남자":"여자";
        Long DBuserSeatGroup= (Long) IDList[0].get(IDindex).get("Seat");

        usingCount.setText("내생택 " + DBusingCount + "회");
        saveCost.setText(DBsaveCost + "원");
        userName.setText(DBuserName);
        userSex.setText(DBuserSex);

        if(DBuserSeatGroup == 0){
            userSeatFront.setChecked(true);
            userSeatBack.setChecked(false);
        }else{
            userSeatFront.setChecked(false);
            userSeatBack.setChecked(true);
        }


        NavigationView navi=(NavigationView)findViewById(R.id.navigationView);
        View view=navi.getHeaderView(0);

        profile_image=view.findViewById(R.id.profile_image);
        profile_name=view.findViewById(R.id.profile_name);
        profile_info=view.findViewById(R.id.profile_info);

        String userName = "TestName";
        String userSex = "TestSex";
        //DB 에서 읽고 네비바 내용 변경
        //Arraylist에서 null이라고 값 못읽음;
        if(IDList[0].size()!=0){
            userName= (String) IDList[0].get(IDindex).get("Name");
            userSex= IDList[0].get(IDindex).get("Sex").equals("0")? "남자":"여자";
        }


//        profile_image.setImageResource(IDList[0].get(0).get("Image").toString());
        profile_name.setText(userName);
        profile_info.setText(userSex);

    }
}
