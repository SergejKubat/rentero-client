package com.metropolitan.rentero_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.metropolitan.rentero_client.activities.ContentActivity;
import com.metropolitan.rentero_client.activities.LoginActivity;
import com.metropolitan.rentero_client.utils.AuthUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

        if (firstStart) {
            Toast.makeText(getApplicationContext(), "Aplikacija se pokrenula prvi put!", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();

        }

        AuthUtil authUtil = new AuthUtil(getApplicationContext());

        if (authUtil.isTokenExpired()) {
            authUtil.removeToken();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
            startActivity(intent);
        }

        finish();

    }
}