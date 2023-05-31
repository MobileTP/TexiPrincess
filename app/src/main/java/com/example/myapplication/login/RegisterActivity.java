package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,pw,checkPw;
    RadioButton registeruserSeat;
    DatabaseReference database;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pw=findViewById(R.id.pw);
        checkPw=findViewById(R.id.checkPw);
        registeruserSeat=findViewById(R.id.register_User_seat_front);

        Button signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tmp=0;
                for(tmp=0; tmp<IDList[0].size(); tmp++){
                    if(IDList[0].get(tmp).get("Email").equals(email)){
                        Toast.makeText(RegisterActivity.this, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(tmp==IDList[0].size()){
                    if(!pw.equals(checkPw)){
                        Toast.makeText(RegisterActivity.this, "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                        database= FirebaseDatabase.getInstance().getReference("ID");
                        database.child(String.valueOf(tmp)).child("Seat").setValue(1);
                        database.child(String.valueOf(tmp)).child("Email").setValue(email.getText());
                        database.child(String.valueOf(tmp)).child("Mysang").setValue(new ArrayList<>());
                        database.child(String.valueOf(tmp)).child("Sex").setValue(registeruserSeat.isChecked()?0:1);
                        database.child(String.valueOf(tmp)).child("Count").setValue(0);
                        database.child(String.valueOf(tmp)).child("Review").setValue(new int[]{0, 0, 0, 0, 0, 0});
                        database.child(String.valueOf(tmp)).child("Image").setValue("null");
                        database.child(String.valueOf(tmp)).child("Cost").setValue(0);
                        database.child(String.valueOf(tmp)).child("Name").setValue(name.getText());
                        database.child(String.valueOf(tmp)).child("Password").setValue(pw.getText());
                        Map<String, Object> id = new HashMap<>();
                        id.put("MySang", new ArrayList<>());
                        id.put("Seat", 1);
                        id.put("Email", email.getText());
                        id.put("Sex", registeruserSeat.isChecked()?0:1);
                        id.put("Count", 0);
                        List<Integer> reviewList = new ArrayList<>();
                        for(int i=0; i<6; i++)
                            reviewList.add(0);
                        id.put("Review", reviewList);
                        id.put("Image", "null");
                        id.put("Cost",0);
                        id.put("Name", name.getText());
                        id.put("Password", pw.getText());
                        IDList[0].add(id);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("TaxiList",TaxiList);
                        intent.putExtra("IDList",IDList);
                        intent.putExtra("IDindex",tmp);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });
    }

}