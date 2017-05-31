package com.hyperion.ths.marvel_03.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by ths on 28/05/2017.
 */

public class HeroList implements Parcelable {
    private int mOffset;
    private int mLimit;
    private int mTotal;
    private int mCount;
    @SerializedName("results")
    private List<Heroes> mHeroes;

    public HeroList() {
    }

    public HeroList(int offset, int limit, int total, int count, List<Heroes> heroes) {

        this.mOffset = offset;
        this.mLimit = limit;
        this.mTotal = total;
        this.mCount = count;
        mHeroes = heroes;
    }

    protected HeroList(Parcel in) {
        mOffset = in.readInt();
        mLimit = in.readInt();
        mTotal = in.readInt();
        mCount = in.readInt();
        mHeroes = in.createTypedArrayList(Heroes.CREATOR);
    }

    public static final Creator<HeroList> CREATOR = new Creator<HeroList>() {
        @Override
        public HeroList createFromParcel(Parcel in) {
            return new HeroList(in);
        }

        @Override
        public HeroList[] newArray(int size) {
            return new HeroList[size];
        }
    };

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        this.mOffset = offset;
    }

    public int getLimit() {
        return mLimit;
    }

    public void setLimit(int limit) {
        this.mLimit = limit;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        this.mTotal = total;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public List<Heroes> getHeroes() {
        return mHeroes;
    }

    public void setHeroes(List<Heroes> heroes) {
        mHeroes = heroes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mOffset);
        parcel.writeInt(mLimit);
        parcel.writeInt(mTotal);
        parcel.writeInt(mCount);
        parcel.writeTypedList(mHeroes);
    }
}
