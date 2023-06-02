package com.example.myapplication;

import android.content.Intent;
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

import com.example.myapplication.boarding.BoardingActivity;
import com.example.myapplication.comment.commentFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BogiDetailFragment extends Fragment implements View.OnClickListener, MapView.CurrentLocationEventListener, MapView.MapViewEventListener  {

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Button commentBtn;
    private Button tagiBtn;
    private Toolbar toolbar;
    private TextView departtxt;
    List<Map<String, Object>>[] TaxiList;
    List<Map<String, Object>>[] IDList;
    int IDindex,cntTaxi,cntID,idx;
    DatabaseReference database;
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

        //지도 띄우기
//        initMapView();

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

        departtxt.setText(depart);
        arrivetxt.setText(arrive);
        timetxt.setText(time);
        headtxt.setText(head + "/4");
//        pricetxt.setText(price + "원");

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
                Toast.makeText(getContext(),"택타기",Toast.LENGTH_SHORT).show();

                database= FirebaseDatabase.getInstance().getReference("Taxi");
                DatabaseReference TaxiUser=database.child(String.valueOf(idx));
                Map<String, Object> Update=new HashMap<>();
                ArrayList UserList = new ArrayList<>();
                for (int i=0; i<((ArrayList)TaxiList[0].get(idx).get("User")).size(); i++) {
                    int reviewValue = (int) ((ArrayList)TaxiList[0].get(idx).get("User")).get(i);
                    UserList.add(reviewValue);
                }
                if(!TaxiList[0].get(idx).get("Admin").toString().equals(IDindex+""))
                    UserList.add(IDindex);
                Update.put("User",UserList);
                TaxiUser.updateChildren(Update);

                Intent intent = new Intent(getActivity(), BoardingActivity.class);
                intent.putExtra("TaxiList",TaxiList);
                intent.putExtra("IDList",IDList);
                intent.putExtra("IDindex",IDindex);
                intent.putExtra("cntTaxi",cntTaxi);
                intent.putExtra("cntID",cntID);
                intent.putExtra("idx",idx);
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
                Log.d("Comment",idx+"");
                openfragment(CommentFragment, bundle);
                break;
        }
    }

    //맵 실행 함수
    private void initMapView() {
        mapView = new MapView(getActivity());
        mapView.removeAllPOIItems();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapViewContainer = (ViewGroup) getView().findViewById(R.id.map);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }


}
