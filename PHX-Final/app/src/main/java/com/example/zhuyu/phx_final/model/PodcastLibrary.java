package com.example.zhuyu.phx_final.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PodcastLibrary {

    private static PodcastLibrary sPodcastLibrary;


    private List<Podcast> mPodcastList;

    public static PodcastLibrary get() {
        if (sPodcastLibrary == null) {
            sPodcastLibrary = new PodcastLibrary();
        }
        return sPodcastLibrary;
    }

    private PodcastLibrary() {
        mPodcastList = new ArrayList<>();
    }


    public void addPodcast(Podcast podcast) {
        mPodcastList.add(podcast);

    }


    public List<Podcast> getPodcasts() {
        return mPodcastList;
    }

    public Podcast getPodcast(String id) {
        for (Podcast p : mPodcastList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }


}
