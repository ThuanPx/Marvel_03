package com.hyperion.ths.marvel_03.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Base Recycler view
 * @param <V>
 */
public abstract class BaseRecyclerView<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {
    private Context mContext;

    public BaseRecyclerView(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * Listener click image
     * @param <T>
     */
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemRecyclerViewClick(T item);
    }

    /**
     * Listener click button favorite
     * @param <T>
     */
    public interface OnItemButtonClickListener<T> {
        void onItemButtonClick(T item);
    }
}
