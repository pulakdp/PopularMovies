package com.toxoidandroid.rubric;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VideoViewHolder> {

    Context mContext;
    List<MovieVideo> mMovieVideo;


    ListAdapter(Context context, List<MovieVideo> movieVideo) {
        mContext = context;
        mMovieVideo = movieVideo;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, null);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.titleText.setText(mMovieVideo.get(position).getName());
        holder.typeText.setText(mMovieVideo.get(position).getType());
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mMovieVideo.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return (mMovieVideo != null ? mMovieVideo.size() : 0);
    }

    public void onNewData(List<MovieVideo> videos) {
        mMovieVideo = videos;
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView typeText;

        VideoViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.videoTitle);
            typeText = (TextView) view.findViewById(R.id.videoType);
        }

    }

}
