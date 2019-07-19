package com.example.zomfitmonitor.network;

import com.example.zomfitmonitor.model.BaseResponse;
import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.City;
import com.example.zomfitmonitor.model.activity.Activity;
import com.example.zomfitmonitor.model.add.AddActivityRequest;
import com.example.zomfitmonitor.model.add.AddCenterRequest;
import com.example.zomfitmonitor.model.add.AddCityRequest;
import com.example.zomfitmonitor.model.getactivities.GetActivitiesRequest;
import com.example.zomfitmonitor.model.getactivities.GetActivitiesResponse;
import com.example.zomfitmonitor.model.getcenters.GetCentersRequest;
import com.example.zomfitmonitor.model.getcenters.GetCentersResponse;
import com.example.zomfitmonitor.model.login.LoginRequest;
import com.example.zomfitmonitor.model.login.LoginResponse;
import com.example.zomfitmonitor.screens.add.AddActivityActivity;

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

    @GET("/getAllActivities")
    Call<List<Activity>> getAllActivities();

    @GET("/getTrendingActivities")
    Call<List<Activity>> getTrendingActivities();

    @GET("/getTrendingCenters")
    Call<List<Center>> getTrendingCenters();

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/getCentersById")
    Call<GetCentersResponse> getCenterByCity(@Body GetCentersRequest getCentersRequest);

    @POST("/getActivitiesById")
    Call<GetActivitiesResponse> getActivityById(@Body GetActivitiesRequest getActivitiesRequest);

    @POST("/addCity")
    Call<BaseResponse> addCity(@Body AddCityRequest request);

    @POST("/addCenter")
    Call<BaseResponse> addCenter(@Body AddCenterRequest request);

    @POST("/addActivity")
    Call<BaseResponse> addActivity(@Body AddActivityRequest request);
}
