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
import com.metropolitan.rentero_client.adapter.ReservationAdapter;
import com.metropolitan.rentero_client.model.Reservation;
import com.metropolitan.rentero_client.service.ReservationService;
import com.metropolitan.rentero_client.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationFragment extends Fragment {

    private RecyclerView reservationsList;
    private ReservationAdapter reservationAdapter;

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

        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        reservationsList = view.findViewById(R.id.reservationsList);
        reservationsList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        reservationAdapter = new ReservationAdapter(view.getContext());
        reservationsList.setAdapter(reservationAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveReservations();
    }

    void retrieveReservations() {
        ReservationService reservationService = retrofit.create(ReservationService.class);
        Call<List<Reservation>> reservationsResponse = reservationService.getAll();

        reservationsResponse.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> reservations = response.body();
                    reservationAdapter.setTasks(reservations);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}