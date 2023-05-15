package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class commentFragment extends Fragment {

    ArrayList<CommentData> movieDataList;
    ListView customListView;
    private static CommentAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.comment_fragment, container, false);

        movieDataList = new ArrayList<CommentData>();

        movieDataList.add(new CommentData(R.drawable.logo, "가천대학교","태평역", "2023/05/01 10:55", 2, 24000));
        movieDataList.add(new CommentData(R.drawable.logo, "잠실역","강남역", "2023/05/02 11:55", 1, 10000));
        movieDataList.add(new CommentData(R.drawable.logo, "건대입구역","홍대입구역", "2023/05/03 12:55", 3, 34000));
        movieDataList.add(new CommentData(R.drawable.logo, "성수역","사당역", "2023/05/04 13:55", 1, 30000));
        movieDataList.add(new CommentData(R.drawable.logo, "석촌역","가천대역", "2023/05/05 14:55", 4, 12000));

        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CommentAdapter(getContext(), movieDataList);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.movieName).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

//    ArrayList<CommentData> movieDataList;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.comment_fragment, container, false);
//
//        this.InitializeMovieData();
//
//        ListView listView = (ListView)rootView.findViewById(R.id.listView);
//        final CommentAdapter myAdapter = new CommentAdapter(getActivity(), movieDataList);
//
//        Bundle bundle = getArguments();
//        String depart = bundle.getString("depart");
//
//        return rootView;
//    }
//
//    public void InitializeMovieData()
//    {
//        movieDataList = new ArrayList<CommentData>();
//
//        movieDataList.add(new CommentData(R.drawable.logo, "가천대학교","태평역", "2023/05/01 10:55", 2, 24000));
//        movieDataList.add(new CommentData(R.drawable.logo, "잠실역","강남역", "2023/05/02 11:55", 1, 10000));
//        movieDataList.add(new CommentData(R.drawable.logo, "건대입구역","홍대입구역", "2023/05/03 12:55", 3, 34000));
//        movieDataList.add(new CommentData(R.drawable.logo, "성수역","사당역", "2023/05/04 13:55", 1, 30000));
//        movieDataList.add(new CommentData(R.drawable.logo, "석촌역","가천대역", "2023/05/05 14:55", 4, 12000));
//    }
