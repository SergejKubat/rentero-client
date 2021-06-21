package com.metropolitan.rentero_client.activities;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Company;
import com.metropolitan.rentero_client.service.CompanyService;
import com.metropolitan.rentero_client.utils.AppConstants;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyDetailsActivity extends AppCompatActivity {

    private ImageView companyImage;
    private TextView companyName, companyDescription, companyAddress, companyCity,
            companyPhoneNumber, companyEmail;
    private RatingBar companyRatingBar;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        long companyId = getIntent().getLongExtra("companyId", 0);

        companyImage = findViewById(R.id.companyImage);

        companyName = findViewById(R.id.companyName);
        companyDescription = findViewById(R.id.companyDescription);
        companyAddress = findViewById(R.id.companyAddress);
        companyCity = findViewById(R.id.companyCity);
        companyPhoneNumber = findViewById(R.id.companyPhoneNumber);
        companyEmail = findViewById(R.id.companyEmail);

        companyRatingBar = findViewById(R.id.companyRatingBar);

        retrieveCompany(companyId);
    }

    private void retrieveCompany(long companyId) {
        CompanyService companyService = retrofit.create(CompanyService.class);
        Call<Company> companyResponse = companyService.getById(companyId);

        companyResponse.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    Company company = response.body();
                    populateFields(company);
                } else {
                    Toast.makeText(getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateFields(Company company) {
        Picasso.get()
                .load(company.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(companyImage);

        companyName.setText(company.getName());
        companyDescription.setText(company.getDescription());
        companyAddress.setText(company.getAddress());
        companyCity.setText(company.getCity());
        companyPhoneNumber.setText(company.getPhoneNumber());
        companyEmail.setText(company.getEmail());

        companyAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitude", company.getLatitude());
            intent.putExtra("longitude", company.getLongitude());
            startActivity(intent);
        });

        companyPhoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + company.getPhoneNumber()));
            startActivity(intent);
        });

        companyEmail.setOnClickListener(v -> {
            String[] to = { company.getEmail() };
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, company.getName());
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Zdravo, " + company.getName() + "\n\n");
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Pošaljite email"));
        });
    }

}