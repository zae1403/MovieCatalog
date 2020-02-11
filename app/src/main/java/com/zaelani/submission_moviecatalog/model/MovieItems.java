package com.zaelani.submission_moviecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItems implements Parcelable {
    private String name, description, director, rating, title, photo;
    private int id;


    public MovieItems() {

    }


    protected MovieItems(Parcel in) {
        name = in.readString();
        description = in.readString();
        director = in.readString();
        rating = in.readString();
        title = in.readString();
        photo = in.readString();
        id = in.readInt();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(director);
        parcel.writeString(rating);
        parcel.writeString(title);
        parcel.writeString(photo);
        parcel.writeInt(id);
    }
}
