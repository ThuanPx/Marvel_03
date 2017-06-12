package com.hyperion.ths.marvel_03.ui.main;

import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.connection.MainReceiver;
import com.hyperion.ths.marvel_03.databinding.ActivityMainBinding;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

/**
 * Created by ths on 27/05/2017.
 */

public class MainActivity extends AppCompatActivity {
    private MainViewModel mMainViewModel;
    private MainReceiver mMainReceiver;

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
