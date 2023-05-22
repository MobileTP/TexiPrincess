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

public class MyTaxiDetailFragment extends Fragment implements View.OnClickListener {

    private Button commentBtn;
    private Toolbar toolbar;
    private TextView departtxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_taxi_fragment, container, false);

        toolbar = rootView.findViewById(R.id.toolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        departtxt = rootView.findViewById(R.id.depart);
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

        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
        pricetxt.setText(price + "원");

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
                Toast.makeText(getContext(),"출근 완료",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BoardingActivity.class);
                startActivity(intent);
                break;
            case R.id.comment:
                Toast.makeText(getContext(),"comment",Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("depart", String.valueOf(departtxt));
                openfragment(CommentFragment, bundle);
                break;
        }
    }

}
