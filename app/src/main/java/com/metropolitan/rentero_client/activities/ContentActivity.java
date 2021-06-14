package com.metropolitan.rentero_client.activities;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.utils.AuthUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    private TextView tokenView;
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        tokenView = findViewById(R.id.token);
        signOutBtn = findViewById(R.id.signOutBtn);

        AuthUtil authUtil = new AuthUtil(getApplicationContext());

        String token = authUtil.getToken();

        tokenView.setText(token);

        signOutBtn.setOnClickListener(v -> {
            authUtil.removeToken();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}