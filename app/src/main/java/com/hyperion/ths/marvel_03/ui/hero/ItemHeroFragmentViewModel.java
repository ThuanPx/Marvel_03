package com.hyperion.ths.marvel_03.ui.hero;

import android.databinding.BaseObservable;
import android.view.View;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.utils.Constant;

/**
 * Created by ths on 01/06/2017.
 */

public class ItemHeroFragmentViewModel extends BaseObservable {
    private Hero mHero;
    private BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> mOnRecyclerViewItemClickListener;
    private BaseRecyclerView.OnItemButtonClickListener<Hero> mOnItemButtonClickListener;

    public ItemHeroFragmentViewModel(Hero hero,
            BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> onRecyclerViewItemClickListener,
            BaseRecyclerView.OnItemButtonClickListener<Hero> onItemButtonClickListener) {
        mHero = hero;
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
        mOnItemButtonClickListener = onItemButtonClickListener;
    }

    public void onItemCardClicked(View view) {
        if (mOnRecyclerViewItemClickListener == null) {
            return;
        }
        mOnRecyclerViewItemClickListener.onItemRecyclerViewClick(mHero);
    }

    public void clickButtonFavorite(View view) {
        if (mOnItemButtonClickListener == null) {
            return;
        }
        mOnItemButtonClickListener.onItemButtonClick(mHero);
    }

    public String getHeroName() {
        return mHero.getName();
    }

    public String getHeroImageUrl() {
        return mHero.getImageHero().getImageUrl() + Constant.IMAGEFORMAT;
    }
}
