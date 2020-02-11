package com.zaelani.submission_moviecatalog.database;

import android.provider.BaseColumns;

class DatabaseContract {

    static String TABLE_MOVIE = "movie_favorite";
    static String TABLE_TVSHOW = "tvshow_favorite";

    static final class FavoriteColumns implements BaseColumns{
        static String NAME = "name";
        static String PHOTO = "link_photo";
        static String DESCRIPTION = "description";
        static String RATE = "rate";


    }
}
