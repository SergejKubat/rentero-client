package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarService {

    @GET("cars")
    Call<List<Car>> getAll();

    @GET("cars/{carId}")
    Call<Car> getById(@Path("carId") long carId);

}