package com.example.zomfitmonitor.network;

import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.City;
import com.example.zomfitmonitor.model.getactivities.GetActivitiesRequest;
import com.example.zomfitmonitor.model.getactivities.GetActivitiesResponse;
import com.example.zomfitmonitor.model.getcenters.GetCentersRequest;
import com.example.zomfitmonitor.model.getcenters.GetCentersResponse;
import com.example.zomfitmonitor.model.login.LoginRequest;
import com.example.zomfitmonitor.model.login.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/getAllCities")
    Call<List<City>> getAllCities();

    @GET("/getAllCenters")
    Call<List<Center>> getAllCenters();

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/getCentersById")
    Call<GetCentersResponse> getCenterByCity(@Body GetCentersRequest getCentersRequest);

    @POST("/getActivitiesById")
    Call<GetActivitiesResponse> getActivityById(@Body GetActivitiesRequest getActivitiesRequest);
}
