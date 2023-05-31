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
import com.example.myapplication.comment.commentFragment;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.Map;

public class BogiDetailFragment extends Fragment implements View.OnClickListener {

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Button commentBtn;
    private Button tagiBtn;
    private Toolbar toolbar;
    private TextView departtxt;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bogi_detail, container, false);

//        mapView = new MapView(getContext());
//        mapView.removeAllPOIItems();
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
//        mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map);
//        mapViewContainer.addView(mapView);
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304), true);
//        mapView.setZoomLevel(4, true);
//        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("Default Marker");
//        marker.setTag(0);
//        marker.setMapPoint(MARKER_POINT);
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);

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
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                startActivity(intent);
                break;
            case R.id.comment:
                Bundle bundle = new Bundle();
                bundle.putString("depart", String.valueOf(departtxt));
                openfragment(CommentFragment, bundle);
                break;
        }
    }

}
