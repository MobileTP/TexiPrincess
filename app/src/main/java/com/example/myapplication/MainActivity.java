package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000; // 3초
    DatabaseReference database;
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginID",MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        //Firebase read
        database= FirebaseDatabase.getInstance().getReference();
        DatabaseReference taxiRef = database.child("Taxi");

        List<Map<String, Object>>[] TaxiList = new List[]{new ArrayList<>()};
        List<Map<String, Object>>[] IDList = new List[]{new ArrayList<>()};
        int[] cntTaxi=new int[1];
        int[] cntID=new int[1];

        taxiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Map<String, Object>> taxiList = new ArrayList<>();
                int cnttaxi=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cnttaxi++;
                    Map<String, Object> taxi = new HashMap<>();

                    // "User" 데이터 읽어오기
                    DataSnapshot userSnapshot = snapshot.child("User");
                    List<Integer> userList = new ArrayList<>();
                    for (DataSnapshot user : userSnapshot.getChildren()) {
                        int userId = user.getValue(Integer.class);
                        userList.add(userId);
                    }
                    taxi.put("User", userList);

                    // "Chat" 데이터 읽어오기
                    DataSnapshot chatSnapshot = snapshot.child("Chat");
                    List<Map<String, Object>> chatList = new ArrayList<>();
                    for (DataSnapshot chat : chatSnapshot.getChildren()) {
                        Map<String, Object> chatMap = new HashMap<>();
                        chatMap.put("Content", chat.child("Content").getValue());
                        chatMap.put("Time", chat.child("Time").getValue());
                        chatMap.put("ID", chat.child("ID").getValue());
                        chatList.add(chatMap);
                    }
                    taxi.put("Chat", chatList);

                    // 나머지 데이터 읽어오기
                    taxi.put("Time", snapshot.child("Time").getValue());
                    taxi.put("From", snapshot.child("From").getValue());
                    taxi.put("To", snapshot.child("To").getValue());
                    taxi.put("Admin", snapshot.child("Admin").getValue());
                    taxi.put("Cost", snapshot.child("Cost").getValue());

                    taxiList.add(taxi);
                }

                // "ID" 데이터 읽어오기
                DatabaseReference idRef = database.child("ID");
                int finalCnttaxi = cnttaxi;
                idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Map<String, Object>> idList = new ArrayList<>();
                        int cntid=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            cntid++;
                            Map<String, Object> id = new HashMap<>();

                            // "MySang" 데이터 읽어오기
                            DataSnapshot mySangSnapshot = snapshot.child("MySang");
                            List<Integer> mySangList = new ArrayList<>();
                            for (DataSnapshot mySang : mySangSnapshot.getChildren()) {
                                int mySangValue = mySang.getValue(Integer.class);
                                mySangList.add(mySangValue);
                            }
                            id.put("MySang", mySangList);

                            // 나머지 데이터 읽어오기
                            id.put("Seat", snapshot.child("Seat").getValue());
                            id.put("Email", snapshot.child("Email").getValue());
                            id.put("Sex", snapshot.child("Sex").getValue());
                            id.put("Count", snapshot.child("Count").getValue());

                            // "Review" 데이터 읽어오기
                            DataSnapshot reviewSnapshot = snapshot.child("Review");
                            List<Integer> reviewList = new ArrayList<>();
                            for (DataSnapshot review : reviewSnapshot.getChildren()) {
                                int reviewValue = review.getValue(Integer.class);
                                reviewList.add(reviewValue);
                            }
                            id.put("Review", reviewList);

                            id.put("Image", snapshot.child("Image").getValue());
                            id.put("Cost", snapshot.child("Cost").getValue());
                            id.put("Name", snapshot.child("Name").getValue());
                            id.put("Password", snapshot.child("Password").getValue());

                            idList.add(id);
                        }

                        TaxiList[0] = taxiList;
                        IDList[0] = idList;
                        cntTaxi[0] = finalCnttaxi;
                        cntID[0] =cntid;
                        // 정리된 데이터 출력 예시
                        Log.d("FDB","Taxi data:");
                        for (Map<String, Object> taxi : TaxiList[0]) {
                            Log.d("FDB",taxi+"");
                        }

                        Log.d("FDB","ID data:");
                        for (Map<String, Object> id : IDList[0]) {
                            Log.d("FDB",id+"");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("ID data read failed: " + databaseError.getCode());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Taxi data read failed: " + databaseError.getCode());
            }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(userID != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("TaxiList", TaxiList);
                    intent.putExtra("IDList", IDList);
                    intent.putExtra("IDindex", userID);
                    intent.putExtra("cntTaxi", cntTaxi[0]);
                    intent.putExtra("cntID", cntID[0]);
                    startActivity(intent);
                    finish();
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                finish();
//                startActivity(i);

            }
        }, SPLASH_TIME_OUT);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }


}

