package com.hyperion.ths.marvel_03.ui.hero;

import android.databinding.BaseObservable;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.utils.Constant;

/**
 * Created by ths on 01/06/2017.
 */

public class ItemHeroFragmentViewModel extends BaseObservable {
    private Hero mHero;

    public ItemHeroFragmentViewModel(Hero hero) {
        mHero = hero;
    }

    public String getHeroName() {
        return mHero.getName();
    }

    public String getHeroImageUrl() {
        return mHero.getImageHero().getImageUrl() + Constant.IMAGEFORMAT;
    }
}
