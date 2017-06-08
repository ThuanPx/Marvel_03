package com.hyperion.ths.marvel_03.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.ui.favorite.FavoriteFragment;
import com.hyperion.ths.marvel_03.ui.hero.HeroFragment;

/**
 * Created by ths on 27/05/2017.
 */

public class MainViewPageAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;
    private final String[] mTabsTitle = { "Hero", "Favorite" };
    private final int[] mTabsIcons =
            { R.drawable.ic_hero_selector, R.drawable.ic_favorite_selector };

    public MainViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public View getTabView(int position) {
        @SuppressLint("InflateParams") View view =
                LayoutInflater.from(mContext).inflate(R.layout.custom_tablayout, null);
        TextView textTitle = (TextView) view.findViewById(R.id.title);
        textTitle.setText(mTabsTitle[position]);
        ImageView imageViewIcon = (ImageView) view.findViewById(R.id.icon);
        imageViewIcon.setImageResource(mTabsIcons[position]);
        return view;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HeroFragment();
                break;
            case 1:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
