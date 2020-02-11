package com.zaelani.submission_moviecatalog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.NAME;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.PHOTO;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.RATE;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.TABLE_MOVIE;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.TABLE_TVSHOW;

public class DatabaseMovieHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_MOVIE =
            "create table "+TABLE_MOVIE+
                    " (" +_ID+" integer primary key, "+
                    NAME+" text not null, "+
                    PHOTO+" text not null, "+
                    RATE+" text not null, "+
                    DESCRIPTION+" text not null);";

    private static String CREATE_TABLE_TVSHOW =
            "create table "+TABLE_TVSHOW+
                    " (" +_ID+" integer primary key, "+
                    NAME+" text not null, "+
                    PHOTO+" text not null, "+
                    RATE+" text not null, "+
                    DESCRIPTION+" text not null);";

    DatabaseMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_TVSHOW);
        onCreate(sqLiteDatabase);
    }
}
