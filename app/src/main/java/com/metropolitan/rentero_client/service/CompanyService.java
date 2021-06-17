package com.metropolitan.rentero_client.service;

import com.metropolitan.rentero_client.model.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CompanyService {

    @GET("companies")
    Call<List<Company>> getAll();

    @GET("companies/{companyId}")
    Call<Company> getById(@Path("companyId") long companyId);

}