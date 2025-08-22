package com.example.youtubesearchapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.youtubesearchapp.models.YouTubeResponse;
import com.example.youtubesearchapp.models.YouTubeVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<YouTubeVideo>> videos = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private YouTubeApiService apiService = YouTubeApiService.Factory.create();

    public LiveData<List<YouTubeVideo>> getVideos() {
        return videos;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void searchVideos(String query) {
        if (query == null || query.trim().isEmpty()) {
            error.setValue("يرجى إدخال كلمة بحث");
            return;
        }

        isLoading.setValue(true);
        error.setValue(null);

        // استخدام API_KEY مباشرة بدلاً من الوصول إليه من Factory
        String apiKey = "AIzaSyAEk7F_bbhTFUWxwJXDn5fzxviwCJYk7EY";

        apiService.searchVideos("snippet", "video", query, 10, apiKey)
                .enqueue(new Callback<YouTubeResponse>() {
                    @Override
                    public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                        isLoading.setValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            List<YouTubeVideo> videoList = response.body().getItems();
                            videos.setValue(videoList);

                            if (videoList.isEmpty()) {
                                error.setValue("لا توجد نتائج للبحث");
                            }
                        } else {
                            error.setValue("فشل في جلب البيانات");
                        }
                    }

                    @Override
                    public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                        isLoading.setValue(false);
                        error.setValue("فشل في الاتصال: " + t.getMessage());
                    }
                });
    }
}