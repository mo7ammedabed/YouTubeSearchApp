package com.example.youtubesearchapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.youtubesearchapp.models.YouTubeVideo;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<YouTubeVideo> videos;

    public VideoAdapter(List<YouTubeVideo> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        YouTubeVideo video = videos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return videos != null ? videos.size() : 0;
    }

    public void setVideos(List<YouTubeVideo> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView channelTextView;
        private TextView publishTimeTextView;
        private ImageView thumbnailImageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            channelTextView = itemView.findViewById(R.id.channelTextView);
            publishTimeTextView = itemView.findViewById(R.id.publishTimeTextView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
        }

        public void bind(YouTubeVideo video) {
            titleTextView.setText(video.getSnippet().getTitle());
            channelTextView.setText(video.getSnippet().getChannelTitle());
            publishTimeTextView.setText(video.getSnippet().getPublishedAt());

            // Load thumbnail using Glide
            Glide.with(itemView.getContext())
                    .load(video.getSnippet().getThumbnails().getMedium().getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(thumbnailImageView);
        }
    }
}