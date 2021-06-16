package com.metropolitan.rentero_client.activities;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Car;
import com.metropolitan.rentero_client.service.CarService;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarDetailsActivity extends AppCompatActivity {

    private ImageView carMainImage;
    private TextView carBrandAndModel, carDescription, carCapacity, carNumberOfDoors,
            carFuel, carPrice, carEngineSize, carHp, carGearStick, carYear;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.8:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        long carId = getIntent().getLongExtra("carId", 0);

        carMainImage = findViewById(R.id.carMainImage);

        carBrandAndModel = findViewById(R.id.carBrandAndModel);
        carDescription = findViewById(R.id.carDescription);
        carCapacity = findViewById(R.id.carCapacity);
        carNumberOfDoors = findViewById(R.id.carNumberOfDoors);
        carFuel = findViewById(R.id.carFuel);
        carPrice = findViewById(R.id.carPrice);
        carEngineSize = findViewById(R.id.carEngineSize);
        carHp = findViewById(R.id.carHp);
        carGearStick = findViewById(R.id.carGearStick);
        carYear = findViewById(R.id.carYear);

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
        Picasso.get()
                .load(car.getMainImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(carMainImage);

        carBrandAndModel.setText(car.getBrand() + " " + car.getModel());
        carDescription.setText(car.getDescription());
        carCapacity.setText(car.getCapacity() + " putnika");
        carNumberOfDoors.setText(car.getDoors() + " vrata");
        carFuel.setText(car.getFuel());
        carPrice.setText(String.format("%.2f", car.getPricePerDay())  + "€");
        carEngineSize.setText(car.getEngineSize() + " kubika");
        carHp.setText(car.getHp() + " KS");
        carGearStick.setText(car.getGearStick());
        carYear.setText(String.valueOf(car.getYear()));

    }

}