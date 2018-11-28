package com.bignerdranch.android.databaseupdation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.databaseupdation.database.PodcastBaseHelper;
import com.bignerdranch.android.databaseupdation.database.PodcastCursorWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.bignerdranch.android.databaseupdation.database.PodcastDbSchema.PodcastTable;

public class PodcastLibrary {

    private static PodcastLibrary sPodcastLibrary;

    private Context mContext;
    private SQLiteDatabase mPodcastDatabase;

    public static PodcastLibrary get(Context context) {
        if (sPodcastLibrary == null) {
            sPodcastLibrary = new PodcastLibrary(context);
        }
        return sPodcastLibrary;
    }

    private PodcastLibrary(Context context) {
        mContext = context.getApplicationContext();
        mPodcastDatabase = new PodcastBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void closeDatabase() {
        mPodcastDatabase.close();
    }


    public void addPodcast(Podcast podcast) {
        ContentValues values = getContentValues(podcast);

        mPodcastDatabase.insert(PodcastTable.NAME, null, values);

    }
    public void updatePodcast(Podcast podcast) {
        String id = podcast.getId();
        ContentValues values = getContentValues(podcast);

        mPodcastDatabase.update(PodcastTable.NAME, values, PodcastTable.Cols.ID + " = ?",
                new String[] {id});
    }

    public List<Podcast> getPodcasts() {
        List<Podcast> podcasts= new ArrayList<>();

        PodcastCursorWrapper cursor = queryPodcast(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                podcasts.add(cursor.getPodcast());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return podcasts;
    }

    public Podcast getPodcast(String id) {
        PodcastCursorWrapper cursor = queryPodcast(
                PodcastTable.Cols.ID + " = ?",
                new String[] {id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPodcast();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Podcast podcast) {
        ContentValues values = new ContentValues();
        values.put(PodcastTable.Cols.ID, podcast.getId());
        values.put(PodcastTable.Cols.TITLE, podcast.getTitle());
        values.put(PodcastTable.Cols.TIME, podcast.getTime());
        values.put(PodcastTable.Cols.DESCRIP, podcast.getDescrip());
        values.put(PodcastTable.Cols.IMGURL, podcast.getImgUrl());
        values.put(PodcastTable.Cols.MP3URL, podcast.getMp3Url());
        values.put(PodcastTable.Cols.ISDOWNLOADED, podcast.getDownloaded());

        return values;

    }

    private PodcastCursorWrapper queryPodcast(String whereClause, String[] whereArgs) {


        Cursor cursor = mPodcastDatabase.query(PodcastTable.NAME, null, whereClause,
                whereArgs, null, null, null);
        return new PodcastCursorWrapper(cursor);
    }
}
