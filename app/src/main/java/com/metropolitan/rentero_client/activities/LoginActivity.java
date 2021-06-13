package com.metropolitan.rentero_client.activities;

import com.google.android.material.textfield.TextInputEditText;
import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.AuthRequest;
import com.metropolitan.rentero_client.model.JWTAuthResponse;
import com.metropolitan.rentero_client.service.AuthService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    Button loginBtn;
    TextView signUpText;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.8:8080/api/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.logInEmail);
        passwordInput = findViewById(R.id.logInPassword);

        loginBtn = findViewById(R.id.logInBtn);

        signUpText = findViewById(R.id.signUpText);

        loginBtn.setOnClickListener(v -> {
            logIn();
        });

        signUpText.setOnClickListener(v -> {
            redirectToSignUp();
        });

    }

    public void logIn() {
        String email, password;

        email = String.valueOf(emailInput.getText());
        password = String.valueOf(passwordInput.getText());

        AuthRequest userDetails = new AuthRequest(email, password);

        AuthService authService = retrofit.create(AuthService.class);
        Call<JWTAuthResponse> jwtResponse = authService.logIn(userDetails);

        jwtResponse.enqueue(new Callback<JWTAuthResponse>() {
            @Override
            public void onResponse(Call<JWTAuthResponse> call, Response<JWTAuthResponse> response) {
                Toast.makeText(getApplicationContext(), "Prijava uspešna!", Toast.LENGTH_LONG).show();
                // System.out.println("JWT token: " + response.body().getAccessToken());
                // @TODO: Save token in shared preferences and redirect to main activity
            }

            @Override
            public void onFailure(Call<JWTAuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                // @TODO: Create error messages
            }
        });
    }

    public void redirectToSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}