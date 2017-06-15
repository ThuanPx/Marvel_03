package com.hyperion.ths.marvel_03.ui.heroinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.data.source.local.sqlite.HeroLocalDataSource;
import com.hyperion.ths.marvel_03.databinding.ActivityHeroInfoBinding;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.SchedulerProvider;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManager;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManagerImpl;

/**
 * Create by Hyperion
 */
public class HeroInfoActivity extends AppCompatActivity {
    private HeroInfoViewModel mHeroInfoViewModel;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHeroInfoBinding activityHeroInfoBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_hero_info);
        mNavigator = new Navigator(this);
        Hero hero = getIntent().getParcelableExtra(Constant.BUNDLE_HERO);
        HeroInfoViewPageAdapter heroInfoViewPageAdapter =
                new HeroInfoViewPageAdapter(this, hero.getImageHero().getImageUrl());
        HeroLocalDataSource heroLocalDataSource = new HeroLocalDataSource(this);
        HeroRepository heroRepository = new HeroRepository(null, heroLocalDataSource);
        DialogManager dialogManager = new DialogManagerImpl(this);
        mHeroInfoViewModel =
                new HeroInfoViewModel(hero, mNavigator, heroInfoViewPageAdapter, heroRepository,
                        dialogManager);
        mHeroInfoViewModel.setBaseSchedulerProvider(SchedulerProvider.getInstance());
        activityHeroInfoBinding.setViewModel(mHeroInfoViewModel);
    }

    @Override
    public void onBackPressed() {
        mNavigator.finishActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHeroInfoViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mHeroInfoViewModel.onStop();
        super.onStop();
    }
}
