package com.example.myapplication.comment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commentFragment extends Fragment {

    ArrayList<CommentData> commentList;
    ListView customListView;
    private static CommentAdapter customAdapter;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,cntTaxi,cntID,idx;
    DatabaseReference database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.comment_fragment, container, false);

        Bundle bundle = getArguments();
        idx = bundle.getInt("idx");
        TaxiList = (List<Map<String, Object>>[]) bundle.getSerializable("TaxiList");
        IDList = (List<Map<String, Object>>[]) bundle.getSerializable("IDList");
        IDindex = bundle.getInt("IDindex");
        cntTaxi=bundle.getInt("cntTaxi",0);
        cntID=bundle.getInt("cntID",0);

        commentList = new ArrayList<CommentData>();

        for(int i=0; i<((ArrayList)TaxiList[0].get(idx).get("Chat")).size(); i++){
            String content= (String) ((HashMap)((ArrayList<?>) TaxiList[0].get(idx).get("Chat")).get(i)).get("Content");
            String time= (String) ((HashMap)((ArrayList<?>) TaxiList[0].get(idx).get("Chat")).get(i)).get("Time");
            Object ID=  ((HashMap)((ArrayList<?>) TaxiList[0].get(idx).get("Chat")).get(i)).get("ID");

            if(ID instanceof  Long){
                long cID= (long) ID;
                String name= (String) IDList[0].get((int) cID).get("Name");
                commentList.add(new CommentData(R.drawable.profile, name,content, (int) cID,time));
            }else if(ID instanceof Integer){
                int cID= (int)ID;
                String name= (String) IDList[0].get(cID).get("Name");
                commentList.add(new CommentData(R.drawable.profile, name,content, cID,time));
            }
        }
//        commentList.add(new CommentData(R.drawable.profile, "박현서","안녕하세요",0,"2023/05/01 10:55"));
//        commentList.add(new CommentData(R.drawable.profile, "문희상","ㅎㅇㅎㅇ",0,"2023/05/01 10:55"));
//        commentList.add(new CommentData(R.drawable.profile, "이승원","ㅎㅇㅎㅇ",0,"2023/05/01 10:55"));
//        commentList.add(new CommentData(R.drawable.profile, "유인재","ㅎㅇㅎㅇ",0,"2023/05/01 10:55"));

        customListView = (ListView) rootView.findViewById(R.id.comment_listView_custom);
        customAdapter = new CommentAdapter(getContext(), commentList);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.name).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        EditText editText = rootView.findViewById(R.id.editText);
        ImageButton btn = rootView.findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();
                if (!inputText.isEmpty()) {
//                    // 입력된 값이 비어있지 않으면 토스트로 출력
//                    Toast.makeText(getContext(), inputText, Toast.LENGTH_SHORT).show();
                    database= FirebaseDatabase.getInstance().getReference("Taxi");
                    int cnt=((ArrayList)TaxiList[0].get(idx).get("Chat")).size();
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date date=new Date(System.currentTimeMillis());
                    String time=formatter.format(date);

                    HashMap chatInfo=new HashMap();
                    chatInfo.put("Content",inputText);
                    chatInfo.put("ID",IDindex);
                    chatInfo.put("Time",time);

                    database.child(String.valueOf(idx)).child("Chat").child(String.valueOf(cnt)).setValue(chatInfo);
                    ((ArrayList) TaxiList[0].get(idx).get("Chat")).add(chatInfo);
                    commentList.add(new CommentData(R.drawable.profile, (String) IDList[0].get((int) IDindex).get("Name"),inputText, (int) IDindex,time));
                    // EditText 초기화
                    editText.setText("");
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                }
            }
        });

        return rootView;
    }
}