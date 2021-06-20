package com.metropolitan.rentero_client.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.activities.LoginActivity;
import com.metropolitan.rentero_client.model.User;
import com.metropolitan.rentero_client.service.UserService;
import com.metropolitan.rentero_client.utils.AppConstants;
import com.metropolitan.rentero_client.utils.AuthUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    private CircleImageView userAvatar;
    private TextView userFullName, userEmail, userPhoneNumber;
    private Button userLogOutBtn;

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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        userAvatar = view.findViewById(R.id.userAvatar);

        userFullName = view.findViewById(R.id.userFullName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhoneNumber = view.findViewById(R.id.userPhoneNumber);

        userLogOutBtn = view.findViewById(R.id.userLogOutBtn);

        userLogOutBtn.setOnClickListener(v -> {
            AuthUtil authUtil = new AuthUtil(getContext());
            authUtil.removeToken();
            Toast.makeText(getActivity().getApplicationContext(), "Odjavili ste se!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        retrieveUser();

        return view;
    }

    private void retrieveUser() {

        String token = new AuthUtil(getContext()).getToken();
        JWT jwt = new JWT(token);

        UserService userService = retrofit.create(UserService.class);
        Call<User> userResponse = userService.getByEmail(jwt.getSubject());

        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    populateFields(user);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Greska!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateFields(User user) {
        Picasso.get()
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.user)
                .resize(96, 96)
                .centerCrop()
                .into(userAvatar);

        userFullName.setText(user.getFullName());
        userEmail.setText(user.getEmail());
        userPhoneNumber.setText(user.getPhoneNumber());
    }

}