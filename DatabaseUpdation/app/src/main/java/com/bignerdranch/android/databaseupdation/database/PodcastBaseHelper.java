package com.bignerdranch.android.databaseupdation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.databaseupdation.database.PodcastDbSchema.PodcastTable;

public class PodcastBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3300;
    private static final String DATABASE_NAME = "podcastBase.db";

    public PodcastBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PodcastTable.NAME + "(" + "_id integer primary key autoincrement, " +
        PodcastTable.Cols.ID + ", " +
        PodcastTable.Cols.TITLE + ", " +
        PodcastTable.Cols.TIME + "," +
        PodcastTable.Cols.DESCRIP + ", " +
        PodcastTable.Cols.IMGURL + ", " +
        PodcastTable.Cols.MP3URL + ", " +
        PodcastTable.Cols.ISDOWNLOADED +
        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ;
    }

}
