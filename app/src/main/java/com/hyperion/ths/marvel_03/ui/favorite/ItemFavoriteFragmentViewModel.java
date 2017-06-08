package com.hyperion.ths.marvel_03.ui.favorite;

import android.databinding.BaseObservable;
import android.view.View;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import com.hyperion.ths.marvel_03.utils.Constant;

/**
 * Created by ths on 06/06/2017.
 */

public class ItemFavoriteFragmentViewModel extends BaseObservable {
    private Hero mHero;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mItemClickListener;
    private OnItemClickListener mButtonClickListener;
    private boolean mFavorite;

    public ItemFavoriteFragmentViewModel(Hero hero,
            BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> itemClickListener,
            OnItemClickListener buttonClickListener, boolean favorite) {
        mHero = hero;
        mItemClickListener = itemClickListener;
        mButtonClickListener = buttonClickListener;
        mFavorite = favorite;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mHero);
    }

    public void clickButtonFavorite(View view) {
        if (mButtonClickListener == null) {
            return;
        }
        mButtonClickListener.onItemFavoriteClick(mHero);
    }

    public String getHeroName() {
        return mHero.getName();
    }

    public String getHeroImageUrl() {
        return mHero.getImageHero().getImageUrl() + Constant.IMAGEFORMAT;
    }
}
