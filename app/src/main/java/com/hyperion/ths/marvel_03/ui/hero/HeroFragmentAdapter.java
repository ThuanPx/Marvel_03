package com.hyperion.ths.marvel_03.ui.hero;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.databinding.ItemFragmentHeroBinding;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import com.hyperion.ths.marvel_03.utils.Constant;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 01/06/2017.
 */

public class HeroFragmentAdapter extends RecyclerView.Adapter<HeroFragmentAdapter.ItemViewHolder> {
    private static final String TAG = HeroFragmentAdapter.class.getSimpleName();
    private List<Hero> mHeroList;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mOnRecyclerViewItemClickListener;
    private OnItemClickListener mOnItemButtonClickListener;
    private HeroRepository mHeroRepository;
    private boolean mFavorite;

    public HeroFragmentAdapter(Context context, HeroRepository heroRepository) {
        mHeroList = new ArrayList<>();
        mHeroRepository = heroRepository;
    }

    public void setOnRecyclerViewItemClickListener(
            BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                    onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItemButtonClickListener(OnItemClickListener onItemButtonClickListener) {
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

    private boolean getFavorite(String name) {
        mHeroRepository.getHeroByName(name).subscribe(new Consumer<Hero>() {
            @Override
            public void accept(@NonNull Hero hero) throws Exception {
                if (hero.getId() == Constant.POINT) {
                    mFavorite = false;
                } else {
                    mFavorite = true;
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, throwable.getLocalizedMessage());
            }
        });
        return mFavorite;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindData(mHeroList.get(position), getFavorite(mHeroList.get(position).getName()));
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
        private OnItemClickListener mOnItemButtonClickListener;

        ItemViewHolder(ItemFragmentHeroBinding binding,
                BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                        onRecyclerViewItemClickListener,
                OnItemClickListener onItemButtonClickListener) {
            super(binding.getRoot());
            mItemFragmentHeroBinding = binding;
            mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
            mOnItemButtonClickListener = onItemButtonClickListener;
        }

        void bindData(Hero hero, boolean favorite) {
            mItemFragmentHeroBinding.setViewModel(
                    new ItemHeroFragmentViewModel(hero, mOnRecyclerViewItemClickListener,
                            mOnItemButtonClickListener, favorite));
            mItemFragmentHeroBinding.executePendingBindings();
        }
    }
}
