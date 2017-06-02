package com.hyperion.ths.marvel_03.ui.heroinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.databinding.ActivityHeroInfoBinding;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;

/**
 * Create by Hyperion
 */
public class HeroInfoActivity extends AppCompatActivity {
    private HeroInfoViewModel mHeroInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHeroInfoBinding activityHeroInfoBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_hero_info);
        Navigator navigator = new Navigator(this);
        Hero hero = getIntent().getParcelableExtra(Constant.BUNDLE_HERO);
        mHeroInfoViewModel = new HeroInfoViewModel(hero, navigator);
        activityHeroInfoBinding.setViewModel(mHeroInfoViewModel);
    }
}
