package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI_Interface {
    @GET("v2/local/search/keyword.json") // Receive Keyword.json information
    Call<ResultSearchKeyword> getSearchKeyword(
            @Header("Authorization") String key, // Kakao API authentication key [required]
            @Query("query") String query // query to be searched [required]
            // parameters can be added
            // @Query("category_group_code") String category
    );
}

