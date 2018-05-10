package com.example.jocke.projekt;

/**
 * Created by Jocke on 10-May-18.
 */

public class Picture {
    private String mUrl;
    private String mCreator;
    private String mCreatorUrl;
    private int mViews;
    private int mDownloads;
    private int mLikes;

    public Picture(String url, String creator, String creatorUrl, int views, int downloads, int likes) {
        mUrl = url;
        mCreator = creator;
        mCreatorUrl = creatorUrl;
        mViews = views;
        mDownloads = downloads;
        mLikes = likes;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getCreatorUrl() {
        return mCreatorUrl;
    }

    public int getViews() {
        return mViews;
    }

    public int getDownloads() {
        return mDownloads;
    }

    public int getLikes() {
        return mLikes;
    }
}
