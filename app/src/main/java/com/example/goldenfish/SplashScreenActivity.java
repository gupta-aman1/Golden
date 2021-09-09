package com.example.goldenfish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goldenfish.Dashboard.HomeDashboardActivity;
import com.example.goldenfish.UserAuth.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String user;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash_screen);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2200);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image= (ImageView) findViewById(R.id.splash_logo);

        image.startAnimation(rotate);
        System.out.println("Hello main");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreenActivity splashScreenActivity = SplashScreenActivity.this;
                splashScreenActivity.user = splashScreenActivity.sharedPreferences.getString("username", (String) null);
                if (SplashScreenActivity.this.user != null) {
                    SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, HomeDashboardActivity.class));
                    SplashScreenActivity.this.finish();
                    return;
                }
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, (long) 2500);
    }
}
