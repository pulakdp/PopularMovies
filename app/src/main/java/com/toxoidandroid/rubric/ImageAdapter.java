package com.toxoidandroid.rubric;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter{

    private Context mContext;
    List<Movie> mMovies;

    public ImageAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(540, 800));
            if (getScreenWidth() < getScreenHeight())
                imageView.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth() / 2, getScreenHeight()/2 - 100));
            else
                imageView.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth() / 3, getScreenHeight() - 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Movie movie = mMovies.get(position);
        Picasso.with(mContext).load(movie.getThumbnailUrl())
                .error(R.drawable.images).placeholder(R.drawable.images)
                .into(imageView);
        return imageView;
    }

    public void onNewData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
