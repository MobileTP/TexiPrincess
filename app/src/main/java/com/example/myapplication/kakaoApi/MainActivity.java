package com.example.myapplication.kakaoApi;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.gson.Gson;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.net.URLEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK fe94c788c227a80046a80f13fce7d65a";
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private ArrayList<ListLayout> listItems;
    private int pageNumber = 1;
    private String keyword = "";
    private int cnt = 1;
    private MarkerEventListener eventListener = new MarkerEventListener(this, this);
    MapPOIItem[] poiLists = new MapPOIItem[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);

        mapView = new MapView(this);
        if (cnt == 1) {
            cnt--;
            mapView.removeAllPOIItems();
            mapView.removeAllPolylines();
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
        mapViewContainer = (ViewGroup) findViewById(R.id.map);
        mapViewContainer.addView(mapView);
        mapView.setPOIItemEventListener(eventListener);  // 마커 클릭 이벤트 리스너 등록

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        listAdapter = new ListAdapter(listItems);
        recyclerView.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                ListLayout item = listItems.get(position);
                double latitude = item.getY();
                double longitude = item.getX();
                // Perform the necessary actions on item click
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                mapView.selectPOIItem(poiLists[position], true);
            }
        });

        EditText search = findViewById(R.id.et_search_field);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = search.getText().toString();

                    pageNumber = 1;
                    searchKeyword(keyword, pageNumber);
                    InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    handled = true;
                }
                return handled;
            }
        });

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = search.getText().toString();

                pageNumber = 1;
                searchKeyword(keyword, pageNumber);
                InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        findViewById(R.id.btn_prevPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber--;
                searchKeyword(keyword, pageNumber);
            }
        });

        findViewById(R.id.btn_nextPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                searchKeyword(keyword, pageNumber);
            }
        });
    }


    private void searchKeyword(String keyword, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KakaoAPI api = retrofit.create(KakaoAPI.class);
        Call<ResultSearchKeyword> call = api.getSearchKeyword(API_KEY, keyword, page);

        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                if (response.isSuccessful()) {
                    ResultSearchKeyword searchResult = response.body();
                    addItemsAndMarkers(searchResult);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get search results", Toast.LENGTH_SHORT).show();
                    Log.e("RESPONSE", "response : " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error occurred while searching", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });

    }

    private void addItemsAndMarkers(ResultSearchKeyword searchResult) {
        listItems.clear();
        int cnt = 0;
        int i = 0;
        if (searchResult != null && searchResult.getDocuments() != null) {
            for (Place document : searchResult.getDocuments()) {
                ListLayout item = new ListLayout(
                        document.getPlace_name(),
                        document.getRoad_address_name(),
                        document.getAddress_name(),
                        Double.parseDouble(document.getX()),
                        Double.parseDouble(document.getY())
                );
                listItems.add(item);
                if (cnt == 0) {
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(document.getY()), Double.parseDouble(document.getX())), true);
                    cnt++;
                }
                MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(Double.parseDouble(document.getY()), Double.parseDouble(document.getX()));
                MapPOIItem marker = new MapPOIItem();
                poiLists[i] = marker;
                i++;
                marker.setItemName(document.getPlace_name());
                marker.setTag(0);
                marker.setMapPoint(MARKER_POINT);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);

            }

        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        mapViewContainer.removeView(mapView);
        mapView = null;
        cnt++;
        super.finish();
    }

}