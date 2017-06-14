package com.hyperion.ths.marvel_03.ui.main;

import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.connection.MainReceiver;
import com.hyperion.ths.marvel_03.databinding.ActivityMainBinding;
import com.hyperion.ths.marvel_03.ui.favorite.OnTextSearchFavoriteListener;
import com.hyperion.ths.marvel_03.ui.hero.OnTextSearchListener;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

/**
 * Created by ths on 27/05/2017.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private MainViewModel mMainViewModel;
    private MainReceiver mMainReceiver;
    private SearchView mSearchView;
    private OnTextSearchListener mOnTextSearchListener;
    private OnTextSearchFavoriteListener mOnTextSearchFavoriteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewPageAdapter mainViewPageAdapter =
                new MainViewPageAdapter(getSupportFragmentManager(), this);
        mMainReceiver = new MainReceiver();
        mMainViewModel = new MainViewModel(mainViewPageAdapter, mMainReceiver);
        binding.setViewModel(mMainViewModel);
    }

    public void setOnTextSearchFavoriteListener(
            OnTextSearchFavoriteListener onTextSearchFavoriteListener) {
        mOnTextSearchFavoriteListener = onTextSearchFavoriteListener;
    }

    public void setOnTextSearchListener(OnTextSearchListener onTextSearchListener) {
        mOnTextSearchListener = onTextSearchListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mOnTextSearchListener.getTextListener(newText);
        mOnTextSearchFavoriteListener.getTextListener(newText);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.this.registerReceiver(mMainReceiver, new IntentFilter(CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.this.unregisterReceiver(mMainReceiver);
    }
}
