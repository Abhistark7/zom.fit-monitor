package com.example.zomfitmonitor.model.getactivities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetActivitiesRequest {

    @SerializedName("activityIdList")
    public List<String> activityIdList;
}
