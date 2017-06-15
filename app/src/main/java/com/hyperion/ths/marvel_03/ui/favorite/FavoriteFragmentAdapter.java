package com.hyperion.ths.marvel_03.ui.favorite;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.databinding.ItemFragmentFavoriteBinding;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ths on 06/06/2017.
 */

public class FavoriteFragmentAdapter
        extends BaseRecyclerView<FavoriteFragmentAdapter.ItemViewHolder> implements Filterable {
    private List<Hero> mHeroList;
    private List<Hero> mHeroListStore;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mItemClickListener;
    private OnItemClickListener mFavoriteClickListener;
    private boolean mIsStore;

    public FavoriteFragmentAdapter(Context context) {
        super(context);
        mHeroList = new ArrayList<>();
        mHeroListStore = new ArrayList<>();
    }

    public void updateData(List<Hero> heroList) {
        if (!mIsStore) {
            mHeroListStore.clear();
            mHeroListStore.addAll(heroList);
            mIsStore = true;
        }
        mHeroList.clear();
        mHeroList.addAll(heroList);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener<Hero> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setFavoriteClickListener(OnItemClickListener buttonClickListener) {
        mFavoriteClickListener = buttonClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFragmentFavoriteBinding itemFragmentFavoriteBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_fragment_favorite, parent, false);
        return new ItemViewHolder(itemFragmentFavoriteBinding, mItemClickListener,
                mFavoriteClickListener);
    }

    //Because adapter for Fragment Favorite so icon favorite always checked so set "true"
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindData(mHeroList.get(position), true);
    }

    @Override
    public int getItemCount() {
        return mHeroList != null ? mHeroList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mHeroList.clear();
                    mHeroList.addAll(mHeroListStore);
                } else {
                    mHeroList.clear();
                    Log.e("Test", mHeroListStore.size() + "-");
                    for (Hero hero : mHeroListStore) {
                        Log.e("Test", hero.getName());
                        if (hero.getName().toLowerCase(Locale.getDefault()).contains(charString)) {
                            mHeroList.add(hero);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mHeroList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mHeroList = (List<Hero>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * Create Item View Holder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemFragmentFavoriteBinding mItemFragmentFavoriteBinding;
        private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mItemClickListener;
        private OnItemClickListener mButtonClickListener;

        ItemViewHolder(ItemFragmentFavoriteBinding itemFragmentFavoriteBinding,
                BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> itemClickListener,
                OnItemClickListener buttonClickListener) {
            super(itemFragmentFavoriteBinding.getRoot());
            mItemFragmentFavoriteBinding = itemFragmentFavoriteBinding;
            mItemClickListener = itemClickListener;
            mButtonClickListener = buttonClickListener;
        }

        void bindData(Hero hero, boolean isFavorite) {
            mItemFragmentFavoriteBinding.setViewModel(
                    new ItemFavoriteFragmentViewModel(hero, mItemClickListener,
                            mButtonClickListener, isFavorite));
            mItemFragmentFavoriteBinding.executePendingBindings();
        }
    }
}
