package com.example.myapplication.mypage;

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

import com.example.myapplication.R;
import com.example.myapplication.boarding.BoardingActivity;
import com.example.myapplication.comment.commentFragment;

import java.util.List;
import java.util.Map;

public class MyTaxiDetailFragment extends Fragment implements View.OnClickListener {

    private Button commentBtn;
    private Toolbar toolbar;
    private TextView departtxt;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,cntTaxi,cntID,idx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_taxi_fragment, container, false);

        toolbar = rootView.findViewById(R.id.toolBar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        departtxt = rootView.findViewById(R.id.depart);
        TextView arrivetxt = rootView.findViewById(R.id.arrive);
        TextView timetxt = rootView.findViewById(R.id.time);
        TextView headtxt = rootView.findViewById(R.id.head);
//        TextView pricetxt = rootView.findViewById(R.id.price);

        Bundle bundle = getArguments();
        String depart = bundle.getString("depart");
        String arrive = bundle.getString("arrive");
        String time = bundle.getString("time");
        int head = bundle.getInt("head");
//        int price = bundle.getInt("price");
        idx = bundle.getInt("idx");
        TaxiList = (List<Map<String, Object>>[]) bundle.getSerializable("TaxiList");
        IDList = (List<Map<String, Object>>[]) bundle.getSerializable("IDList");
        IDindex = bundle.getInt("IDindex");
        cntTaxi=bundle.getInt("cntTaxi",0);
        cntID=bundle.getInt("cntID",0);

        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
//        pricetxt.setText(price + "원");

        commentBtn = rootView.findViewById(R.id.comment);
        commentBtn.setOnClickListener((View.OnClickListener) this);

        return rootView;
    }

    public void openfragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.to_bottom, R.anim.from_bottom).
                add(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    public void onClick(View view) {
        commentFragment CommentFragment = new commentFragment();
        switch (view.getId()) {
            case R.id.taxi_tagi:
                Intent intent = new Intent(getActivity(), BoardingActivity.class);
                startActivity(intent);
                break;
            case R.id.comment:
                Bundle bundle = new Bundle();
                bundle.putString("depart", String.valueOf(departtxt));
                bundle.putSerializable("TaxiList",TaxiList);
                bundle.putSerializable("IDList",IDList);
                bundle.putInt("IDindex",IDindex);
                bundle.putInt("cntTaxi",cntTaxi);
                bundle.putInt("cntID",cntID);
                bundle.putInt("idx",idx);
                openfragment(CommentFragment, bundle);
                break;
        }
    }

}
