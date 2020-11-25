package com.example.bank.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtilsBanks {
    private static final String BANK_API_BASE_URL = "api.privatbank.ua";
    private static final String BANK_GET = "/p24api/infrastructure";
    private static final String PARAM_ADDRESS="address";
    private static final String PARAM_CITY="city";

    public static URL generateURL(String address, String city) {
        Uri buildUri = Uri.parse(BANK_API_BASE_URL + BANK_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_ADDRESS, address)
                .appendQueryParameter(PARAM_CITY, city)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
