package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.boarding.BoardingActivity;

import java.util.List;
import java.util.Map;

public class BogiDetailFragment extends Fragment implements View.OnClickListener {

    private Button commentBtn;
    private Button tagiBtn;
    private Toolbar toolbar;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bogi_detail, container, false);

        toolbar = rootView.findViewById(R.id.toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TextView departtxt = rootView.findViewById(R.id.depart);
        TextView arrivetxt = rootView.findViewById(R.id.arrive);
        TextView timetxt = rootView.findViewById(R.id.time);
        TextView headtxt = rootView.findViewById(R.id.head);
        TextView pricetxt = rootView.findViewById(R.id.price);

        Bundle bundle = getArguments();
        String depart = bundle.getString("depart");
        String arrive = bundle.getString("arrive");
        String time = bundle.getString("time");
        int head = bundle.getInt("head");
        int price = bundle.getInt("price");
        TaxiList = (List<Map<String, Object>>[]) bundle.getSerializable("TaxiList");
        IDList = (List<Map<String, Object>>[]) bundle.getSerializable("IDList");
        IDindex = bundle.getInt("IDindex");

        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
        pricetxt.setText(price + "원");

        tagiBtn = rootView.findViewById(R.id.taxi_tagi);
        commentBtn = rootView.findViewById(R.id.comment);
        tagiBtn.setOnClickListener((View.OnClickListener) this);
        commentBtn.setOnClickListener((View.OnClickListener) this);

        return rootView;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taxi_tagi:
                Toast.makeText(getContext(),"출근 완료",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BoardingActivity.class);
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                startActivity(intent);
                break;
            case R.id.comment:
                Toast.makeText(getContext(),"comment",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
