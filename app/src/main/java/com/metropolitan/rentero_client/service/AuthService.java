package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.AuthRequest;
import com.metropolitan.rentero_client.model.JWTAuthResponse;
import com.metropolitan.rentero_client.model.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("signup")
    Call<String> signUp(@Body SignUpRequest signUp);

    @POST("login")
    Call<JWTAuthResponse> logIn(@Body AuthRequest auth);

}
