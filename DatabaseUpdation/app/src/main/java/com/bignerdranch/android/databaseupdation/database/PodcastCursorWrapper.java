package com.bignerdranch.android.databaseupdation.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.databaseupdation.Podcast;
import com.bignerdranch.android.databaseupdation.database.PodcastDbSchema.PodcastTable;

public class PodcastCursorWrapper extends CursorWrapper {
    public PodcastCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Podcast getPodcast() {
        String id = getString(getColumnIndex(PodcastTable.Cols.ID));
        String title = getString(getColumnIndex(PodcastTable.Cols.TITLE));
        String time = getString(getColumnIndex(PodcastTable.Cols.TIME));
        String Descrip = getString(getColumnIndex(PodcastTable.Cols.DESCRIP));
        String imgUrl = getString(getColumnIndex(PodcastTable.Cols.IMGURL));
        String mp3Url = getString(getColumnIndex(PodcastTable.Cols.MP3URL));
        int isDownloaded = getInt(getColumnIndex(PodcastTable.Cols.ISDOWNLOADED));

        return null;

    }

}
