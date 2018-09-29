package com.example.lnthe54.miniproject2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lnthe54.miniproject2.R;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class ScreenFlashActivity extends AppCompatActivity {
    private final int SPLASH_SLEEP = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_flash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent openMainActivity = new Intent(ScreenFlashActivity.this,MainActivity.class);
                startActivity(openMainActivity);
                finish();
            }
        }, SPLASH_SLEEP);
    }
}
