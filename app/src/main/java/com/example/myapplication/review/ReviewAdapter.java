package com.example.myapplication.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.SampleData;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> sample;

    public ReviewAdapter(Context context, ArrayList<SampleData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SampleData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.review_list, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.poster);
        TextView grade = (TextView)view.findViewById(R.id.grade);
//        Button btn = (Button) view.findViewById(R.id.reviewBtn);

        imageView.setImageResource(sample.get(position).getPoster());
        grade.setText(sample.get(position).getArrive());
//        btn.setText("리뷰하기");

        return view;
    }
}