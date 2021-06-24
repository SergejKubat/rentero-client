package com.metropolitan.rentero_client.activities;

import com.google.android.material.textfield.TextInputEditText;
import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.adapter.ReviewAdapter;
import com.metropolitan.rentero_client.model.Company;
import com.metropolitan.rentero_client.model.Review;
import com.metropolitan.rentero_client.model.ReviewRequest;
import com.metropolitan.rentero_client.service.CompanyService;
import com.metropolitan.rentero_client.service.ReviewService;
import com.metropolitan.rentero_client.utils.AppConstants;
import com.metropolitan.rentero_client.utils.AuthUtil;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyDetailsActivity extends AppCompatActivity {

    private ScrollView companyScrollView;
    private ImageView companyImage;
    private TextView companyName, companyDescription, companyAddress, companyCity,
            companyPhoneNumber, companyEmail, companyAverageRating;
    private RatingBar companyRatingBar, addMark;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private TextInputEditText addComment;
    private Button addReviewBtn;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        long companyId = getIntent().getLongExtra("companyId", 0);

        companyScrollView = findViewById(R.id.companyScrollView);

        companyImage = findViewById(R.id.companyImage);

        companyName = findViewById(R.id.companyName);
        companyDescription = findViewById(R.id.companyDescription);
        companyAddress = findViewById(R.id.companyAddress);
        companyCity = findViewById(R.id.companyCity);
        companyPhoneNumber = findViewById(R.id.companyPhoneNumber);
        companyEmail = findViewById(R.id.companyEmail);
        companyAverageRating = findViewById(R.id.companyAverageRating);

        companyRatingBar = findViewById(R.id.companyRatingBar);
        addMark = findViewById(R.id.addMark);

        addComment = findViewById(R.id.addComment);

        addReviewBtn = findViewById(R.id.addReviewBtn);

        addReviewBtn.setOnClickListener(v -> {
            int mark = Math.round(addMark.getRating());
            String comment = addComment.getText().toString();
            ReviewRequest reviewRequest = new ReviewRequest(mark, comment);

            String token = new AuthUtil(this).getToken();

            ReviewService reviewService = retrofit.create(ReviewService.class);
            Call<Review> reviewResponse = reviewService.create("Bearer " + token, companyId, reviewRequest);

            reviewResponse.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Uspešno ste ocenili kompaniju!", Toast.LENGTH_LONG).show();
                        addMark.setRating(1);
                        addComment.setText("");
                        companyScrollView.fullScroll(ScrollView.FOCUS_UP);
                        retrieveCompany(companyId);
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
                public void onFailure(Call<Review> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        reviewRecyclerView = findViewById(R.id.reviewsList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this);
        reviewRecyclerView.setAdapter(reviewAdapter);

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

                    ReviewService reviewService = retrofit.create(ReviewService.class);
                    Call<List<Review>> reviewResponse = reviewService.getAll(company.getId());

                    reviewResponse.enqueue(new Callback<List<Review>>() {
                        @Override
                        public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                            if (response.isSuccessful()) {
                                List<Review> reviews = response.body();
                                populateRatingBar(reviews);
                                reviewAdapter.setTasks(reviews);
                            } else {
                                Toast.makeText(getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Review>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

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

    private void populateRatingBar(List<Review> reviews) {
        int numOfRatings = reviews.size();
        int sumOfRatings = reviews.stream().mapToInt(Review::getMark).sum();
        float averageRating = (float) sumOfRatings / numOfRatings;

        companyAverageRating.setText(String.format("%.2f", averageRating));
        companyRatingBar.setRating(averageRating);
    }

}