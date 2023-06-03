package com.example.myapplication.mypage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.Map;

public class MyTaxiDetailFragment extends Fragment implements View.OnClickListener {

    private MapView mapView;
    private ViewGroup mapViewContainer;
    double FromX,FromY,ToX,ToY;
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
        idx = Integer.parseInt(bundle.getString("idx"));
        TaxiList = (List<Map<String, Object>>[]) bundle.getSerializable("TaxiList");
        IDList = (List<Map<String, Object>>[]) bundle.getSerializable("IDList");
        IDindex = bundle.getInt("IDindex");
        cntTaxi=bundle.getInt("cntTaxi",0);
        cntID=bundle.getInt("cntID",0);

        FromX= (double) TaxiList[0].get(idx).get("FromX");
        FromY= (double) TaxiList[0].get(idx).get("FromY");
        ToX= (double) TaxiList[0].get(idx).get("ToX");
        ToY= (double) TaxiList[0].get(idx).get("ToY");

        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
//        pricetxt.setText(price + "원");

        commentBtn = rootView.findViewById(R.id.comment);
        commentBtn.setOnClickListener((View.OnClickListener) this);

        mapView = new MapView(getContext());
        mapView.removeAllPOIItems();
        mapView.removeAllPolylines();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map);
        mapViewContainer.addView(mapView);

        MapPoint MARKER_POINT_depart = MapPoint.mapPointWithGeoCoord(FromY, FromX);
        Log.e("MARKER_DEPART", "X: " + FromX + " Y: " + FromY);
        MapPOIItem marker_depart = new MapPOIItem();
        marker_depart.setItemName("departure");
        marker_depart.setTag(0);
        marker_depart.setMapPoint(MARKER_POINT_depart);
        MapPoint MARKER_POINT_arrive = MapPoint.mapPointWithGeoCoord(ToY, ToX);
        Log.e("MARKER_ARRIVE", "X: " + ToX + " Y: " + ToY);
        MapPOIItem marker_arrive = new MapPOIItem();
        marker_arrive.setItemName("arrival");
        marker_arrive.setTag(0);
        marker_arrive.setMapPoint(MARKER_POINT_arrive);
        marker_depart.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker_depart.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker_arrive.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        marker_arrive.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker_depart);
        mapView.addPOIItem(marker_arrive);

        MapPolyline polyline = new MapPolyline();
        polyline.setLineColor(Color.argb(128, 255, 0, 0));
        polyline.addPoint(MARKER_POINT_arrive);
        polyline.addPoint(MARKER_POINT_depart);
        mapView.addPolyline(polyline);

        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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
