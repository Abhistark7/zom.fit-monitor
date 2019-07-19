package com.example.zomfitmonitor.model.add;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class AddCityRequest {

    @SerializedName("cityName")
    public String cityName;

    @SerializedName("cityImageUrl")
    public String cityImageUrl;
}
