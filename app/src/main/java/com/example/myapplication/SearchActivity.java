package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private WebView webView;

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Log.i("getData", "data: " + data);
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WebView webview = findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        webview.loadUrl("http://172.20.10.5:5000/api");

//        webview.addJavascriptInterface(new BridgeInterface(), "Android");
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // Android -> javascript 함수 호출!
//                webview.loadUrl("javascript:sample2_execDaumPostcode();");
//            }
//        });
//        //최초 웹뷰 로드
//        webview.loadUrl("https://searchaddress-bab25.web.app/");
    }

//    private class BridgeInterface {
//        @JavascriptInterface
//        public void processDATA(String data){
//            // 카카오 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달 받는다. (from Javascript)
//            Intent intent = new Intent();
//            intent.putExtra("data",data);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
//    }
}
