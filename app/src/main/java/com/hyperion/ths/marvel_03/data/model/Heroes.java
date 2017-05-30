package com.hyperion.ths.marvel_03.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 28/05/2017.
 */

public class Heroes implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("thumbnail")
    private ImageHero mImageHero;

    protected Heroes(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mImageHero = in.readParcelable(ImageHero.class.getClassLoader());
    }

    public static final Creator<Heroes> CREATOR = new Creator<Heroes>() {
        @Override
        public Heroes createFromParcel(Parcel in) {
            return new Heroes(in);
        }

        @Override
        public Heroes[] newArray(int size) {
            return new Heroes[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public ImageHero getImageHero() {
        return mImageHero;
    }

    public void setImageHero(ImageHero imageHero) {
        this.mImageHero = imageHero;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeParcelable(mImageHero, flags);
    }
}