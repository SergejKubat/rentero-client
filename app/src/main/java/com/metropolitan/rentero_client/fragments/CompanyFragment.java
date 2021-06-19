package com.metropolitan.rentero_client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.adapter.CompanyAdapter;
import com.metropolitan.rentero_client.model.Company;
import com.metropolitan.rentero_client.service.CompanyService;
import com.metropolitan.rentero_client.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyFragment extends Fragment {

    private RecyclerView companyRecyclerView;
    private CompanyAdapter companyAdapter;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        companyRecyclerView = view.findViewById(R.id.companiesList);
        companyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        companyAdapter = new CompanyAdapter(view.getContext(), company -> {
            Toast.makeText(getActivity().getApplicationContext(), "Id je: " + company.getId(), Toast.LENGTH_LONG).show();
        });
        companyRecyclerView.setAdapter(companyAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveCompanies();
    }

    private void retrieveCompanies() {
        CompanyService companyService = retrofit.create(CompanyService.class);
        Call<List<Company>> companiesResponse = companyService.getAll();

        companiesResponse.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    List<Company> companies = response.body();
                    companyAdapter.setTasks(companies);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}