package com.hyperion.ths.marvel_03.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 28/05/2017.
 */

public class ImageHero implements Parcelable {
    @SerializedName("path")
    private String mImageUrl;

    protected ImageHero(Parcel in) {
        mImageUrl = in.readString();
    }

    public static final Creator<ImageHero> CREATOR = new Creator<ImageHero>() {
        @Override
        public ImageHero createFromParcel(Parcel in) {
            return new ImageHero(in);
        }

        @Override
        public ImageHero[] newArray(int size) {
            return new ImageHero[size];
        }
    };

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public ImageHero() {

    }

    public ImageHero(String imageUrl) {

        this.mImageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mImageUrl);
    }
}
