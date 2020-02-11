package com.zaelani.submission_moviecatalog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.zaelani.submission_moviecatalog.model.MovieItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.NAME;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.PHOTO;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.FavoriteColumns.RATE;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.TABLE_MOVIE;
import static com.zaelani.submission_moviecatalog.database.DatabaseContract.TABLE_TVSHOW;

public class MovieHelper {
    private DatabaseMovieHelper databaseMovieHelper;
    //private DatabaseTvshowHelper databaseTvshowHelper;
    private SQLiteDatabase database;

    private static MovieHelper INSTANCE;

    public MovieHelper(Context context) {
        databaseMovieHelper = new DatabaseMovieHelper(context);
        // databaseTvshowHelper = new DatabaseTvshowHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void openMovie() throws SQLException {
        database = databaseMovieHelper.getWritableDatabase();
    }

    public void closeMovie() {
        databaseMovieHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<MovieItems> getAllFavoriteMovie() {
        database = databaseMovieHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_MOVIE, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems movieItems;

        if (cursor.getCount() > 0) {
            do {
                movieItems = new MovieItems();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItems.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                movieItems.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movieItems.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATE)));
                movieItems.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));

                arrayList.add(movieItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int selectTvshowId(int id) {
        Cursor cursor = database.query(TABLE_TVSHOW, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        int num=0;
        if (cursor.getCount() > 0) {
            do {
                num = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return num;
    }

    public int selectMovieId(int id) {
        Cursor cursor = database.query(TABLE_MOVIE, null, _ID + " = ? ", new String[]{String.valueOf(id)}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        int num=0;
        if (cursor.getCount() > 0) {
            do {
                num = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return num;
    }




    public ArrayList<MovieItems> getAllFavoriteTvshow() {
        database = databaseMovieHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_TVSHOW, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems movieItems;

        if (cursor.getCount() > 0) {
            do {
                movieItems = new MovieItems();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItems.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                movieItems.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movieItems.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATE)));
                movieItems.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));

                arrayList.add(movieItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieItems movieItems) {
        ContentValues values = new ContentValues();
        values.put(_ID, movieItems.getId());
        values.put(NAME, movieItems.getName());
        values.put(PHOTO, movieItems.getPhoto());
        values.put(RATE, movieItems.getRating());
        values.put(DESCRIPTION, movieItems.getDescription());
        return database.insert(TABLE_MOVIE, null, values);
    }

    public long insertTvshow(MovieItems movieItems) {
        ContentValues values = new ContentValues();
        values.put(_ID, movieItems.getId());
        values.put(NAME, movieItems.getName());
        values.put(PHOTO, movieItems.getPhoto());
        values.put(RATE, movieItems.getRating());
        values.put(DESCRIPTION, movieItems.getDescription());
        return database.insert(TABLE_TVSHOW, null, values);
    }

    public int deleteMovieById(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteTvshowById(int id) {
        return database.delete(TABLE_TVSHOW, _ID + " = ?", new String[]{String.valueOf(id)});
    }

}
