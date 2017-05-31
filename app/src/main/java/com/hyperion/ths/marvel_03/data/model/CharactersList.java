package com.hyperion.ths.marvel_03.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 27/05/2017.
 */

public class CharactersList implements Parcelable {
    @SerializedName("code")
    private int mCode;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("copyright")
    private String mCopyRight;
    @SerializedName("attributionText")
    private String mAttributionText;
    @SerializedName("attributionHTML")
    private String mAttributionHTML;
    @SerializedName("etag")
    private String mEtag;
    @SerializedName("data")
    private HeroList mHeroesLists;

    public CharactersList() {
    }

    public CharactersList(int code, String status, String copyRight, String attributionText,
            String attributionHTML, String etag, HeroList heroes) {
        this.mCode = code;
        this.mStatus = status;
        this.mCopyRight = copyRight;
        this.mAttributionText = attributionText;
        this.mAttributionHTML = attributionHTML;
        this.mEtag = etag;
        mHeroesLists = heroes;
    }

    protected CharactersList(Parcel in) {
        mCode = in.readInt();
        mStatus = in.readString();
        mCopyRight = in.readString();
        mAttributionText = in.readString();
        mAttributionHTML = in.readString();
        mEtag = in.readString();
        mHeroesLists = in.readParcelable(HeroList.class.getClassLoader());
    }

    public static final Creator<CharactersList> CREATOR = new Creator<CharactersList>() {
        @Override
        public CharactersList createFromParcel(Parcel in) {
            return new CharactersList(in);
        }

        @Override
        public CharactersList[] newArray(int size) {
            return new CharactersList[size];
        }
    };

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getCopyRight() {
        return mCopyRight;
    }

    public void setCopyRight(String copyRight) {
        this.mCopyRight = copyRight;
    }

    public String getAttributionText() {
        return mAttributionText;
    }

    public void setAttributionText(String attributionText) {
        this.mAttributionText = attributionText;
    }

    public String getAttributionHTML() {
        return mAttributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.mAttributionHTML = attributionHTML;
    }

    public String getEtag() {
        return mEtag;
    }

    public void setEtag(String etag) {
        this.mEtag = etag;
    }

    public HeroList getHeroesLists() {
        return mHeroesLists;
    }

    public void setHeroes(HeroList heroes) {
        mHeroesLists = heroes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mCode);
        parcel.writeString(mStatus);
        parcel.writeString(mCopyRight);
        parcel.writeString(mAttributionText);
        parcel.writeString(mAttributionHTML);
        parcel.writeString(mEtag);
        parcel.writeParcelable(mHeroesLists, flags);
    }
}
