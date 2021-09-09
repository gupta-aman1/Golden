package com.example.goldenfish.UserAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.goldenfish.Dashboard.HomeDashboardActivity;
import com.example.goldenfish.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View view) {

        startActivity(new Intent(this, HomeDashboardActivity.class));
    }

    public void ClickSignup(View view) {

        startActivity(new Intent(this, SignupUSer.class));
    }
}