package com.metropolitan.rentero_client.activities;

import com.google.android.material.textfield.TextInputEditText;
import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.SignUpRequest;
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

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fullNameInput, emailInput, phoneNumberInput, passwordInput;
    private Button signUpBtn;
    private TextView loginText;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.8:8080/api/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameInput = findViewById(R.id.signUpFullName);
        emailInput = findViewById(R.id.signUpEmail);
        phoneNumberInput = findViewById(R.id.signUpPhoneNumber);
        passwordInput = findViewById(R.id.signUpPassword);

        signUpBtn = findViewById(R.id.signUpBtn);

        loginText = findViewById(R.id.loginText);

        signUpBtn.setOnClickListener(v -> {
            signUp();
        });

        loginText.setOnClickListener(v -> {
            redirectToLogIn();
        });

    }

    public void signUp() {
        String fullName, email, phoneNumber, password;

        fullName = String.valueOf(fullNameInput.getText());
        email = String.valueOf(emailInput.getText());
        phoneNumber = String.valueOf(phoneNumberInput.getText());
        password = String.valueOf(passwordInput.getText());

        SignUpRequest userDetails = new SignUpRequest(fullName,
                email,
                phoneNumber,
                password);

        AuthService authService = retrofit.create(AuthService.class);
        Call<String> signUpModel = authService.signUp(userDetails);

        signUpModel.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void redirectToLogIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}