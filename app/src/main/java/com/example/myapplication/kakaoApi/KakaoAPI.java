package com.example.myapplication.kakaoApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI {
    @GET("/v2/local/search/keyword.json")
    Call<ResultSearchKeyword> getSearchKeyword(
            @Header("Authorization") String key,
            @Query("query") String query,
            @Query("page") int page
    );
}
