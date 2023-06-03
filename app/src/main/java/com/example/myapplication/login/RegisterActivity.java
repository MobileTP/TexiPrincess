package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    int IDindex,cntTaxi,cntID;
    //파이어베이스 연동
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TaxiList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("TaxiList");
        IDList= (List<Map<String, Object>>[]) getIntent().getSerializableExtra("IDList");
        IDindex=getIntent().getIntExtra("IDindex",0);
        cntTaxi=getIntent().getIntExtra("cntTaxi",0);
        cntID=getIntent().getIntExtra("cntID",0);

        Log.d("FDB","Register "+IDList[0].toString()+" "+cntID);
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
                if(email.getText()!=null && name.getText()!=null && pw.getText()!=null && checkPw.getText()!=null){
                    for(tmp=0; tmp<cntID; tmp++){
                        String getEmail= String.valueOf(email.getText());
                        String getIDEmail= (String) IDList[0].get(tmp).get("Email");
                        if(getEmail==null)
                            Log.d("FDB","getEmail null");
                        else if(getIDEmail==null)
                            Log.d("FDB","getIDEmail null");

                        if(getIDEmail.equals(getEmail)){
                            Toast.makeText(RegisterActivity.this, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(tmp==cntID){
                        if(!pw.getText().toString().equals(checkPw.getText().toString())){
                            Toast.makeText(RegisterActivity.this, "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                            database= FirebaseDatabase.getInstance().getReference("ID");
                            database.child(String.valueOf(tmp)).child("Seat").setValue(1);
                            database.child(String.valueOf(tmp)).child("Email").setValue(email.getText().toString());
                            database.child(String.valueOf(tmp)).child("Mysang").setValue(new ArrayList<>());
                            database.child(String.valueOf(tmp)).child("Sex").setValue(registeruserSeat.isChecked()?0:1);
                            database.child(String.valueOf(tmp)).child("Count").setValue(0);
                            database.child(String.valueOf(tmp)).child("Review").setValue(new int[]{0, 0, 0, 0, 0, 0});
                            database.child(String.valueOf(tmp)).child("Image").setValue("null");
                            database.child(String.valueOf(tmp)).child("Cost").setValue(0);
                            database.child(String.valueOf(tmp)).child("Name").setValue(name.getText().toString());
                            database.child(String.valueOf(tmp)).child("Password").setValue(pw.getText().toString());
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

            }
        });
    }

}