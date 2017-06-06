package com.hyperion.ths.marvel_03.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Base Recycler view
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
     */
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemRecyclerViewClick(T item);
    }
}
