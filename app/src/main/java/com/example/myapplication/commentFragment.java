package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

        return rootView;
    }
}