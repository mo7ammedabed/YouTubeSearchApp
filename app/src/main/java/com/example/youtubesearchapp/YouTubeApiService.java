package com.example.youtubesearchapp;

import com.example.youtubesearchapp.models.YouTubeResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    @GET("search")
    Call<YouTubeResponse> searchVideos(
            @Query("part") String part,
            @Query("type") String type,
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("key") String key
    );

    class Factory {
        private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

        public static YouTubeApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(YouTubeApiService.class);
        }
    }
}