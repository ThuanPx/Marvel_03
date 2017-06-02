package com.hyperion.ths.marvel_03.ui.heroinfo;

import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;

/**
 * Created by ths on 01/06/2017.
 */

public class HeroInfoViewModel extends BaseViewModel {
    private Hero mHero;
    private Navigator mNavigator;

    public HeroInfoViewModel(Hero hero, Navigator navigator) {
        mHero = hero;
        mNavigator = navigator;
    }

    public String getHeroName() {
        return mNavigator.getActivity().getString(R.string.name) + mHero.getName();
    }

    public String getHeroId() {
        return mNavigator.getActivity().getString(R.string.id) + mHero.getId();
    }

    public String getHeroDes() {
        return mNavigator.getActivity().getString(R.string.description)
                + mHero.getDescription();
    }

    public String getHeroImageUrl() {
        return mHero.getImageHero().getImageUrl() + Constant.IMAGEFORMAT;
    }
}
