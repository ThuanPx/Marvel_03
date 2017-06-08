package com.hyperion.ths.marvel_03.ui.favorite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.data.source.local.sqlite.HeroLocalDataSource;
import com.hyperion.ths.marvel_03.databinding.FragmentFavoriteBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private FavoriteViewModel mFavoriteViewModel;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentFavoriteBinding fragmentFavoriteBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        HeroRepository heroRepository =
                new HeroRepository(null, new HeroLocalDataSource(getActivity()));
        FavoriteFragmentAdapter favoriteFragmentAdapter =
                new FavoriteFragmentAdapter(getActivity(), heroRepository);
        mFavoriteViewModel = new FavoriteViewModel(favoriteFragmentAdapter, heroRepository);
        View view = fragmentFavoriteBinding.getRoot();
        fragmentFavoriteBinding.setViewModel(mFavoriteViewModel);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (!menuVisible) {
            return;
        }
        mFavoriteViewModel.getAllHero();
    }
}
