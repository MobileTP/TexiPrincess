package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.kakaoApi.MainActivity;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateTaxiActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {
    private static final int SEARCH_DEPART_ACTIVITY = 20000;
    private static final int SEARCH_ARRIVE_ACTIVITY = 10000;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Toolbar toolbar;
    private Button createButton, searchButton1,searchButton2;
    private EditText departure, arrival, departureTime, numberOfPeople;
    private MapPOIItem marker_depart = null;
    private MapPOIItem marker_arrive = null;
    private MapPoint MARKER_POINT_ARRIVE = null;
    private MapPoint MARKER_POINT_DEPART = null;
    private Button departureTimeButton;
    private TextView departureTimeText;
    private int cnt = 0;


    public CreateTaxiActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtaxi);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //지도 띄우기
        initMapView();


        //출발지, 도착지 텍스트
        departure = findViewById(R.id.departure);
        arrival = findViewById(R.id.arrival);

        //인원 수 선택 스피너
        Spinner spinner = (Spinner) findViewById(R.id.number_of_people);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //출발지
        departure.setFocusable(false);
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                    Log.i("주소설정페이지", "주소입력창 클릭");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // 화면전환 애니메이션 없애기
                    overridePendingTransition(0, 0);
                    // 주소결과
                    startActivityForResult(i, SEARCH_DEPART_ACTIVITY);

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //도착지
        arrival.setFocusable(false);
        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                    Log.i("주소설정페이지", "주소입력창 클릭");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // 화면전환 애니메이션 없애기
                    overridePendingTransition(0, 0);
                    // 주소결과
                    startActivityForResult(i, SEARCH_ARRIVE_ACTIVITY);

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //출발시간 선택 버튼
        departureTimeButton = findViewById(R.id.departure_time_button);
        departureTimeText = findViewById(R.id.departure_time_text);
        departureTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTaxiActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Save the selected date
                                // Then show TimePickerDialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTaxiActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                // Save the selected time
                                                departureTimeText.setText(" "+year + "년 "+(monthOfYear + 1)+ "월 " + dayOfMonth + "일 " + hourOfDay + "시 " + minute+"분");
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //생성버튼
        createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        mapViewContainer.removeView(mapView);
        mapView = null;
        cnt--;
        super.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
        mapView = null;
    }

    @Override
    public void onResume() {
        super.onResume();

//         When restarting the activity (when Activity B is finished and restarted)
//         if mapView is not included, add it
        if (mapViewContainer.indexOfChild(mapView) == -1) {
            try {
                // Re-initialize and add mapView
                initMapView();
            } catch (RuntimeException re) {
                Log.e("Error", re.toString());
            }
        }
    }

    private void initMapView() {
        mapView = new MapView(this);
        if (cnt == 0) {
            cnt++;
            mapView.removeAllPOIItems();
            mapView.removeAllPolylines();
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
        mapViewContainer = (ViewGroup) findViewById(R.id.map);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //search Activity 로부터의 결과 값이 이골으로 전달 된다
                if (result.getResultCode() == RESULT_OK){
                    if(result.getData() != null) {
                        String data = result.getData().getStringExtra("data");
                        departure.setText(data);
                    }
                }
            }
    );

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult");
        if (mapViewContainer.indexOfChild(mapView) == -1) {
            try {
                // Re-initialize and add mapView
                initMapView();
            } catch (RuntimeException re) {
                Log.e("Error", re.toString());
            }
        }

        switch (requestCode) {
            case SEARCH_DEPART_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("PlaceName");
                    if (data != null) {
                        Log.i("test", "data: " + data);
                        double X = intent.getExtras().getDouble("PlaceX");
                        double Y = intent.getExtras().getDouble("PlaceY");
                        departure.setText(data);
                        if (marker_depart != null) {
                            mapView.removePOIItem(marker_depart);
                        }
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Y, X), true);

                        MARKER_POINT_DEPART = MapPoint.mapPointWithGeoCoord(Y, X);
                        marker_depart = new MapPOIItem();
                        marker_depart.setItemName("출발지 : " + data);
                        marker_depart.setTag(0);
                        marker_depart.setMapPoint(MARKER_POINT_DEPART);
                        marker_depart.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker_depart.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker_depart);
                    }
                }
                break;
            case SEARCH_ARRIVE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("PlaceName");
                    if (data != null) {
                        Log.i("test", "data: " + data);
                        arrival.setText(data);
                        double X = intent.getExtras().getDouble("PlaceX");
                        double Y = intent.getExtras().getDouble("PlaceY");
                        if (marker_arrive != null) {
                            mapView.removePOIItem(marker_arrive);
                        }
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Y, X), true);

                        MARKER_POINT_ARRIVE = MapPoint.mapPointWithGeoCoord(Y, X);
                        marker_arrive = new MapPOIItem();
                        marker_arrive.setItemName("도착지 : " + data);
                        marker_arrive.setTag(0);
                        marker_arrive.setMapPoint(MARKER_POINT_ARRIVE);
                        marker_arrive.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker_arrive.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker_arrive);
                    }
                }
                break;
        }

        if (marker_arrive != null && marker_depart != null) {
            mapView.removeAllPolylines();
            mapView.addPOIItem(marker_depart);
            mapView.addPOIItem(marker_arrive);
            MapPolyline polyline = new MapPolyline();
            polyline.setLineColor(Color.argb(128, 255, 0, 0));
            polyline.addPoint(MARKER_POINT_ARRIVE);
            polyline.addPoint(MARKER_POINT_DEPART);
            mapView.addPolyline(polyline);

            MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
            int padding = 100; // px
            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        }
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
