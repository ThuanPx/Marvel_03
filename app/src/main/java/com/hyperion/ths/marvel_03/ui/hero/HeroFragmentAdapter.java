package com.hyperion.ths.marvel_03.ui.hero;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.Locale;

/**
 * Created by ths on 01/06/2017.
 */

public class HeroFragmentAdapter extends RecyclerView.Adapter<HeroFragmentAdapter.ItemViewHolder>
        implements Filterable {
    private static final String TAG = HeroFragmentAdapter.class.getSimpleName();
    private List<Hero> mHeroList;
    private List<Hero> mHeroListStore;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mOnRecyclerViewItemClickListener;
    private OnItemClickListener mOnItemButtonClickListener;
    private HeroRepository mHeroRepository;
    private boolean mFavorite;
    private boolean mIsStore;
    private OnLoadMoreListener mOnLoadMoreListener;
    boolean mIsLoading = false, mIsMoreDataAvailable = true;
    public static final int TYPE_HERO = 0;
    public static final int TYPE_LOAD = 1;

    public HeroFragmentAdapter(HeroRepository heroRepository) {
        mHeroList = new ArrayList<>();
        mHeroListStore = new ArrayList<>();
        mHeroRepository = heroRepository;
    }

    public void setOnRecyclerViewItemClickListener(
            BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>
                    onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setOnItemButtonClickListener(OnItemClickListener onItemButtonClickListener) {
        mOnItemButtonClickListener = onItemButtonClickListener;
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
        if (position >= getItemCount() - 1
                && mIsMoreDataAvailable
                && !mIsLoading
                && mOnLoadMoreListener != null) {
            mIsLoading = true;
            mIsStore = false;
            mOnLoadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_HERO) {
            holder.bindData(mHeroList.get(position),
                    getFavorite(mHeroList.get(position).getName()));
            mIsLoading = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeroList.get(position).getId() == Constant.POINT) {
            return TYPE_LOAD;
        } else {
            return TYPE_HERO;
        }
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
                    for (Hero hero : mHeroListStore) {
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
