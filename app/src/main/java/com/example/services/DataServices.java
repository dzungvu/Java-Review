package com.example.services;

import com.example.models.Data;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataServices {
    @GET("v1/808ec3de")
    Observable<Response<List<Data>>> fetchData();
}
