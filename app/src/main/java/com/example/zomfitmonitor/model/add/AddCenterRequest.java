package com.example.zomfitmonitor.model.add;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class AddCenterRequest {

    @SerializedName("centerName")
    public String centerName;

    @SerializedName("cityName")
    public String cityName;

    @SerializedName("centerImageUrl")
    public String centerImageUrl;
}
