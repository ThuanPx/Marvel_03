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
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.SchedulerProvider;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManager;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManagerImpl;

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
        Navigator navigator = new Navigator(getActivity());
        FavoriteFragmentAdapter favoriteFragmentAdapter =
                new FavoriteFragmentAdapter(getActivity());
        DialogManager dialogManager = new DialogManagerImpl(getContext());
        mFavoriteViewModel =
                new FavoriteViewModel(favoriteFragmentAdapter, heroRepository, navigator,
                        dialogManager);
        mFavoriteViewModel.setBaseSchedulerProvider(SchedulerProvider.getInstance());
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

    @Override
    public void onStop() {
        super.onStop();
        mFavoriteViewModel.onStop();
    }
}
