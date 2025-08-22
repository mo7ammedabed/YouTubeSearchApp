package com.example.youtubesearchapp.models;

public class YouTubeVideo {
    private VideoId id;
    private VideoSnippet snippet;

    public VideoId getId() {
        return id;
    }

    public void setId(VideoId id) {
        this.id = id;
    }

    public VideoSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(VideoSnippet snippet) {
        this.snippet = snippet;
    }
}