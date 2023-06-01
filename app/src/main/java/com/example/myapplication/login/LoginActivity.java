package com.example.myapplication.login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


// 로그인 창
public class LoginActivity extends AppCompatActivity {

    TextView textView;
    EditText idValue;
    EditText pwValue;
    ImageView profile;
    EditText editTextName;
    EditText editTextPrice;
    DatabaseReference database;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int cntTaxi;
    int cntID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //아이디 저장용 db
        SharedPreferences sharedPreferences = getSharedPreferences("LoginID",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


//        Log.e("Debug", Utility.INSTANCE.getKeyHash(this));

        // SDK 초기화
        KakaoSdk.init(this, "551c0166c5ab89ca32fc99ab1b62e129");

//        textView = findViewById(R.id.textView);
        idValue = findViewById(R.id.emailValue);
        pwValue = findViewById(R.id.pwValue);
//        profile = findViewById(R.id.imageView);
//        editTextName = findViewById(R.id.editName);
//        editTextPrice = findViewById(R.id.editPrice);

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
        //Firebase read


        // 카카오 로그인 버튼 클릭 이벤트
        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().loginWithKakaoAccount(getApplicationContext(), new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                        if (throwable != null) {
                            Log.e("Debug", "로그인 실패!");
                        } else if (oAuthToken != null) {
                            Log.e("Debug", "로그인 성공!");
                            // 로그인 성공 시 사용자 정보 받기
                            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                @Override
                                public Unit invoke(User user, Throwable throwable) {
                                    if (throwable != null) {
                                        Log.e("Deubg", "사용자 정보 받기 실패!");
                                        Log.d(TAG, "로그인이 안되어 있음");
                                    } else if (user != null) {
                                        Log.e("Debug", "사용자 정보 받기 성공!");
                                        String nickName = user.getKakaoAccount().getProfile().getNickname();
                                        Log.d(TAG, "invoke: id " + user.getId());
                                        Log.d(TAG, "invoke: email " + user.getKakaoAccount().getEmail());
                                        Log.d(TAG, "invoke: nickName " + user.getKakaoAccount().getProfile().getNickname());
                                        Log.d(TAG, "invoke: gender " + user.getKakaoAccount().getGender());
                                        Log.d(TAG, "invoke: age " + user.getKakaoAccount().getAgeRange());
                                        Log.d(TAG, "invoke: birthday " + user.getKakaoAccount().getBirthday());
//                                        Glide.with(profile).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profile);
//                                        textView.setText(nickName);

                                        int tmp=0;
                                        for(tmp=0; tmp<cntID[0]; tmp++){
                                            if(IDList[0].get(tmp).get("Email").toString().equals(idValue.getText().toString())){
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                intent.putExtra("TaxiList",TaxiList);
                                                intent.putExtra("IDList",IDList);
                                                intent.putExtra("IDindex",tmp);
                                                intent.putExtra("cntTaxi",cntTaxi[0]);
                                                intent.putExtra("cntID",cntID[0]);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        database= FirebaseDatabase.getInstance().getReference("ID");
                                        database.child(String.valueOf(tmp)).child("Seat").setValue(1);
                                        database.child(String.valueOf(tmp)).child("Email").setValue( user.getKakaoAccount().getEmail());
                                        database.child(String.valueOf(tmp)).child("Mysang").setValue(new ArrayList<>());
                                        database.child(String.valueOf(tmp)).child("Sex").setValue(user.getKakaoAccount().getGender().equals("남자")?0:1);
                                        database.child(String.valueOf(tmp)).child("Count").setValue(0);
                                        database.child(String.valueOf(tmp)).child("Review").setValue(new int[]{0, 0, 0, 0, 0, 0});
                                        database.child(String.valueOf(tmp)).child("Image").setValue("null");
                                        database.child(String.valueOf(tmp)).child("Cost").setValue(0);
                                        database.child(String.valueOf(tmp)).child("Name").setValue(user.getKakaoAccount().getProfile().getNickname());
                                        database.child(String.valueOf(tmp)).child("Password").setValue("null");
                                        Map<String, Object> id = new HashMap<>();
                                        id.put("MySang", new ArrayList<>());
                                        id.put("Seat", 1);
                                        id.put("Email", user.getKakaoAccount().getEmail());
                                        id.put("Sex", user.getKakaoAccount().getGender().equals("남자")?0:1);
                                        id.put("Count", 0);
                                        List<Integer> reviewList = new ArrayList<>();
                                        for(int i=0; i<6; i++)
                                            reviewList.add(0);
                                        id.put("Review", reviewList);
                                        id.put("Image", "null");
                                        id.put("Cost",0);
                                        id.put("Name", user.getKakaoAccount().getProfile().getNickname());
                                        id.put("Password", "null");
                                        IDList[0].add(id);
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.putExtra("TaxiList",TaxiList);
                                        intent.putExtra("IDList",IDList);
                                        intent.putExtra("IDindex",tmp);
                                        intent.putExtra("cntTaxi",cntTaxi[0]);
                                        intent.putExtra("cntID",cntID[0]);
                                        finish();
                                        startActivity(intent);
                                    }
                                    return null;
                                }
                            });
                        }
                        return null;
                    }
                });
            }
        });

        //일반 로그인 버튼
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tmp=0;
                Log.d("FDB","cntID:"+cntID[0]);
                for(tmp=0; tmp<cntID[0]; tmp++) {
                    String DBID = (String) IDList[0].get(tmp).get("Email");
                    String ETID = String.valueOf(idValue.getText());
                    String DBPW = (String) IDList[0].get(tmp).get("Password");
                    String ETPW = String.valueOf(pwValue.getText());
                    if (DBID.equals(ETID)) {
                        if (DBPW.equals(ETPW)) {
                            //아이디 저장(로그인 유지)
                            myEdit.putString("userID", ETID);
                            myEdit.commit();

                            if (IDList[0].get(tmp).get("Email").toString().equals(idValue.getText().toString())) {
                                if (IDList[0].get(tmp).get("Password").toString().equals(pwValue.getText().toString())) {

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("TaxiList", TaxiList);
                                    intent.putExtra("IDList", IDList);
                                    intent.putExtra("IDindex", tmp);
                                    intent.putExtra("cntTaxi", cntTaxi[0]);
                                    intent.putExtra("cntID", cntID[0]);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                        if (tmp == cntID[0])
                            Toast.makeText(LoginActivity.this, "등록된 이메일이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
        }});

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

//        private void updateKakaoLoginUi() {
//            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
//                @Override
//                public Unit invoke(User user, Throwable throwable) {
//                    // 로그인 되어있으면
//                    if(user != null){
//                        Log.d(TAG, "invoke: id" + user.getId());
//                        Log.d(TAG, "invoke: email" + user.getKakaoAccount().getEmail());
//                        Log.d(TAG, "invoke: nickName" + user.getKakaoAccount().getProfile().getNickname());
//                        Log.d(TAG, "invoke: gender" + user.getKakaoAccount().getGender());
//                        Log.d(TAG, "invoke: age" + user.getKakaoAccount().getAgeRange());
//                        Log.d(TAG, "invoke: birthday" + user.getKakaoAccount().getBirthday());
//                    }
//                    else {
//                        // 로그인 안되어있으면
//                        Log.d(TAG, "로그인이 안되어 있음");
//                    }
//                    return null;
//                }
//            });
//        }

//        Button button2 = findViewById(R.id.buttonPay);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // EditText에 입력한 상품 정보를 가져온다.
//                String name = editTextName.getText().toString();
//                String price = editTextPrice.getText().toString();
//
//                // 결제가 이루어지는 PayActivity를 생성한다.
//                // - 생성자를 이용하여 상품 정보를 입력한다.
//                PayActivity payActivity = new PayActivity(name, price);
//
//                // Intent로 새로운 Activity를 실행한다.
//                Intent intent = new Intent(getApplicationContext(), payActivity.getClass());
//                startActivity(intent);
//            }
//        });

    }

}