package com.example.hiveapp_login.ui;

import com.example.hiveapp_login.R;
import com.example.hiveapp_login.ui.login.LoginActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        fillVersion();

        // 몇 초 후에 로그인으로 넘어감
        new Handler().postDelayed(() -> {
            // 실행할 동작 코딩
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }

    private void fillVersion() {
        String appName = getString(R.string.app_name);
        String appTeam = getString(R.string.app_team);
        ((TextView) findViewById(R.id.tv_splash_app_title)).setText(appName);
        ((TextView) findViewById(R.id.tv_splash_app_team)).setText(appTeam);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            ((TextView) findViewById(R.id.tv_splash_app_version)).setText(getString(R.string.splash_app_version, versionName));
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}