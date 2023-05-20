package com.example.myapplication.seungwon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class PayActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        EditText PaymentValue = findViewById(R.id.paymentValue);
        Button PaymentBtn = findViewById(R.id.paymentBtn);

        PaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "택공주";
                String price = PaymentValue.getText().toString();

                KakaoPayActivity kakaoPayActivity = new KakaoPayActivity(name, price);

                Intent intent = new Intent(getApplicationContext(), kakaoPayActivity.getClass());
                startActivity(intent);
            }
        });
    }
}

