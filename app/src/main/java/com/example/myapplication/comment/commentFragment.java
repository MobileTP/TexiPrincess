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

import java.util.ArrayList;

public class commentFragment extends Fragment {

    ArrayList<CommentData> commentList;
    ListView customListView;
    private static CommentAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.comment_fragment, container, false);

        commentList = new ArrayList<CommentData>();

        commentList.add(new CommentData(R.drawable.profile, "박현서","안녕하세요"));
        commentList.add(new CommentData(R.drawable.profile, "문희상","ㅎㅇㅎㅇ"));
        commentList.add(new CommentData(R.drawable.profile, "이승원","ㅎㅇㅎㅇ"));
        commentList.add(new CommentData(R.drawable.profile, "유인재","ㅎㅇㅎㅇ"));

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
                    // 입력된 값이 비어있지 않으면 토스트로 출력
                    Toast.makeText(getContext(), inputText, Toast.LENGTH_SHORT).show();
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