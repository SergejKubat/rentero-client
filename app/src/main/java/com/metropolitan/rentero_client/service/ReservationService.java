package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReservationService {

    @GET("reservations")
    Call<List<Reservation>> getAll();

    @GET("reservations/{reservationId}")
    Call<Reservation> getById(@Path("reservationId") long reservationId);

}
