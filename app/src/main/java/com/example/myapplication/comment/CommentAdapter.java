package com.example.myapplication.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView tv_name;
        public TextView tv_comment;
        public ImageView iv_profile;
    }

    public CommentAdapter(Context context, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.comment_list_custom, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.comment);
        viewHolder.iv_profile = (ImageView) convertView.findViewById(R.id.profile);

        final CommentData comment = (CommentData) list.get(position);
        viewHolder.tv_name.setText(comment.getname());
        viewHolder.tv_comment.setText(comment.getcomment());
        Glide
                .with(context)
                .load(comment.getprofile())
                .into(viewHolder.iv_profile);
        viewHolder.tv_name.setTag(comment.getname());

        return convertView;
    }

}