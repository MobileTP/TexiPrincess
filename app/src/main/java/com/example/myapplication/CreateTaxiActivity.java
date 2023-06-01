package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateTaxiActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Toolbar toolbar;
    private Button createButton, searchButton1,searchButton2;
    private EditText departure, arrival, departureTime, numberOfPeople;

    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK fe94c788c227a80046a80f13fce7d65a"; // REST API key


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

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        // 권한ID를 가져옵니다
//        int permission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.INTERNET);
//
//        int permission2 = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//        int permission3 = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // 권한이 열려있는지 확인
//        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
//            // 마쉬멜로우 이상버전부터 권한을 물어본다
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
//                requestPermissions(
//                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                        1000);
//            }
//            return;
//        }
//
//        //지도를 띄우자
//        // java code
//        mapView = new MapView(this);
//        mapViewContainer = (ViewGroup) findViewById(R.id.map);
//        mapViewContainer.addView(mapView);
//        mapView.setMapViewEventListener(this);
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
//
        departure = findViewById(R.id.departure);
        arrival = findViewById(R.id.arrival);
        departureTime = findViewById(R.id.departure_time);
        Spinner spinner = (Spinner) findViewById(R.id.number_of_people);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        departure.setFocusable(false);
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(CreateTaxiActivity.this, SearchActivity.class);
                getSearchResult.launch(intent);
            }
        });

        arrival.setFocusable(false);
        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(CreateTaxiActivity.this, SearchActivity.class);
                getSearchResult.launch(intent);
            }
        });



//        searchButton1 = findViewById(R.id.search_button_departure);
//        searchButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Retrieve the text from the departure EditText
//                String departureText = departure.getText().toString();
//
//                // Use the text as a parameter for the searchKeyword method
//                searchKeyword(departureText);
//            }
//        });
//
//        searchButton2 = findViewById(R.id.search_button_destination);
//        searchButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Add the functionality to go back to the previous window
//
//            }
//        });

        createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add functionality for the create button
            }
        });
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

    private void searchKeyword(String keyword) {
        Retrofit retrofit = new Retrofit.Builder() // Retrofit setup
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KakaoAPI_Interface api = retrofit.create(KakaoAPI_Interface.class); // Create communication interface object
        Call<ResultSearchKeyword> call = api.getSearchKeyword(API_KEY, keyword); // Input search conditions

        // Request to API server
        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                // Communication success (search results are stored in response.body())
                Log.d("Test", "Raw: " + response.raw());
                Log.d("Test", "Body: " + response.body());
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                // Communication failure
                Log.w("CreateTaxi", "Communication failure: " + t.getMessage());
            }
        });
    }

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
