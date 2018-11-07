package com.bignerdranch.android.databaseupdation.database;

public class PodcastDbSchema {

    public static final class PodcastTable {
        public static final String NAME = "podcasts";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String TIME = "time";
            public static final String DESCRIP = "descrip";
            public static final String IMGURL = "img_url";
            public static final String MP3URL = "mp3_url";
            public static final String ISDOWNLOADED = "download_state";
        }
    }
}
