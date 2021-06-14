package com.metropolitan.rentero_client.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthUtil {

    private Context context;

    private static final String SHARED_PREFS = "prefs";
    private static final String JWT_TOKEN = "token";
    private static final String EXPIRATION_KEY = "expiration";
    private static final long EXPIRATION_TIMESTAMP = 604800000;

    public AuthUtil(Context context) {
        this.context = context;
    }

    public void storeToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(JWT_TOKEN, token);

        long currentTimestamp = System.currentTimeMillis();
        long expirationTimestamp = currentTimestamp + EXPIRATION_TIMESTAMP;
        editor.putLong(EXPIRATION_KEY, expirationTimestamp);

        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(JWT_TOKEN, "");
    }

    public void removeToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(JWT_TOKEN);
        editor.apply();
    }

    public boolean isTokenExpired() {
        if (getToken().isEmpty()) return true;

        long currentTimestamp = System.currentTimeMillis();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        long expirationTimestamp = sharedPreferences.getLong(EXPIRATION_KEY, 0);

        System.out.println("current timestamp: " + currentTimestamp);
        System.out.println("expiration timestamp: " + expirationTimestamp);

        return currentTimestamp > expirationTimestamp;
    }

}