package com.toxoidandroid.rubric;

import java.io.Serializable;

public class Movie implements Serializable {

    private String mId;
    private String mTitle;
    private String mThumbnailUrl;
    private String mSynopsis;
    private String mUserRating;
    private String mReleaseYear;

    public Movie(String id, String title, String thumbnailUrl, String synopsis, String userRating, String releaseYear) {
        mId = id;
        mTitle = title;
        mThumbnailUrl = thumbnailUrl;
        mSynopsis = synopsis;
        mUserRating = userRating;
        mReleaseYear = releaseYear;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                ", mSynopsis='" + mSynopsis + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mReleaseYear='" + mReleaseYear + '\'' +
                '}';
    }
}
