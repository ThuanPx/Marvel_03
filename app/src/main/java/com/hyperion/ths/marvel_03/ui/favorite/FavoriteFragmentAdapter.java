package com.hyperion.ths.marvel_03.ui.favorite;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.databinding.ItemFragmentFavoriteBinding;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 06/06/2017.
 */

public class FavoriteFragmentAdapter
        extends BaseRecyclerView<FavoriteFragmentAdapter.ItemViewHolder> {
    private List<Hero> mHeroList;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mItemClickListener;
    private OnItemClickListener mFavoriteClickListener;
    private HeroRepository mHeroRepository;

    public FavoriteFragmentAdapter(Context context, HeroRepository heroRepository) {
        super(context);
        mHeroList = new ArrayList<>();
        mHeroRepository = heroRepository;
    }

    public void updateData(List<Hero> heroList) {
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

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindData(mHeroList.get(position), false);
    }

    @Override
    public int getItemCount() {
        return mHeroList != null ? mHeroList.size() : 0;
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
