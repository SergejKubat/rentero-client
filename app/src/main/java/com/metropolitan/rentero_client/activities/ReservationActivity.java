package com.metropolitan.rentero_client.activities;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Car;
import com.metropolitan.rentero_client.model.Reservation;
import com.metropolitan.rentero_client.model.ReservationRequest;
import com.metropolitan.rentero_client.service.CarService;
import com.metropolitan.rentero_client.service.ReservationService;
import com.metropolitan.rentero_client.utils.AppConstants;
import com.metropolitan.rentero_client.utils.AuthUtil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationActivity extends AppCompatActivity {

    private long carId;
    private double pricePerDay;

    private TextView carBrandAndModel, carEngineSize, carHp, carYear, carPrice, startDateText,
            endDateText, totalPriceText;
    private Button datePickerBtn, createReservationBtn;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        carId = getIntent().getLongExtra("carId", 0);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        carBrandAndModel = findViewById(R.id.carBrandAndModel);
        carPrice = findViewById(R.id.carPrice);
        carEngineSize = findViewById(R.id.carEngineSize);
        carHp = findViewById(R.id.carHp);
        carYear = findViewById(R.id.carYear);
        startDateText = findViewById(R.id.startDate);
        endDateText = findViewById(R.id.endDate);
        totalPriceText = findViewById(R.id.totalPrice);

        datePickerBtn = findViewById(R.id.datePickerBtn);
        createReservationBtn = findViewById(R.id.createReservationBtn);

        datePickerBtn.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            long startMillis = selection.first;
            long endMillis = selection.second;

            LocalDate startDate = Instant.ofEpochMilli(startMillis)
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate endDate = Instant.ofEpochMilli(endMillis)
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            startDateText.setText(startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            endDateText.setText(endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

            long daysBetween = Duration.between(startDate.atStartOfDay(),
                    endDate.atStartOfDay()).toDays();

            double totalPrice = daysBetween * pricePerDay;
            totalPriceText.setText(String.format("%.2f", totalPrice)  + "€");
        });

        createReservationBtn.setOnClickListener(v -> {
            long startMillis = materialDatePicker.getSelection().first;
            long endMillis = materialDatePicker.getSelection().second;

            LocalDate startDate = Instant.ofEpochMilli(startMillis)
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate endDate = Instant.ofEpochMilli(endMillis)
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            ReservationRequest reservationRequest = new ReservationRequest(startDate.toString(),
                    endDate.toString());

            String token = new AuthUtil(this).getToken();

            ReservationService reservationService = retrofit.create(ReservationService.class);
            Call<Reservation> reservationResponse = reservationService.create("Bearer " + token, carId, reservationRequest);

            reservationResponse.enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Uspešno ste rezervisali automobil!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), errorObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reservation> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        retrieveCar(carId);
    }

    private void retrieveCar(long carId) {
        CarService carService = retrofit.create(CarService.class);
        Call<Car> carResponse = carService.getById(carId);

        carResponse.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Car car = response.body();
                    pricePerDay = car.getPricePerDay();
                    populateFields(car);
                } else {
                    Toast.makeText(getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateFields(Car car) {
        carBrandAndModel.setText("Automobil: " + car.getBrand() + " " + car.getModel());
        carPrice.setText("Cena po danu: " + String.format("%.2f", car.getPricePerDay())  + "€");
        carEngineSize.setText("Zapremina motora: " + car.getEngineSize() + " kubika");
        carHp.setText("Broj konjskih snaga: " + car.getHp() + " KS");
        carYear.setText("Godina proiyvodnje: " + String.valueOf(car.getYear()));
    }

}