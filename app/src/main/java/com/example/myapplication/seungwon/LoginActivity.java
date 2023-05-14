package com.example.myapplication.seungwon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Log.e("Debug", Utility.INSTANCE.getKeyHash(this));

        // SDK 초기화
        KakaoSdk.init(this, "551c0166c5ab89ca32fc99ab1b62e129");

//        textView = findViewById(R.id.textView);
        idValue = findViewById(R.id.idValue);
        pwValue = findViewById(R.id.pwValue);
//        profile = findViewById(R.id.imageView);
//        editTextName = findViewById(R.id.editName);
//        editTextPrice = findViewById(R.id.editPrice);

        // 로그인 버튼 클릭 이벤트
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
                                    } else if (user != null) {
                                        Log.e("Debug", "사용자 정보 받기 성공!");
                                        String nickName = user.getKakaoAccount().getProfile().getNickname();
//                                        Glide.with(profile).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profile);
//                                        textView.setText(nickName);
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

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