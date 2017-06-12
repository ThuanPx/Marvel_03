package com.hyperion.ths.marvel_03.utils.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.hyperion.ths.marvel_03.ui.heroinfo.HeroInfoViewPageAdapter;
import com.hyperion.ths.marvel_03.ui.main.MainViewPageAdapter;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

/**
 * Created by ths on 27/05/2017.
 */

public final class BindingUtils {

    private BindingUtils() {
    }

    @BindingAdapter({ "onRefresh", "colorRes" })
    public static void setRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout,
            SwipeRefreshLayout.OnRefreshListener refreshListener, int colorResIds) {
        swipeRefreshLayout.setColorSchemeColors(colorResIds);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    @BindingAdapter("upWithViewPageHeroInfo")
    public static void setUpWithViewPageHeroInfo(final TabLayout tabLayout,
            final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("adapterViewPageHeroInfo")
    public static void setAdapterViewPageHeroInfo(final ViewPager viewPage,
            final HeroInfoViewPageAdapter heroInfoViewPageAdapter) {
        viewPage.setAdapter(heroInfoViewPageAdapter);
    }

    @BindingAdapter("buttonFavorite")
    public static void setButtonFavorite(final ShineButton shineButton, boolean checked) {
        shineButton.setChecked(checked);
    }

    @BindingAdapter("layoutManagerRecycler")
    public static void setLayoutManagerRecycler(final RecyclerView recyclerView,
            GridLayoutManager gridLayoutManager) {
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(final ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("recyclerAdapter")
    public static void setAdapterRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("adapterViewPage")
    public static void setAdapterViewPage(final ViewPager viewPage,
            final MainViewPageAdapter mainViewPageAdapter) {
        viewPage.setAdapter(mainViewPageAdapter);
    }

    @BindingAdapter({ "upWithViewPage", "tabIcons" })
    public static void setUpWithViewPage(final TabLayout tabLayout, final ViewPager viewPager,
            final MainViewPageAdapter mainViewPageAdapter) {
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mainViewPageAdapter.getTabView(i));
        }
    }
}
