package com.example.zomfitmonitor.model.getactivities;

import com.example.zomfitmonitor.model.activity.Activity;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetActivitiesResponse {

    @SerializedName("activityList")
    public List<Activity> activityList;
}
