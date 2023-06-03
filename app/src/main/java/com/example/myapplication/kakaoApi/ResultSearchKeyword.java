package com.example.myapplication.kakaoApi;

import java.util.List;

public class ResultSearchKeyword {
    private PlaceMeta meta;
    private List<Place> documents;

    public PlaceMeta getMeta() {
        return meta;
    }

    public void setMeta(PlaceMeta meta) {
        this.meta = meta;
    }

    public List<Place> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Place> documents) {
        this.documents = documents;
    }
}
