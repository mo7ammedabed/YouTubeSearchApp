package com.example.youtubesearchapp.models;

import java.util.List;

public class YouTubeResponse {
    private List<YouTubeVideo> items;

    public List<YouTubeVideo> getItems() {
        return items;
    }

    public void setItems(List<YouTubeVideo> items) {
        this.items = items;
    }
}