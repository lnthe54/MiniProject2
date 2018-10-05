package com.example.lnthe54.miniproject2.view.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.ViewPagerAdapter;
import com.example.lnthe54.miniproject2.service.MusicService;
import com.example.lnthe54.miniproject2.utils.AppController;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.utils.ConfigAction;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private RelativeLayout layoutCurrentSong;
    private TextView tvCurrentSong;
    private TextView tvCurrentArtist;
    private ImageView ivCurrentSong;
    private MusicService musicService;

    private int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppController.getInstance().setMainActivity(this);

        if (!checkPermissions()) {
            return;
        }

        initViews();

        if (musicService != null) {
            showCurrentSong();
        }
        registerBroadcastUpdatePlaying();
        addEvents();

    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String check : permissions) {
                int status = checkSelfPermission(check);
                if (status == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(permissions, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkPermissions()) {
            initViews();
        } else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    BroadcastReceiver broadcastReceiverUpdatePlaying = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            musicService = (MusicService) AppController.getInstance().getMusicService();
            if (musicService != null) {
                layoutCurrentSong.setVisibility(View.VISIBLE);
            } else {
                layoutCurrentSong.setVisibility(View.GONE);
            }
            showCurrentSong();
            if (musicService != null) {
                totalTime = musicService.getTotalTime();
//                updateSeekBar();
                if (musicService.isPlaying()) {
//                    ivPlayPause.setImageResource(R.drawable.pause_button);
                } else {
//                    ivPlayPause.setImageResource(R.drawable.play_button);
                }
            }
        }
    };

    private void registerBroadcastUpdatePlaying() {
        IntentFilter intentFilter = new IntentFilter(ConfigAction.ACTION_UPDATE_PlAY_STATUS);
        registerReceiver(broadcastReceiverUpdatePlaying, intentFilter);
    }

    private void unRegisterBroadcastUpdatePlaying() {
        unregisterReceiver(broadcastReceiverUpdatePlaying);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        addPagerAdapter();

        layoutCurrentSong = findViewById(R.id.layout_current_song);

        if (musicService != null) {
            layoutCurrentSong.setVisibility(View.VISIBLE);
        } else {
            layoutCurrentSong.setVisibility(View.GONE);
        }

        ivCurrentSong = findViewById(R.id.iv_current_song);
        tvCurrentSong = findViewById(R.id.tv_current_song);
        tvCurrentArtist = findViewById(R.id.tv_current_artist);
    }

    private void addEvents() {
        layoutCurrentSong.setOnClickListener(this);
    }

    private void addPagerAdapter() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home: {

            }

            case R.id.layout_current_song: {
                clickLayoutPlaying();
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_song: {
                break;
            }
            case R.id.ic_album: {
                break;
            }
            case R.id.ic_artist: {
                break;
            }
            case R.id.ic_log_out: {
                break;
            }
            case R.id.ic_setting: {
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showCurrentSong() {
        if (musicService != null) {
            if (musicService.getCurrentSong().getAlbumImage() != null) {
                Glide.with(this)
                        .load(musicService.getCurrentSong().getAlbumImage())
                        .into(ivCurrentSong);
            } else {
                ivCurrentSong.setImageResource(R.drawable.album_default);
            }

            Animation rotate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_iv_playing);
            ivCurrentSong.startAnimation(rotate);
            tvCurrentSong.setText(musicService.getCurrentSong().getNameSong());
            tvCurrentArtist.setText(musicService.getCurrentSong().getArtistSong());

        }
    }

    private void clickLayoutPlaying() {
        if (musicService != null) {
            Intent openPlay = new Intent(MainActivity.this, PlayMusicActivity.class);
            openPlay.putExtra(Config.IS_PLAYING, true);
            startActivity(openPlay);
            this.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppController.getInstance().setMainActivity(null);
        unRegisterBroadcastUpdatePlaying();
    }
}
