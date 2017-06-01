package com.hyperion.ths.marvel_03.ui.hero;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.databinding.ItemFragmentHeroBinding;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 01/06/2017.
 */

public class HeroFragmentAdapter extends RecyclerView.Adapter<HeroFragmentAdapter.ItemViewHolder> {
    private List<Hero> mHeroList;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mOnRecyclerViewItemClickListener;
    private BaseRecyclerView.OnItemButtonClickListener<Hero> mOnItemButtonClickListener;

    public HeroFragmentAdapter(Context context) {
        mHeroList = new ArrayList<>();
    }

    public void setOnRecyclerViewItemClickListener(
            BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                    onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItemButtonClickListener(
            BaseRecyclerView.OnItemButtonClickListener<Hero> onItemButtonClickListener) {
        mOnItemButtonClickListener = onItemButtonClickListener;
    }

    public void updateData(List<Hero> heroList) {
        mHeroList.clear();
        mHeroList.addAll(heroList);
        notifyDataSetChanged();
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFragmentHeroBinding itemFragmentHeroBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_fragment_hero, parent, false);
        return new ItemViewHolder(itemFragmentHeroBinding, mOnRecyclerViewItemClickListener,
                mOnItemButtonClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindData(mHeroList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHeroList != null ? mHeroList.size() : 0;
    }

    /**
     * Create Item View Holder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemFragmentHeroBinding mItemFragmentHeroBinding;
        private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                mOnRecyclerViewItemClickListener;
        private BaseRecyclerView.OnItemButtonClickListener<Hero> mOnItemButtonClickListener;

        ItemViewHolder(ItemFragmentHeroBinding binding,
                BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                        onRecyclerViewItemClickListener,
                BaseRecyclerView.OnItemButtonClickListener<Hero> onItemButtonClickListener) {
            super(binding.getRoot());
            mItemFragmentHeroBinding = binding;
            mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
            mOnItemButtonClickListener = onItemButtonClickListener;
        }

        void bindData(Hero hero) {
            mItemFragmentHeroBinding.setViewModel(
                    new ItemHeroFragmentViewModel(hero, mOnRecyclerViewItemClickListener,
                            mOnItemButtonClickListener));
            mItemFragmentHeroBinding.executePendingBindings();
        }
    }
}
