package com.toxoidandroid.rubric;

import java.io.Serializable;

public class MovieVideo implements Serializable {

    private String mId;
    private String mName;
    private String mVideoId;
    private String mVideoKey;
    private String mVideoSite;
    private String mType;

    public MovieVideo(String id, String name, String videoId, String videoKey, String videoSite, String type) {
        mId = id;
        mName = name;
        mVideoId = videoId;
        mVideoKey = videoKey;
        mVideoSite = videoSite;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;

    }

    public void setId(String id) {
        mId = id;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public String getVideoKey() {
        return mVideoKey;
    }

    public void setVideoKey(String videoKey) {
        mVideoKey = videoKey;
    }

    public String getVideoSite() {
        return mVideoSite;
    }

    public void setVideoSite(String videoSite) {
        mVideoSite = videoSite;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
