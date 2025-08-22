package com.example.youtubesearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.youtubesearchapp.models.YouTubeVideo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView emptyStateTextView;
    private TextView errorTextView;

    private MainViewModel viewModel;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViewModel();
        setupRecyclerView();
        setupSearchButton();
        setupObservers();
    }

    private void initViews() {
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        emptyStateTextView = findViewById(R.id.emptyStateTextView);
        errorTextView = findViewById(R.id.errorTextView);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new VideoAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(v -> performSearch());

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().trim();
        viewModel.searchVideos(query);
    }

    private void setupObservers() {
        viewModel.getVideos().observe(this, videos -> {
            if (videos != null && !videos.isEmpty()) {
                showResults();
                adapter.setVideos(videos);
            } else {
                showEmptyState();
            }
        });

        viewModel.getIsLoading().observe(this, isLoading ->
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );

        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }
        });
    }

    private void showResults() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyStateTextView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
    }

    private void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        emptyStateTextView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
    }

    private void showError(String errorMessage) {
        recyclerView.setVisibility(View.GONE);
        emptyStateTextView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(errorMessage);
    }
}