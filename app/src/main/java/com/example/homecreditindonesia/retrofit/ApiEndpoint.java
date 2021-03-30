package com.example.homecreditindonesia.retrofit;

import com.example.homecreditindonesia.model.Data;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {

    @GET("home")
    Call<Data> getData();
}
