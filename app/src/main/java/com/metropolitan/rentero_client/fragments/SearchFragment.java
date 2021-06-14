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
import com.metropolitan.rentero_client.adapter.CarAdapter;
import com.metropolitan.rentero_client.model.Car;
import com.metropolitan.rentero_client.service.CarService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private RecyclerView carRecyclerView;
    private CarAdapter carAdapter;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.8:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        carRecyclerView = view.findViewById(R.id.carsList);
        carRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        carAdapter = new CarAdapter(view.getContext(), car -> {
            Toast.makeText(getActivity().getApplicationContext(), "Id od automobila je: " + car.getId(), Toast.LENGTH_LONG).show();
        });
        carRecyclerView.setAdapter(carAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveCars();
    }

    private void retrieveCars() {
        CarService carService = retrofit.create(CarService.class);
        Call<List<Car>> getAllCars = carService.getAll();

        getAllCars.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()) {
                    List<Car> returnedCars = response.body();
                    carAdapter.setTasks(returnedCars);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}