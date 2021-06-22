package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.Review;
import com.metropolitan.rentero_client.model.ReviewRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewService {

    @GET("reviews")
    Call<List<Review>> getAll(@Query("companyId") long companyId);

    @GET("reviews/{reviewId}")
    Call<Review> getById(@Path("reviewId") long reviewId);

    @POST("reviews")
    Call<Review> create(@Header("Authorization") String token,
                        @Query("companyId") long companyId,
                        @Body ReviewRequest reviewRequest);

}