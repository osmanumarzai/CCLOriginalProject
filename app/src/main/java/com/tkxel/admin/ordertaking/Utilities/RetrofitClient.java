package com.tkxel.admin.ordertaking.Utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{

    private static final String BASE_URL = Api.BASE_URL;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


}
