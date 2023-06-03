package com.example.myapplication.kakaoApi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.appcompat.app.AlertDialog;

public class MarkerEventListener implements MapView.POIItemEventListener {
    private Context context;
    private Activity activity;

    public MarkerEventListener(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem) {
        // 마커 클릭 시
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem) {
        // 말풍선 클릭 시 (Deprecated)
        // 이 함수도 작동하지만 그냥 아래 있는 함수에 작성하자
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem, MapPOIItem.CalloutBalloonButtonType buttonType) {
        String name = poiItem.getItemName();
        MapPoint mapPoint = poiItem.getMapPoint();
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        double Y = mapPointGeo.latitude;
        double X = mapPointGeo.longitude;
        Bundle extra = new Bundle();
        Intent intent = new Intent();
        extra.putString("PlaceName", name);
        extra.putDouble("PlaceX", X);
        extra.putDouble("PlaceY", Y);
        intent.putExtras(extra);

        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem poiItem, MapPoint mapPoint) {
        // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
    }
}