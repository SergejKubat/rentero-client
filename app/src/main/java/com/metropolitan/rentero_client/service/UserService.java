package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("customers")
    Call<List<User>> getAll();

    @GET("customers/{email}")
    Call<User> getByEmail(@Path("email") String email);

}